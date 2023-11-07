package com.icia.price.service;

import com.icia.price.dto.GoodsDTO;
import com.icia.price.dto.MemberDTO;
import com.icia.price.entity.GoodsEntity;
import com.icia.price.entity.MemberEntity;
import com.icia.price.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;

    public Long save(GoodsDTO goodsDTO) {
        GoodsEntity goodsEntity = GoodsEntity.toSaveEntity(goodsDTO);
        return goodsRepository.save(goodsEntity).getId();
    }
}
