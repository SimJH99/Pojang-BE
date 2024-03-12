package com.sns.pojang.domain.notification.repository;

import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByReceiver(Member receiver); // 알림 전체 목록을 조회
}
