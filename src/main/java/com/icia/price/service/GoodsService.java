package com.icia.price.service;

import com.icia.price.dto.GoodsDTO;
import com.icia.price.entity.GoodsEntity;
import com.icia.price.entity.GoodsFileEntity;
import com.icia.price.repository.GoodsFileRepository;
import com.icia.price.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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
}
