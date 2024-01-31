package com.example.board.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class DeletedPostDto {
    private Long boardId;
    private String nickname;
    private String password;
    private String title;
    private String content;
    private Integer views;
    private Integer reports;

    @Builder
    public DeletedPostDto(Long boardId, String nickname, String password, String title, String content, Integer views, Integer reports) {
        this.boardId = boardId;
        this.nickname = nickname;
        this.password = password;
        this.title = title;
        this.content = content;
        this.views = views;
        this.reports = reports;
    }
}