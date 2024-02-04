package com.example.board.controller;

import com.example.board.dto.CommentDto;
import com.example.board.dto.CommentEditDto;
import com.example.board.service.CommentService;
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
@RequestMapping("api/v1/board/{boardId}/comment")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @PostMapping("write")
    public ResponseEntity<CommentDto> writeComment(@PathVariable(value = "boardId") Long boardId,
                                                   @RequestBody @Valid CommentDto commentDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                logger.error("{}: {}", fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(commentDto);
        }
        commentService.saveComment(boardId, commentDto);
        return ResponseEntity.ok()
                .body(commentDto);
    }

    @PutMapping("{commentId}/edit")
    public ResponseEntity<CommentEditDto> editComment(@PathVariable(value = "boardId") Long boardId,
                                                      @PathVariable(value = "commentId") Long commentId,
                                                      @RequestBody @Valid CommentEditDto commentEditDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                logger.error("{}: {}", fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest()
                    .body(CommentEditDto.builder()
                            .password(null)
                            .content(null)
                            .build());
        }
        commentService.updateComment(boardId, commentId, commentEditDto);
        return ResponseEntity.ok()
                .body(commentEditDto);
    }
}
