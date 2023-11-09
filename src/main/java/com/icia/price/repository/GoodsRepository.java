package com.icia.price.repository;

import com.icia.price.entity.BoardEntity;
import com.icia.price.entity.GoodsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<GoodsEntity, Long> {

    Page<GoodsEntity> findByGoodsNameContaining(String q, Pageable pageable);

    Page<GoodsEntity> findByGoodsMakerContaining(String q, Pageable pageable);
}
