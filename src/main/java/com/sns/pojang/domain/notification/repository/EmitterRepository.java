package com.sns.pojang.domain.notification.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class EmitterRepository {

    // ConcurrentHashMap: 멀티스레드 환경에서 안전하게 HashMap 을 조작할 수 있도록 java 에서 제공하는 자료 구조(Thread-Safe)
    // HashMap을 그냥 사용하면 멀티스레드 환경에서 동시에 수정을 시도하는 경우 예상하지 못한 결과가 발생할 수 있음
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

    public SseEmitter save(String emitterId, SseEmitter sseEmitter){
        emitters.put(emitterId, sseEmitter);
        log.info("SSE Emitter 저장 완료!\n emitterId: " + emitterId);
        return sseEmitter;
    }

    public void saveEventCache(String id, Object event){
        eventCache.put(id, event);
    }

    // 해당 receiver와 관련된 모든 emitter 조회
    public Map<String, SseEmitter> findAllStartWithByReceiverId(String receiverId){
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(receiverId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    // 해당 member와 관련된 모든 event 조회
    public Map<String, Object> findAllEventCacheStartWithByMemberId(String memberId){
        return eventCache.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(memberId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void deleteAllStartWithId(String id){
        emitters.forEach(
                (key, emitter) -> {
                    if (key.startsWith(id)){
                        emitters.remove(key);
                    }
                }
        );
    }

    public void deleteAllEventCacheStartWithId(String id){
        eventCache.forEach(
                (key, emitter) -> {
                    if (key.startsWith(id)){
                        eventCache.remove(key);
                    }
                }
        );
    }

    public void deleteById(String id){
        emitters.remove(id);
    }
}
