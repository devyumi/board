package com.example.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class BoardListsDto {
    private Long boardId;
    private String title;
    private String nickname;
    private Integer views;
    private LocalDateTime createDate;

    @Builder
    public BoardListsDto(Long boardId, String title, String nickname, Integer views, LocalDateTime createDate) {
        this.boardId = boardId;
        this.title = title;
        this.nickname = nickname;
        this.views = views;
        this.createDate = createDate;
    }
}