package com.sk.hotspot.store.domain.repository;

import com.sk.hotspot.store.domain.entity.Store;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface StoreRepository extends PagingAndSortingRepository<Store,Long> {
    //
    List<Store> findByCXAndCY(float cx, float cy);
    Store findByName(String name);
    List<Store> findByCategory(String category);
}
