package com.example.board.service;

import com.example.board.domain.Board;
import com.example.board.dto.BoardDetailsDto;
import com.example.board.dto.BoardDto;
import com.example.board.dto.BoardListsDto;
import com.example.board.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long savePost(BoardDto boardDto) {
        Board board = Board.builder()
                .nickname(boardDto.getNickname())
                .password(passwordEncoder.encode(boardDto.getPassword()))
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .views(0)
                .reports(0)
                .status(1)
                .build();

        return boardRepository.save(board).getBoardId();
    }

    public List<BoardListsDto> findPost() {
        List<Board> boards = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardId"));
        List<BoardListsDto> boardListsDtos = new ArrayList<>();

        for (Board board : boards) {
            BoardListsDto boardListsDto = BoardListsDto.builder()
                    .boardId(board.getBoardId())
                    .title(board.getTitle())
                    .nickname(board.getNickname())
                    .views(board.getViews())
                    .createDate(board.getCreateDate())
                    .build();

            boardListsDtos.add(boardListsDto);
        }
        return boardListsDtos;
    }

    @Transactional
    public BoardDetailsDto findPostDetails(Long boardId) {
        updateViews(boardId);
        Board board = boardRepository.findById(boardId).get();
        return BoardDetailsDto.builder()
                .nickname(board.getNickname())
                .views(board.getViews())
                .createDate(board.getCreateDate())
                .title(board.getTitle())
                .content(board.getContent())
                .build();
    }

    @Transactional
    public Integer updateViews(Long boardId) {
        return boardRepository.updateViewCount(boardId);
    }
}
