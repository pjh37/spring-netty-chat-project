package com.netty.practice.api;

import com.netty.practice.domain.dto.ChatListResDto;
import com.netty.practice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatApiController {
    private final ChatService chatService;

    @GetMapping("/api/v1/messages")
    public List<ChatListResDto> findPagingMessages(@RequestParam(value = "page",defaultValue = "1")int page,
                                                   @RequestParam(value = "chatId",defaultValue = "-1")Long id,
                                                @RequestParam(value = "roomId")String roomId){
        return chatService.findPagingMessages(page,id,roomId);
    }


}
