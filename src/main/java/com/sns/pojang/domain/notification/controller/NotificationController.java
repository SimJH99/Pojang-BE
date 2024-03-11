package com.sns.pojang.domain.notification.controller;

import com.sns.pojang.domain.notification.dto.NotificationsResponse;
import com.sns.pojang.domain.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@Slf4j
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * 클라이언트로부터 오는 알림 구독 요청을 받는 API
     * lastEventId: 클라이언트가 마지막으로 수신한 데이터의 id값
     * -> sse 연결이 시간 만료 등의 이유로 끊어졌을 때 발생하는 알림 데이터 유실 방지
     */
    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "")
                                    String lastEventId){
        log.info("SSE 연결 요청 Controller 시작!");
        return notificationService.subscribe(lastEventId);
    }

    /**
     * 읽지 않은 알림 조회
     */
    @GetMapping("/notifications")
    public ResponseEntity<NotificationsResponse> findUnReadNotifications(){
        return ResponseEntity.ok().body(notificationService.findUnReadNotifications());
    }

    /**
     * 알림 읽음 처리
     */
    @PatchMapping("/notifications/{id}")
    public ResponseEntity<Void> readNotification(@PathVariable Long id){
        notificationService.readNotification(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
