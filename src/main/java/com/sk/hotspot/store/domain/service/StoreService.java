package com.sk.hotspot.store.domain.service;

import com.sk.hotspot.store.application.dto.StoreDto;
import com.sk.hotspot.store.domain.entity.Store;

import java.util.List;

public interface StoreService {
    List<Store> findStoreByCurrentLocation(float x, float y);
    Store registerStoreInfo(StoreDto store);
    Store findStoreByName(String name);
    List<Store> findStoreByCategory(String category);
}