package com.netty.practice.service;

import com.netty.practice.domain.chat.ChatRepository;
import com.netty.practice.domain.dto.ChatListResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private static final int PAGE_SIZE=10;
    private static final Long FIRST_PAGE=-1L;
    private final ChatRepository chatRepository;

    @Transactional
    public List<ChatListResDto> findPagingMessages(int page,Long id,String roomId){
        Pageable pageable= PageRequest.of(page-1,PAGE_SIZE, Sort.by("id").descending());
        log.info("page: "+page+" id: "+id+" roomId: "+roomId);
        if(id.equals(FIRST_PAGE)){
            log.info("page: "+page+" id: "+id+" roomId: "+roomId);
            List<ChatListResDto> chatListResDtos=chatRepository.findPagingMessages(pageable,roomId).stream()
                    .map(ChatListResDto::new)
                    .sorted(Comparator.comparing(ChatListResDto::getId))
                    .collect(Collectors.toList());
            log.info(chatListResDtos.size()+"");
            return chatListResDtos;
        }
        return chatRepository.findPagingMessages(pageable,id,roomId).stream()
                .map(ChatListResDto::new)
                .sorted(Comparator.comparing(ChatListResDto::getId))
                .collect(Collectors.toList());
    }
}
