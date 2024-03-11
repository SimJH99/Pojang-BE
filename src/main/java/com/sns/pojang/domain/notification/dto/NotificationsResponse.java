package com.sns.pojang.domain.notification.dto;

import com.sns.pojang.domain.notification.domain.Notification;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class NotificationsResponse {
    // 유저의 모든 알림
    private List<NotificationResponse> notificationResponses;

    // 읽지 않은 알림 수
    private long unreadCount;

    public static NotificationsResponse of(List<NotificationResponse> notificationResponses, long count){
        return NotificationsResponse.builder()
                .notificationResponses(notificationResponses)
                .unreadCount(count)
                .build();
    }
}
