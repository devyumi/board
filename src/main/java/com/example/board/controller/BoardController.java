package com.example.board.controller;

import com.example.board.dto.BoardDto;
import com.example.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/board")
@AllArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("write")
    public ResponseEntity<BoardDto> writePost(@RequestBody @Valid BoardDto boardDto, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        boardService.savePost(boardDto);
        return ResponseEntity.ok(boardDto);
    }
}
