package com.example.board.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Comment extends Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String nickname;
    private String password;
    private String content;
    private Integer hearts;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Comment(Long commentId, String nickname, String password, String content, Integer hearts, Board board) {
        this.commentId = commentId;
        this.nickname = nickname;
        this.password = password;
        this.content = content;
        this.hearts = hearts;
        this.board = board;
    }
}
