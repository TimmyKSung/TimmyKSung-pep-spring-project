package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

/**
 * JPARepository that will be used to preform persistence operations on Message objects
 */
public interface MessageRepository extends JpaRepository<Message, Long>{
    Optional<Message> findByMessageId(int messageId);
    List<Message> findAllByPostedBy(int postedBy);
}
