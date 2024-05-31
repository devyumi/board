package com.example.board.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Image extends Time{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;
    private String originName;
    private String saveName;
    private String imagePath;
    private Long imageSize;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Image(Long imageId, String originName, String saveName, String imagePath, Long imageSize, Board board) {
        this.imageId = imageId;
        this.originName = originName;
        this.saveName = saveName;
        this.imagePath = imagePath;
        this.imageSize = imageSize;
        this.board = board;
    }
}