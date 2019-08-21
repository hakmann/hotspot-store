package com.sk.hotspot.store.application;

import com.sk.hotspot.store.application.dto.StoreDto;
import com.sk.hotspot.store.domain.entity.Store;
import com.sk.hotspot.store.domain.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/store")
public class StoreController implements StoreService{

    @Autowired
    private StoreService storeService;

    @Override
    @GetMapping("/find/location/{x}/{y}")
    public String findStoreByCurrentLocation(@PathVariable("x") float x, @PathVariable("y") float y) {
        return storeService.findStoreByCurrentLocation(x,y);
    }

    @Override
    @PostMapping(value = "/register")
    public Store registerStoreInfo(@RequestBody StoreDto store) {
        return storeService.registerStoreInfo(store);
    }

    @Override
    @GetMapping(value = "/find/name/{store}")
    public Store findStoreByName(@PathVariable("store") String name) {
        return storeService.findStoreByName(name);
    }

    @Override
    @GetMapping(value = "/find/category/{category}")
    public List<Store> findStoreByCategory(@PathVariable("category") String category) {
        return storeService.findStoreByCategory(category);
    }

    @Override
    @PostMapping(value = "/register/external/rows/{numOfRows}")
    public List<Store> registerStoreInfoFromOpenApi(@PathVariable("numOfRows") Integer numOfRows) {
        storeService.registerStoreInfoFromOpenApi(numOfRows);
        return null;
    }
}
