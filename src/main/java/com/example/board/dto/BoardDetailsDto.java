package com.example.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class BoardDetailsDto {
    private String nickname;
    private Integer views;
    @JsonFormat(pattern = "yyyy.MM.dd. HH:mm")
    private LocalDateTime createDate;
    private String title;
    private String content;

    @Builder
    public BoardDetailsDto(String nickname, Integer views, LocalDateTime createDate, String title, String content) {
        this.nickname = nickname;
        this.views = views;
        this.createDate = createDate;
        this.title = title;
        this.content = content;
    }
}
