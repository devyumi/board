package com.example.board.service;

import com.example.board.domain.Board;
import com.example.board.dto.BoardDetailsDto;
import com.example.board.dto.BoardDto;
import com.example.board.dto.BoardEditDto;
import com.example.board.dto.BoardListsDto;
import com.example.board.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
            if (board.getStatus() == 1) {
                BoardListsDto boardListsDto = BoardListsDto.builder()
                        .boardId(board.getBoardId())
                        .title(board.getTitle())
                        .nickname(board.getNickname())
                        .views(board.getViews())
                        .createDate(board.getCreateDate())
                        .build();
                boardListsDtos.add(boardListsDto);
            }
        }
        return boardListsDtos;
    }

    @Transactional
    public BoardDetailsDto findPostDetails(Long boardId) {
        Board board = boardRepository.findById(boardId).get();
        if (board.getStatus() == 0) {
            throw new IllegalStateException("페이지가 존재하지 않습니다.");
        } else {
            boardRepository.updateViewCount(boardId);
            return BoardDetailsDto.builder()
                    .nickname(board.getNickname())
                    .views(board.getViews() + 1)
                    .createDate(board.getCreateDate())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .build();
        }
    }

    @Transactional
    public Integer deletePost(Long boardId, String password) {
        Board board = boardRepository.findById(boardId).get();

        if (passwordEncoder.matches(password.toString(), board.getPassword())) {
            return boardRepository.updateStatus(boardId);
        } else {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Transactional
    public void updatePost(Long boardId, BoardEditDto boardEditDto) {
        Board board = boardRepository.findById(boardId).get();

        if (passwordEncoder.matches(boardEditDto.getPassword().toString(), board.getPassword())) {
            boardRepository.updateBoard(boardId, boardEditDto.getTitle(), boardEditDto.getContent());
        } else {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
    }
}
