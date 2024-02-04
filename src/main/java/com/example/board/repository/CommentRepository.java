package com.example.board.repository;

import com.example.board.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Modifying
    @Query("update Comment c set c.content = :content where c.commentId = :commentId")
    void updateComment(Long commentId, String content);

    @Modifying
    @Query("update Comment c set c.hearts = c.hearts + 1 where c.commentId = :commentId")
    Integer updateHeartCount(Long commentId);

    Long countByBoard_BoardId(Long boardId);
}