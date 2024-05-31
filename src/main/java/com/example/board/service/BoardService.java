package com.example.board.service;

import com.example.board.domain.Board;
import com.example.board.domain.Comment;
import com.example.board.domain.DeletedPost;
import com.example.board.domain.Image;
import com.example.board.dto.*;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import com.example.board.repository.DeletedPostRepository;
import com.example.board.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;
    private final CommentRepository commentRepository;
    private final DeletedPostRepository deletedPostRepository;
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
                .build();

        return boardRepository.save(board).getBoardId();
    }

    public List<BoardListsDto> findPost(Pageable pageable) {
        Page<Board> boards = boardRepository.findAll(pageable);
        List<BoardListsDto> boardListsDtos = new ArrayList<>();

        for (Board board : boards) {
            boardListsDtos.add(BoardListsDto.builder()
                    .boardId(board.getBoardId())
                    .title(board.getTitle())
                    .commentCount(commentRepository.countByBoard_BoardId(board.getBoardId()))
                    .nickname(board.getNickname())
                    .views(board.getViews())
                    .createDate(board.getCreateDate())
                    .build());
        }
        return boardListsDtos;
    }

    @Transactional
    public BoardDetailsDto findPostDetails(Long boardId, Pageable pageable) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalStateException("페이지가 존재하지 않습니다."));
        boardRepository.updateViewCount(boardId);

        List<Image> images = imageRepository.findAllByBoard_BoardId(boardId);
        List<ImageDetailsDto> imageDetailsDtos = new ArrayList<>();

        List<Comment> comments = commentRepository.findAllByBoard_BoardId(boardId, pageable);
        List<CommentDetailsDto> commentDetailsDtos = new ArrayList<>();

        for (Image image : images) {
            imageDetailsDtos.add(ImageDetailsDto.builder()
                    .originName(image.getOriginName())
                    .saveName(image.getSaveName())
                    .imagePath(image.getImagePath())
                    .imageSize(image.getImageSize())
                    .build());
        }

        for (Comment comment : comments) {
            commentDetailsDtos.add(CommentDetailsDto.builder()
                    .nickname(comment.getNickname())
                    .content(comment.getContent())
                    .hearts(comment.getHearts())
                    .createDate(comment.getCreateDate())
                    .build());
        }

        return BoardDetailsDto.builder()
                .nickname(board.getNickname())
                .views(board.getViews() + 1)
                .createDate(board.getCreateDate())
                .title(board.getTitle())
                .content(board.getContent())
                .images(imageDetailsDtos)
                .comments(commentDetailsDtos)
                .build();
    }

    @Transactional
    public Long deletePost(Long boardId, String password) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalStateException("페이지가 존재하지 않습니다."));

        if (passwordEncoder.matches(password.toString(), board.getPassword())) {
            List<Comment> comments = commentRepository.findAllByBoard_BoardId(boardId);

            for (Comment comment : comments) {
                commentRepository.delete(comment);
            }

            boardRepository.delete(board);
            DeletedPost deletedPost = DeletedPost.builder()
                    .boardId(board.getBoardId())
                    .nickname(board.getNickname())
                    .password(board.getPassword())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .views(board.getViews())
                    .reports(board.getReports())
                    .createDate(board.getCreateDate())
                    .modifiedDate(board.getModifiedDate())
                    .build();

            return deletedPostRepository.save(deletedPost).getDeletedPostId();
        } else {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Transactional
    public void updatePost(Long boardId, BoardEditDto boardEditDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalStateException("페이지가 존재하지 않습니다."));

        if (passwordEncoder.matches(boardEditDto.getPassword().toString(), board.getPassword())) {
            boardRepository.updateBoard(boardId, boardEditDto.getTitle(), boardEditDto.getContent());
        } else {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Transactional
    public void reportPost(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalStateException("페이지가 존재하지 않습니다."));

        boardRepository.updateReportCount(boardId);
        if (board.getReports() + 1 == 5) {
            boardRepository.delete(board);

            DeletedPost deletedPost = DeletedPost.builder()
                    .boardId(board.getBoardId())
                    .nickname(board.getNickname())
                    .password(board.getPassword())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .views(board.getViews())
                    .reports(board.getReports() + 1)
                    .createDate(board.getCreateDate())
                    .modifiedDate(board.getModifiedDate())
                    .build();

            deletedPostRepository.save(deletedPost).getDeletedPostId();
        }
    }

    public List<BoardListsDto> searchPosts(String keyword, Pageable pageable) {
        List<Board> boards = boardRepository.findByTitleContaining(keyword, pageable);
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
}