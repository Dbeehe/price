package com.icia.price.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter(AccessLevel.PRIVATE)
@Getter
@Table(name = "goods_file_table")
public class GoodsFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private GoodsEntity goodsEntity;

    public static GoodsFileEntity toSaveBoardFile(GoodsEntity savedEntity, String originalFilename, String storedFileName) {
        GoodsFileEntity goodsFileEntity = new GoodsFileEntity();
        goodsFileEntity.setOriginalFileName(originalFilename);
        goodsFileEntity.setStoredFileName(storedFileName);
        goodsFileEntity.setGoodsEntity(savedEntity);
        return goodsFileEntity;
    }
}









