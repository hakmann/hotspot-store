package com.sk.hotspot.store.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreDto {
    private String name;
    private String category;
    private float cX;
    private float cY;

    private String lnoAdr; // 지번주소
    private String rdnmAdr; // 도로명주소

}
