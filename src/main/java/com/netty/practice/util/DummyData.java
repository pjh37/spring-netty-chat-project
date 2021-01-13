package com.netty.practice.util;

import com.netty.practice.domain.board.Board;
import com.netty.practice.domain.board.BoardRepository;
import com.netty.practice.domain.chat.ChatRepository;
import com.netty.practice.domain.chat.Message;
import com.netty.practice.domain.Room.Room;
import com.netty.practice.domain.Room.RoomRepository;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class DummyData {
    private final RoomRepository roomRepository;
    private final ChatRepository chatRepository;
    private final BoardRepository boardRepository;
    private final RoomManager roomManager;

    @PostConstruct
    public void init(){
        String roomId="";
        for(int i=0;i<107;i++){
            Room room=roomRepository.save(Room.builder()
                    .title("채팅방 리스트 "+(i+1))
                    .build());
            roomManager.getRoomManager().put(room.getId(),new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));
            if(i==106){
                roomId=room.getId();
            }
        }
        Room room=roomRepository.findById(roomId).get();
        for(int i=1;i<=200;i++){
            Message message=Message.builder()
                    .username("테스터")
                    .message(i+" 번째 메세지")
                    .build();
            message.setRoom(room);
            chatRepository.save(message);
        }
        for(int i=0;i<200;i++){
            boardRepository.save(Board.builder()
                    .title("테스트 타이틀 "+i)
                    .author("홍길동")
                    .content("안녕하세요 테스트 글입니다.")
                    .build());
        }

    }
}
