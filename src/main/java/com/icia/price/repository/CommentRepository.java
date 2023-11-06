package com.icia.price.repository;

import com.icia.price.entity.BoardEntity;
import com.icia.price.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByBoardEntityOrderByIdDesc(BoardEntity boardEntity);
}