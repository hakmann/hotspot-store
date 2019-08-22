package com.sk.hotspot.store.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Entity
@Data
@Builder
@EqualsAndHashCode(callSuper=true)
public class Store extends BaseEntity{

    private String name;
    private String category; // indsSclsCd
    private float cX;
    private float cY;

    private Long eId;
    private String lnoAdr; // 지번주소
    private String rdnmAdr; // 도로명주소

    public Store(){}

    public Store(String name, String category, float cX, float cY, Long eId, String lnoAdr, String rdnmAdr) {
        this.name = name;
        this.category = category;
        this.cX = cX;
        this.cY = cY;
        this.eId = eId;
        this.lnoAdr = lnoAdr;
        this.rdnmAdr = rdnmAdr;
    }

}
