package kr.kiomn2.newsletter.application.member.required;

import kr.kiomn2.newsletter.domain.member.subscriber.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
}
