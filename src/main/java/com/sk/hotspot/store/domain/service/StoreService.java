package com.sk.hotspot.store.domain.service;

import com.sk.hotspot.store.application.dto.StoreDto;
import com.sk.hotspot.store.domain.entity.Store;

import java.util.List;
import java.util.Optional;

public interface StoreService {
    //String findStoreByCurrentLocationByOpenApi(float x, float y);
    //Store registerStoreInfo(StoreDto store);
    List<Store> findStoreByName(String name);
    //List<Store> findStoreByCategory(String category);

    List<Store> registerStoreInfoFromOpenApi(Integer numOfRows, Integer page);

    List<Store> findStoreByCurrentLocation(float x, float y);

}
