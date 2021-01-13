package com.netty.practice.api;

import com.netty.practice.domain.dto.BoardListResDto;
import com.netty.practice.domain.dto.BoardSaveRequestDto;
import com.netty.practice.domain.dto.BoardUpdateRequestDto;
import com.netty.practice.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardApiController {
    private final BoardService boardService;

    @PostMapping("/api/v1/board")
    public Long save(@RequestBody BoardSaveRequestDto boardSaveRequestDto){
        return boardService.save(boardSaveRequestDto);
    }

    @GetMapping("/api/v1/boards")
    public List<BoardListResDto> findByPaging(@RequestParam(value = "page",defaultValue = "1")int page){
        log.info("findByPaging");
        return boardService.findByPaging(page);
    }

    @PutMapping("/api/v1/board/{id}")
    public Long update(@PathVariable Long id, @RequestBody BoardUpdateRequestDto boardUpdateRequestDto){
        return boardService.update(id,boardUpdateRequestDto);
    }

    @DeleteMapping("/api/v1/board/{id}")
    public void delete(@PathVariable Long id){
        boardService.delete(id);
    }
}
