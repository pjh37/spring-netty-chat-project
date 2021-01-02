package com.netty.practice.api;

import com.netty.practice.domain.dto.RoomListResDto;
import com.netty.practice.service.RoomService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@RestController
public class RoomApiController {
    private final RoomService roomService;

    @GetMapping("/api/v1/rooms")
    public Result findPagingRoom(@RequestParam(value = "page",defaultValue = "1")int page,
                                 @RequestParam(value = "totalCount",defaultValue = "1")Long totalCount) {
        if(totalCount==1){
            totalCount=roomService.getTotalPageCount(totalCount);
        }
        return new Result<List<RoomListResDto>>(roomService.findPagingRoom(page),roomService.getPageList(page,totalCount),totalCount);
    }

    @Data
    @AllArgsConstructor
    class Result<T>{
        private T data;
        private List<Integer> pageList;
        private Long totalCount;
    }
}
