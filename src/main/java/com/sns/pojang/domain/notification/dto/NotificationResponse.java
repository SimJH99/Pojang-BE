package com.sns.pojang.domain.notification.dto;

import com.sns.pojang.domain.notification.domain.Notification;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class NotificationResponse {
    // 알림 id
//    private Long id;
    private Long storeId;
    private String storeName;
    // 알림 내용
//    private String content;

    // 알림 클릭 시 이동할 url
//    private String url;

    // 알림 읽음 여부
//    private Boolean isRead;

    // 알림 발송 시간
    private String createdAt;

    public static NotificationResponse from(Notification notification){
        return NotificationResponse.builder()
                .storeId(notification.getOrder().getStore().getId())
                .storeName(notification.getOrder().getStore().getName())
//                .content(notification.getContent())
//                .url(notification.getRelatedUrl())
//                .isRead(notification.getIsRead())
                .createdAt(notification.getCreatedTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }
}
