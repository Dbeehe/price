package com.icia.price.service;

import com.icia.price.dto.BoardDTO;
import com.icia.price.dto.GoodsDTO;
import com.icia.price.entity.BoardEntity;
import com.icia.price.entity.GoodsEntity;
import com.icia.price.entity.GoodsFileEntity;
import com.icia.price.repository.GoodsFileRepository;
import com.icia.price.repository.GoodsRepository;
import com.icia.price.util.UtilClass;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;
    private final GoodsFileRepository goodsFileRepository;

    public Long save(GoodsDTO goodsDTO) throws IOException {
        if (goodsDTO.getGoodsFile().get(0).isEmpty()) {
            GoodsEntity goodsEntity = GoodsEntity.toSaveEntity(goodsDTO);
            return goodsRepository.save(goodsEntity).getId();
        } else {
            GoodsEntity goodsEntity = GoodsEntity.toSaveEntityWithFile(goodsDTO);
            GoodsEntity savedEntity = goodsRepository.save(goodsEntity);
            for (MultipartFile goodsFile: goodsDTO.getGoodsFile()) {
                String originalFilename = goodsFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
                String savePath = "D:\\springboot_img\\" + storedFileName;
                goodsFile.transferTo(new File(savePath));
                GoodsFileEntity goodsFileEntity =
                        GoodsFileEntity.toSaveBoardFile(savedEntity, originalFilename, storedFileName);
                goodsFileRepository.save(goodsFileEntity);
            }
            return savedEntity.getId();
        }
    }

    public Page<GoodsDTO> findAll(int page, String type, String q) {
        page = page - 1;
        int pageLimit = 5;
        Page<GoodsEntity> goodsEntities = null;
        if (q.equals("")) {
            goodsEntities = goodsRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        } else {
            if (type.equals("goodsName")) {
                goodsEntities = goodsRepository.findByGoodsNameContaining(q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
            } else if (type.equals("goodsMaker")) {
                goodsEntities = goodsRepository.findByGoodsMakerContaining(q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
            }
        }

        Page<GoodsDTO> goodsList = goodsEntities.map(goodsEntity ->
                GoodsDTO.builder()
                        .id(goodsEntity.getId())
                        .goodsName(goodsEntity.getGoodsName())
                        .goodsPrice(goodsEntity.getGoodsPrice())
                        .goodsMaker(goodsEntity.getGoodsMaker())
                        .createdAt(UtilClass.dateTimeFormat(goodsEntity.getCreatedAt()))
                        .build());
        return goodsList;
    }


    public GoodsDTO findById(Long id) {
        GoodsEntity goodsEntity = goodsRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return GoodsDTO.toDTO(goodsEntity);
    }
}
