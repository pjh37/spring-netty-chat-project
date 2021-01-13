package com.netty.practice.domain.board;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {
    @Query("select b from Board b")
    List<Board> findPagingBoard(Pageable pageable);

    @Query("select b from Board b where b.title like :search%")
    List<Board> findPagingBoard(Pageable pageable,@Param("search")String search);
}
