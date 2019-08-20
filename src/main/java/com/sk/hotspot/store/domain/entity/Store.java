package com.sk.hotspot.store.domain.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
@Builder
public class Store extends BaseEntity{

    private String name;
    private String category;
    private float cX;
    private float cY;
}
