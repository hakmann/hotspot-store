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
    //    {
//        "bizesId" : "26220456"
//            ,"bizesNm" : "삼성웰스토리제주다음"
//            ,"indsLclsCd" : "Q"
//            ,"indsLclsNm" : "음식"
//            ,"indsMclsCd" : "Q01"
//            ,"indsMclsNm" : "한식"
//            ,"indsSclsCd" : "Q01A01"
//            ,"indsSclsNm" : "한식/백반/한정식"
//            ,"ksicCd" : "I56111"
//            ,"ksicNm" : "한식 음식점업"
//            ,"ctprvnCd" : "50"
//            ,"ctprvnNm" : "제주특별자치도"
//            ,"signguCd" : "50110"
//            ,"signguNm" : "제주시"
//            ,"adongCd" : "5011063000"
//            ,"adongNm" : "아라동"
//            ,"ldongCd" : "5011013600"
//            ,"ldongNm" : "영평동"
//            ,"lnoCd" : "5011013600221810000"
//            ,"plotSctCd" : "1"
//            ,"plotSctNm" : "대지"
//            ,"lnoMnno" : 2181
//            ,"lnoSlno" : ""
//            ,"lnoAdr" : "제주특별자치도 제주시 영평동 2181"
//            ,"rdnmCd" : "501103349191"
//            ,"rdnm" : "제주특별자치도 제주시 첨단로"
//            ,"bldMnno" : 242
//            ,"bldSlno" : ""
//            ,"bldMngNo" : "5011013600121810000000001"
//            ,"bldNm" : ""
//            ,"rdnmAdr" : "제주특별자치도 제주시 첨단로 242"
//            ,"oldZipcd" : "690140"
//            ,"newZipcd" : "63309"
//            ,"dongNo" : ""
//            ,"flrNo" : "1"
//            ,"hoNo" : ""
//            ,"lon" : 126.570549095293
//            ,"lat" : 33.4506673573279
//    }
}
