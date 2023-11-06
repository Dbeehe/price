package com.icia.price.repository;

import com.icia.price.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void increaseHits(@Param("id") Long id);

    Page<BoardEntity> findByBoardTitleContaining(String q, Pageable pageable);
    Page<BoardEntity> findByBoardWriterContaining(String q, Pageable pageable);

}