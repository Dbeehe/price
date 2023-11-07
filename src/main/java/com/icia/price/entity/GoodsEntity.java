package com.icia.price.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

}
