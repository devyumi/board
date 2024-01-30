package com.example.board.controller;

import com.example.board.dto.BoardDto;
import com.example.board.dto.BoardListsDto;
import com.example.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/board")
@AllArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final Logger logger = LoggerFactory.getLogger(BoardController.class);

    @PostMapping("write")
    public ResponseEntity<BoardDto> writePost(@RequestBody @Valid BoardDto boardDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                logger.error("{}: {}", fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(boardDto);
        }
        boardService.savePost(boardDto);
        return ResponseEntity.ok()
                .body(boardDto);
    }

    @GetMapping()
    public ResponseEntity<List<BoardListsDto>> findLists() {
        return ResponseEntity.ok()
                .body(boardService.findPost());
    }
}
