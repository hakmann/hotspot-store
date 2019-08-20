package com.sk.hotspot.store.domain.service;

import com.sk.hotspot.store.application.dto.StoreDto;
import com.sk.hotspot.store.domain.entity.Store;
import com.sk.hotspot.store.domain.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Override
    public List<Store> findStoreByCurrentLocation(float x, float y) {

        return storeRepository.findByCXAndCY(x,y);
    }

    @Override
    public Store registerStoreInfo(StoreDto store) {
        Store entity = Store.builder()
                .name(store.getName())
                .category(store.getCategory())
                .cX(store.getCX())
                .cY(store.getCY())
                .build();
        return storeRepository.save(entity);
    }

    @Override
    public Store findStoreByName(String name) {
        return storeRepository.findByName(name);
    }

    @Override
    public List<Store> findStoreByCategory(String category) {
        return storeRepository.findByCategory(category);
    }
}
