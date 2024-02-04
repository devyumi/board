package com.example.board.service;

import com.example.board.domain.Board;
import com.example.board.domain.Comment;
import com.example.board.dto.CommentDto;
import com.example.board.dto.CommentEditDto;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CommentService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long saveComment(Long boardId, CommentDto commentDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalStateException("페이지가 존재하지 않습니다."));

        Comment comment = Comment.builder()
                .nickname(commentDto.getNickname())
                .password(passwordEncoder.encode(commentDto.getPassword()))
                .content(commentDto.getContent())
                .hearts(0)
                .board(board)
                .build();

        return commentRepository.save(comment).getCommentId();
    }

    @Transactional
    public void updateComment(Long boardId, Long commentId, CommentEditDto commentEditDto) {
        boardRepository.findById(boardId).orElseThrow(() -> new IllegalStateException("페이지가 존재하지 않습니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalStateException("댓글이 존재하지 않습니다."));

        if (comment.getBoard().getBoardId() != boardId) {
            throw new IllegalStateException("잘못된 경로입니다.");
        }

        if (passwordEncoder.matches(commentEditDto.getPassword().toString(), comment.getPassword())) {
            commentRepository.updateComment(commentId, commentEditDto.getContent());
        } else {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Transactional
    public void deleteComment(Long boardId, Long commentId, String password) {
        boardRepository.findById(boardId).orElseThrow(() -> new IllegalStateException("페이지가 존재하지 않습니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalStateException("댓글이 존재하지 않습니다."));

        if (comment.getBoard().getBoardId() != boardId) {
            throw new IllegalStateException("잘못된 경로입니다.");
        }

        if (passwordEncoder.matches(password.toString(), comment.getPassword())) {
            commentRepository.deleteById(commentId);
        } else {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Transactional
    public void recommendComment(Long boardId, Long commentId) {
        boardRepository.findById(boardId).orElseThrow(() -> new IllegalStateException("페이지가 존재하지 않습니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalStateException("댓글이 존재하지 않습니다."));

        if (comment.getBoard().getBoardId() != boardId) {
            throw new IllegalStateException("잘못된 경로입니다.");
        }
        commentRepository.updateHeartCount(commentId);
    }
}
