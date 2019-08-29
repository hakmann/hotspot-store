package com.sk.hotspot.store.domain.service;

import com.sk.hotspot.store.domain.entity.Store;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class StoreServiceImplTest {

    @Spy
    @InjectMocks
    private StoreServiceImpl storeService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findStoreInfoByNameInNormalCase() {
        final int SIZE = 3;
        Mockito.doReturn(getStoreList(SIZE)).when(storeService).findStoreByName(Mockito.any());
        List<Store> storeByName = storeService.findStoreByName(Mockito.any());

        assertThat(storeByName.size(), equalTo(SIZE));
    }

    @Test
    public void findStoreInfoByLocationInNormalCase() {
        final int SIZE = 5;
        Mockito.doReturn(getStoreList(SIZE)).when(storeService).findStoreByCurrentLocation(Mockito.anyFloat(), Mockito.anyFloat());
        List<Store> storeByLocation = storeService.findStoreByCurrentLocation(Mockito.anyFloat(), Mockito.anyFloat());

        assertThat(storeByLocation.size(), equalTo(SIZE));
    }

    private List<Store> getStoreList(int size) {
        List<Store> storeList = new ArrayList<>();
        for(int i = 1; i <= size ; i++){
            storeList.add(Store.builder().name("Test" + i).build());
        }
        return storeList;
    }
}