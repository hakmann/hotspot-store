package com.sk.hotspot.store.domain.repository;

import com.sk.hotspot.store.domain.entity.Store;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface StoreRepository extends PagingAndSortingRepository<Store,Long> {
    //
    @Query("SELECT s FROM Store s WHERE s.cX BETWEEN ?1 AND ?2 AND s.cY BETWEEN ?3 AND ?4")
    List<Store> findByLocation (float cx1, float cx2, float cx3, float cx4);
    List<Store> findByNameContaining(String name);
    List<Store> findByCategory(String category);

    Store findByeId(Long eId);

}
