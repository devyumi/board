package com.example.board.service;

import com.example.board.domain.Board;
import com.example.board.domain.Comment;
import com.example.board.dto.CommentDto;
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
    public Long saveComment(CommentDto commentDto, Long boardId) {
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
}
