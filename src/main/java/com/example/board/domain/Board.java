package com.example.board.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Board extends Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;
    private String nickname;
    private String password;
    private String title;
    private String content;
    private Integer views;
    private Integer reports;
    private Integer status;

    @Builder
    public Board(Long boardId, String nickname, String password, String title, String content, Integer views, Integer reports, Integer status) {
        this.boardId = boardId;
        this.nickname = nickname;
        this.password = password;
        this.title = title;
        this.content = content;
        this.views = views;
        this.reports = reports;
        this.status = status;
    }
}
