package com.sns.pojang.domain.notification.service;

import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.member.entity.Role;
import com.sns.pojang.domain.member.exception.MemberNotFoundException;
import com.sns.pojang.domain.member.repository.MemberRepository;
import com.sns.pojang.domain.notification.domain.Notification;
import com.sns.pojang.domain.notification.domain.NotificationType;
import com.sns.pojang.domain.notification.dto.NotificationResponse;
import com.sns.pojang.domain.notification.dto.NotificationsResponse;
import com.sns.pojang.domain.notification.exception.NotificationNotFoundException;
import com.sns.pojang.domain.notification.repository.EmitterRepository;
import com.sns.pojang.domain.notification.repository.NotificationRepository;
import com.sns.pojang.domain.order.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60; // 1시간 동안 SSE 연결 지속

    private final MemberRepository memberRepository;
    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;

    // 클라이언트에서 서버로의 SSE 연결(구독) 요청
    public SseEmitter subscribe(String lastEventId) {
        Member findMember = findMember();
        // 시간을 붙이면 데이터가 유실된 시점을 알 수 있으므로 저장된 Key 값과의 비교를 통해 유실된 데이터만 재전송
        //  Id 값만 사용하면 언제 데이터가 보내졌는지, 유실 되었는지 알 수가 없다.
        String emitterId = findMember.getId() + "_" + System.currentTimeMillis();

        // Emitter는 발신기라는 뜻을 가지고 있음.
        // SSE 연결을 위해서 유효 시간이 담긴 SseEmitter 객체를 만들어 반환해야 한다.
        // 시간이 지나면 자동으로 클라이언트에서 재연결 요청을 보낸다.
        SseEmitter sseEmitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        // 비동기 요청이 완료되거나 타임아웃 발생 시 실행할 콜백을 등록할 수 있다.
        // 타임아웃이 발생하면 브라우저에서 재연결 요청을 보내는데, 이때 새로운 Emitter 객체를 다시 생성하기 때문에
        // 기존의 Emitter를 제거해주어야 함
        sseEmitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        sseEmitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        // Sse 연결이 이뤄진 후, 데이터가 하나도 전송되지 않았는데 SseEmitter의 유효시간이 끝나면 503 에러가 발생함.
        // 따라서, 최초 연결 시 더미 데이터를 보내줌
        sendToClient(sseEmitter, emitterId, "EventStream Created. [userId = " + findMember.getId() + "]");

        // 클라이언트가 미수신한 이벤트(알림) 목록이 존재할 경우(lastEventId값이 있는 경우)
        // 저장된 데이터 캐시에서 유실된 데이터들을 다시 전송함.
        if (!lastEventId.isEmpty()) {
            Map<String, Object> events =
                    emitterRepository.findAllEventCacheStartWithByMemberId(String.valueOf(findMember.getId()));
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(sseEmitter, entry.getKey(), entry.getValue()));
        }
        log.info(sseEmitter.toString());
        return sseEmitter;
    }

    @Transactional
    // 알림 전송
    public void send(Member receiver, Order order, NotificationType notificationType,
                     String content, String url){
        log.info("알림 전송 Service Start!");
        Notification newNotification = notificationRepository.save(
                createNotification(receiver, order, notificationType, content, url));
        String receiverId = String.valueOf(receiver.getId());
        // Receiver의 emitter를 다 불러온다.
        // 여러 브라우저에서 접속할 수 있기 때문에 emitter가 여러개 일 수 있음
        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllStartWithByReceiverId(receiverId);
        sseEmitters.forEach(
                (key, emitter) -> {
                    // Emitter를 EventCache에 저장
                    emitterRepository.saveEventCache(key, newNotification);
//                    sendToClient(emitter, key, NotificationResponse.from(newNotification));
                    sendToClient(emitter, key, newNotification.getOrder().getStore().getName());
                }
        );
    }

    @Transactional
    // 알림 읽음 처리
    public void readNotification(Long id){
        Notification notification = notificationRepository.findById(id).orElseThrow(NotificationNotFoundException::new);
        notification.read();
    }

    @Transactional
    // 읽지 않은 알림 수 표시
    public NotificationsResponse findUnReadNotifications(){
        Member findReceiver = findMember();
        List<NotificationResponse> responses = notificationRepository.findByReceiver(findReceiver).stream()
                .map(NotificationResponse::from)
                .collect(Collectors.toList());

        long unreadCount = responses.stream()
                .filter(notification -> !notification.getIsRead())
                .count();

        return NotificationsResponse.of(responses, unreadCount);
    }

    // 알림 생성
    private Notification createNotification(Member receiver, Order order, NotificationType notificationType,
                                            String content, String url){
        return Notification.builder()
                .receiver(receiver)
                .notificationType(notificationType)
                .content(content)
                .order(order)
                .relatedUrl(url)
                .build();
    }

    // Client에 데이터 전송
    private void sendToClient(SseEmitter emitter, String emitterId, Object data){
        log.info("Client에게 알림 데이터 전송 시작!");
        try {
            emitter.send(SseEmitter.event()
                    .id(emitterId)
                    .data(data));
            log.info("Client에게 알림 데이터 전송 성공!");
        } catch (IOException e){
            emitterRepository.deleteById(emitterId);
            log.error("SSE 연결 오류!", e);
        }
    }

    private Member findMember(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("회원 이메일: " + authentication.getName());
        return memberRepository.findByEmail(authentication.getName())
                .orElseThrow(MemberNotFoundException::new);
    }
}
