package com.icia.price.entity;

import com.icia.price.dto.BoardDTO;
import com.icia.price.dto.GoodsDTO;
import com.icia.price.dto.MemberDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@Table(name = "goods_table")
public class GoodsEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String goodsName;

    @Column(length = 50, nullable = false)
    private String goodsPrice;

    @Column(length = 500)
    private String goodsMaker;

    @Column
    private int fileAttached;

    @OneToMany(mappedBy = "goodsEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GoodsFileEntity> goodsFileEntityList = new ArrayList<>();

    public static GoodsEntity toSaveEntity(GoodsDTO goodsDTO) {
        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setGoodsName(goodsDTO.getGoodsName());
        goodsEntity.setGoodsPrice(goodsDTO.getGoodsPrice());
        goodsEntity.setGoodsMaker(goodsDTO.getGoodsMaker());
        return goodsEntity;
    }

    public static GoodsEntity toupdateEntity(GoodsDTO goodsDTO) {
        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setId(goodsDTO.getId());
        goodsEntity.setGoodsName(goodsDTO.getGoodsName());
        goodsEntity.setGoodsPrice(goodsDTO.getGoodsPrice());
        goodsEntity.setGoodsMaker(goodsDTO.getGoodsMaker());
        return goodsEntity;
    }

    public static GoodsEntity toSaveEntityWithFile(GoodsDTO goodsDTO) {
        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setGoodsName(goodsDTO.getGoodsName());
        goodsEntity.setGoodsPrice(goodsDTO.getGoodsPrice());
        goodsEntity.setGoodsMaker(goodsDTO.getGoodsMaker());
        goodsEntity.setFileAttached(1);
        return goodsEntity;
    }

}
