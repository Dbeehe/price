package com.icia.price.dto;

import com.icia.price.entity.BoardEntity;
import com.icia.price.entity.BoardFileEntity;
import com.icia.price.entity.GoodsEntity;
import com.icia.price.entity.GoodsFileEntity;
import com.icia.price.util.UtilClass;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GoodsDTO {
    private Long id;
    private String goodsName;
    private String goodsPrice;
    private String goodsMaker;
    private String createdAt;

    private List<MultipartFile> goodsFile;
    private int fileAttached;
    private List<String> originalFileName = new ArrayList<>();
    private List<String> storedFileName = new ArrayList<>();

    public static GoodsDTO toDTO(GoodsEntity goodsEntity) {
        GoodsDTO goodsDTO = new GoodsDTO();
        goodsDTO.setId(goodsEntity.getId());
        goodsDTO.setGoodsName(goodsEntity.getGoodsName());
        goodsDTO.setGoodsPrice(goodsEntity.getGoodsPrice());
        goodsDTO.setGoodsMaker(goodsEntity.getGoodsMaker());
        goodsDTO.setCreatedAt(UtilClass.dateTimeFormat(goodsEntity.getCreatedAt()));

        if (goodsEntity.getFileAttached() == 1) {
            for (GoodsFileEntity goodsFileEntity: goodsEntity.getGoodsFileEntityList()) {
                goodsDTO.getOriginalFileName().add(goodsFileEntity.getOriginalFileName());
                goodsDTO.getStoredFileName().add(goodsFileEntity.getStoredFileName());
            }
            goodsDTO.setFileAttached(1);
        } else {
            goodsDTO.setFileAttached(0);
        }
        return goodsDTO;

    }

}
