package com.example.board.controller;

import com.example.board.dto.BoardDetailsDto;
import com.example.board.dto.BoardDto;
import com.example.board.dto.BoardEditDto;
import com.example.board.dto.BoardListsDto;
import com.example.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<List<BoardListsDto>> findLists(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                                         @PageableDefault(size = 5, sort = "boardId", direction = Sort.Direction.DESC) Pageable pageable) {
        if (keyword.length() == 0) {
            return ResponseEntity.ok()
                    .body(boardService.findPost(pageable));
        } else if (keyword.length() == 1) {
            throw new IllegalStateException("검색 키워드를 두 글자 이상 입력하세요.");
        } else {
            return ResponseEntity.ok()
                    .body(boardService.searchPosts(keyword, pageable));
        }
    }

    @GetMapping("{boardId}")
    public ResponseEntity<BoardDetailsDto> findPostDetails(@PathVariable(value = "boardId", required = false) Long boardId) {
        return ResponseEntity.ok()
                .body(boardService.findPostDetails(boardId));
    }

    @PutMapping("{boardId}")
    public ResponseEntity<String> deletePost(@PathVariable(value = "boardId") Long boardId, @RequestBody Map<String, String> password) {
        boardService.deletePost(boardId, password.get("password"));
        return ResponseEntity.ok()
                .body("삭제되었습니다.");
    }

    @PutMapping("{boardId}/edit")
    public ResponseEntity<BoardEditDto> editPost(@PathVariable(value = "boardId") Long boardId, @RequestBody @Valid BoardEditDto boardEditDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                logger.error("{}: {}", fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(boardEditDto);
        }
        boardService.updatePost(boardId, boardEditDto);
        return ResponseEntity.ok()
                .body(boardEditDto);
    }

    @PostMapping("{boardId}/report")
    public ResponseEntity<String> reportPost(@PathVariable(value = "boardId") Long boardId) {
        boardService.reportPost(boardId);
        return ResponseEntity.ok()
                .body("게시글이 신고되었습니다.");
    }
}
