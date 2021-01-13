package com.netty.practice.domain.chat;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Message,Long> {
    @Query("select m from Message m where m.id<:id and m.room.id=:roomId")
    List<Message> findPagingMessages(Pageable pageable,@Param("id")Long id, @Param("roomId")String roomId);

    @Query("select m from Message m where m.room.id=:roomId")
    List<Message> findPagingMessages(Pageable pageable,@Param("roomId")String roomId);
}
