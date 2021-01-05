package com.netty.practice.domain.Room;

import com.netty.practice.domain.Room.Room;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,String> {
    @Query("select r from Room r")
    List<Room> findPagingRoom(Pageable pageable);


}
