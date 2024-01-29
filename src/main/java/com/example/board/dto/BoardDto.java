package com.example.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class BoardDto {
    private Long boardId;

    @NotBlank(message = "닉네임을 입력하세요.")
    @Size(min = 3, max = 10, message = "닉네임은 3자 이상, 10자 이하만 가능합니다.")
    private String nickname;

    @NotBlank(message = "제목을 입력하세요.")
    @Size(min = 2, max = 20, message = "제목은 2자 이상, 20자 이하만 가능합니다.")
    private String title;

    @NotBlank(message = "내용을 입력하세요.")
    @Size(min = 10, message = "내용은 10자 이상 입력해야 합니다.")
    private String content;

    private Integer views;
    private Integer reports;
    private String show;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    @Builder
    public BoardDto(Long boardId, String nickname, String title, String content, Integer views, Integer reports, String show, LocalDateTime createDate, LocalDateTime modifiedDate) {
        this.boardId = boardId;
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.views = views;
        this.reports = reports;
        this.show = show;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }
}
