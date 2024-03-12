package com.sns.pojang.domain.notification.domain;

import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.order.entity.Order;
import com.sns.pojang.global.config.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column(nullable = false)
    // 어떤 페이지 url로 이동시킬 지 링크
    private String relatedUrl;

    @Column
    // 알림 읽었는 지에 대한 여부
    private Boolean isRead;

    @Enumerated(value = EnumType.STRING)
    // 알림 종류 (주문 접수, 조리 완료)
    private NotificationType notificationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) // receiver를 삭제하면 연관관계도 함께 삭제
    private Member receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Builder
    public Notification(String content, String relatedUrl, NotificationType notificationType,
                        Member receiver, Order order) {
        this.content = content;
        this.relatedUrl = relatedUrl;
        this.notificationType = notificationType;
        this.receiver = receiver;
        this.order = order;
        this.isRead = false;
    }

    public void read(){
        this.isRead = true;
    }
}
