package com.netty.practice.api;

import com.netty.practice.domain.dto.RoomListResDto;
import com.netty.practice.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
public class RoomApiController {
    private final RoomService roomService;

    @GetMapping("/api/v1/rooms")
    public List<RoomListResDto> findAllRoom() {
        return roomService.findAllRoom();
    }
}
