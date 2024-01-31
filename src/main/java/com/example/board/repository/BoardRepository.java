package com.example.board.repository;

import com.example.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    @Modifying
    @Query("update Board b set b.views = b.views + 1 where b.boardId = :boardId")
    Integer updateViewCount(Long boardId);

    @Modifying
    @Query("update Board b set b.reports = b.reports + 1 where b.boardId = :boardId")
    Integer updateReportCount(Long boardId);

    @Modifying
    @Query("update Board b set b.title = :title, b.content = :content where b.boardId = :boardId")
    void updateBoard(Long boardId, String title, String content);
}
