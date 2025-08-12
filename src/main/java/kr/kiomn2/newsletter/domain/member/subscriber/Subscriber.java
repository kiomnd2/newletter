package kr.kiomn2.newsletter.domain.member.subscriber;

import jakarta.persistence.*;
import kr.kiomn2.newsletter.domain.member.Member;
import kr.kiomn2.newsletter.domain.news.NewsCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "subscribers")
@EntityListeners(AuditingEntityListener.class)
public class Subscriber {

    @Id
    private Long id;

    @MapsId  // Member의 ID를 그대로 사용
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id")
    private Member member;

    private SubscriptionStatus subscriptionStatus;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "subscriber_categories", joinColumns = @JoinColumn(name = "subscriber_id"))
    @Column(name = "category_code")
    private Set<String> subscribedCategories = new HashSet<>();

    private Long totalSubscribedCount = 0L;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime lastEmailSentAt;


    public static Subscriber apply(Member member) {
        Subscriber subscriber = new Subscriber();
        subscriber.member = member;
        subscriber.subscriptionStatus = SubscriptionStatus.ACTIVE;
        subscriber.createdAt = LocalDateTime.now();
        subscriber.updatedAt = LocalDateTime.now();
        return subscriber;
    }

    public void addSubscribedCategory(String categoryCode) {
        this.subscribedCategories.add(categoryCode);
    }

    public void allUnsubscribe() {
        this.subscribedCategories.clear();
        this.subscriptionStatus = SubscriptionStatus.UNSUBSCRIBED;
    }

    public void pause() {
        this.subscriptionStatus = SubscriptionStatus.PAUSED;
    }

    public void resume() {
        this.subscriptionStatus = SubscriptionStatus.ACTIVE;
    }
}
