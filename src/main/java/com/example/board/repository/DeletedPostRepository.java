package com.example.board.repository;

import com.example.board.domain.DeletedPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeletedPostRepository extends JpaRepository<DeletedPost, Long> {
}