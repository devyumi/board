package com.example.board.service;

import com.example.board.domain.Board;
import com.example.board.domain.Image;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final String absolutePath = Paths.get("C:", "image").toString();
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public void saveImage(Long boardId, List<MultipartFile> images) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalStateException("페이지가 존재하지 않습니다."));

        for (MultipartFile file : images) {
            String saveFileName = createSaveFileName(file.getOriginalFilename());
            String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")).toString();
            String uploadPath = Paths.get(getUploadPath(now), saveFileName).toString();
            File uploadFile = new File(uploadPath);

            try {
                file.transferTo(uploadFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Image image = Image.builder()
                    .originName(file.getOriginalFilename())
                    .saveName(saveFileName)
                    .imagePath(uploadPath)
                    .imageSize(file.getSize())
                    .board(board)
                    .build();
            imageRepository.save(image);
        }
    }

    public void findImage() {

    }

    public void deleteImage() {

    }

    private String createSaveFileName(String filename) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String extension = StringUtils.getFilenameExtension(filename);
        return uuid + "." + extension;
    }

    private String getUploadPath(String addPath) {
        return makeDirectories(Paths.get(absolutePath, addPath).toString());
    }

    private String makeDirectories(String absolutePath) {
        File dir = new File(absolutePath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir.getPath();
    }
}