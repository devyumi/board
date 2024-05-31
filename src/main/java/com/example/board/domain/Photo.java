package com.example.board.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "photo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Photo extends Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;
    private String fileName;
    private String filePath;
    private Long fileSize;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Photo(Long photoId, String fileName, String filePath, Long fileSize, Board board) {
        this.photoId = photoId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.board = board;
    }
}