package com.netty.practice.service;

import com.netty.practice.domain.board.Board;
import com.netty.practice.domain.board.BoardRepository;
import com.netty.practice.domain.dto.BoardListResDto;
import com.netty.practice.domain.dto.BoardSaveRequestDto;
import com.netty.practice.domain.dto.BoardUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {
    private static final int PAGE_SIZE=10;
    private final BoardRepository boardRepository;

    @Transactional
    public Long save(BoardSaveRequestDto boardSaveRequestDto){
        return boardRepository.save(boardSaveRequestDto.toEntity()).getId();
    }

    @Transactional
    public List<BoardListResDto> findByPaging(int page){
        Pageable pageable= PageRequest.of(page-1,PAGE_SIZE, Sort.by("id").descending());
        return boardRepository.findPagingBoard(pageable)
                .stream()
                .map(BoardListResDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long update(Long id,BoardUpdateRequestDto boardUpdateRequestDto){
        Board board=boardRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다."));
        board.update(boardUpdateRequestDto.getTitle(),boardUpdateRequestDto.getContent());
        return board.getId();
    }

    @Transactional
    public void delete(Long id){
        boardRepository.deleteById(id);
    }
}
