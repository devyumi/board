package com.example.board.service;

import com.example.board.domain.Board;
import com.example.board.dto.BoardDto;
import com.example.board.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long savePost(BoardDto boardDto){
        Board board = Board.builder()
                .nickname(boardDto.getNickname())
                .password(passwordEncoder.encode(boardDto.getPassword()))
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .views(0)
                .reports(0)
                .show("Y")
                .build();

        return boardRepository.save(board).getBoardId();
    }
}
