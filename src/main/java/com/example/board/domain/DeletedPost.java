package com.example.board.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
@Builder
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
}
