package com.example.board.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "deleted_post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class DeletedPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deletedPostId;
    private Long boardId;
    private String nickname;
    private String password;
    private String title;
    private String content;
    private Integer views;
    private Integer reports;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    @Builder
    public DeletedPost(Long deletedPostId, Long boardId, String nickname, String password, String title, String content, Integer views, Integer reports, LocalDateTime createDate, LocalDateTime modifiedDate) {
        this.deletedPostId = deletedPostId;
        this.boardId = boardId;
        this.nickname = nickname;
        this.password = password;
        this.title = title;
        this.content = content;
        this.views = views;
        this.reports = reports;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }
}
