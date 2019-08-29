package com.sk.hotspot.store.domain.service;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.sk.hotspot.store.application.dto.StoreDto;
import com.sk.hotspot.store.domain.entity.Store;
import com.sk.hotspot.store.domain.repository.StoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoreServiceImpl implements StoreService {

    private static final Logger log = LoggerFactory.getLogger(StoreServiceImpl.class);
    private static final Integer MAX_NUM_OF_ROWS = 1000;
    private static final double RADIUS = 1000L; // 500 meter

    @Autowired
    private StoreRepository storeRepository;

    @Value("${external.servicekey}")
    private String serviceKey;

    @Value("${external.host}")
    private String externalHost;

    private String findApiByCoord = "/storeListInRadius";
    private String findApiByCategory = "/storeListInUpjong";

//    @Override
//    public String findStoreByCurrentLocationByOpenApi(float x, float y) {
//        ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
//        RestTemplate restTemplate = new RestTemplate(requestFactory);
//
//        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(externalHost + findApiByCoord);
//        MultiValueMap<String, String> requestParamMap = new LinkedMultiValueMap();
//        requestParamMap.add("ServiceKey", serviceKey);
//        requestParamMap.add("type", "json");
//        requestParamMap.add("radius", "500");
//        requestParamMap.add("cx", String.valueOf(x));
//        requestParamMap.add("cy", String.valueOf(y));
//
//        // 음식점만 조회
//        requestParamMap.add("indsLclsCd", "Q");
//        uriComponentsBuilder.queryParams(requestParamMap);
//        URI uri = uriComponentsBuilder.build(true).toUri();
//
//        String response = restTemplate.getForObject(uri, String.class);
//        log.debug(response);
//        return response;
//    }

//    @Override
//    public Store registerStoreInfo(StoreDto store) {
//        Store entity = Store.builder()
//                .name(store.getName())
//                .category(store.getCategory())
//                .cX(store.getCX())
//                .cY(store.getCY())
//                .rdnmAdr(Optional.of(store.getRdnmAdr()).orElse(""))
//                .lnoAdr(Optional.of(store.getLnoAdr()).orElse(""))
//                .build();
//        return storeRepository.save(entity);
//    }

    @Override
    public List<Store> findStoreByName(String name) {
        return storeRepository.findByNameContaining(name);
    }

//    @Override
//    public List<Store> findStoreByCategory(String category) {
//        return storeRepository.findByCategory(category);
//    }

    @Override
    public List<Store> registerStoreInfoFromOpenApi(Integer numOfRows, Integer page) {
        Integer index = 1;
        List<Store> savedStore = new ArrayList<>();
        while(index <= page){
            String response = callOpenApiFindStoreInfoByCode(numOfRows, index);
            savedStore.addAll(parseResponseToSaveStore(response));
            index++;
        }
        storeRepository.saveAll(savedStore);
        return savedStore;
    }

    @Override
    public List<Store> findStoreByCurrentLocation(float x, float y) {
        List<Store> storeFiltered = new ArrayList<>();
        storeRepository.findAll().forEach(store -> {
            if (RADIUS >= distance(y, x, store.getCY(), store.getCX(), "meter") ){
                storeFiltered.add(store);
            }
        });
        return storeFiltered;
    }

    private String callOpenApiFindStoreInfoByCode(Integer numOfRows, Integer pageNo) {
        ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(externalHost + findApiByCategory);
        MultiValueMap<String, String> requestParamMap = new LinkedMultiValueMap();
        requestParamMap.add("ServiceKey", serviceKey);
        requestParamMap.add("type", "json");
        requestParamMap.add("divId", "indsLclsCd");
        requestParamMap.add("key", "Q");
        // 최대 요청건수 1000건
        requestParamMap.add("numOfRows", String.valueOf(numOfRows > MAX_NUM_OF_ROWS ? MAX_NUM_OF_ROWS : numOfRows));
        requestParamMap.add("pageNo", String.valueOf(pageNo));
        uriComponentsBuilder.queryParams(requestParamMap);
        URI uri = uriComponentsBuilder.build(true).toUri();

        return restTemplate.getForObject(uri, String.class);
    }

    private List<Store> parseResponseToSaveStore(String response) {
        Gson gson = new Gson();
        LinkedTreeMap resultMap = gson.fromJson(response, LinkedTreeMap.class);
        LinkedTreeMap body = (LinkedTreeMap) resultMap.get("body");
        ArrayList items = (ArrayList) body.get("items");
        ArrayList collect = (ArrayList) items.stream()
                .map(item -> {
                    LinkedTreeMap itemMap = (LinkedTreeMap) item;
                    Store storeByEid = storeRepository.findByeId(Long.valueOf(itemMap.get("bizesId").toString()));
                    if (storeByEid != null ){
                        storeByEid.setName(itemMap.get("bizesNm").toString());
                        storeByEid.setCategory(itemMap.get("indsSclsNm").toString());
                        storeByEid.setCX(Float.valueOf((itemMap.get("lon").toString())));
                        storeByEid.setCY(Float.valueOf((itemMap.get("lat").toString())));
                        storeByEid.setLnoAdr(itemMap.get("lnoAdr").toString());
                        storeByEid.setRdnmAdr(itemMap.get("rdnmAdr").toString());
                        return storeByEid;
                    }else{
                        return Store.builder()
                                .name(itemMap.get("bizesNm").toString())
                                .category(itemMap.get("indsSclsNm").toString())
                                .eId(Long.valueOf(itemMap.get("bizesId").toString()))
                                .cX(Float.valueOf((itemMap.get("lon").toString())))
                                .cY(Float.valueOf((itemMap.get("lat").toString())))
                                .lnoAdr(itemMap.get("lnoAdr").toString())
                                .rdnmAdr(itemMap.get("rdnmAdr").toString())
                                .build();
                    }
                }).collect(Collectors.toList());

        return (List<Store>) collect;
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        int connectionTimeout = 5000;
        int readTimeout = 30000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(connectionTimeout);
        clientHttpRequestFactory.setReadTimeout(readTimeout);
        return clientHttpRequestFactory;
    }

    /**
     * 두 지점간의 거리 계산
     *
     * @param lat1 지점 1 위도
     * @param lon1 지점 1 경도
     * @param lat2 지점 2 위도
     * @param lon2 지점 2 경도
     * @param unit 거리 표출단위
     * @return
     */
    private double distance(double lat1, double lon1, double lat2, double lon2, String unit) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if(unit == "meter"){
            dist = dist * 1609.344;
        }

        return (dist);
    }

    // This function converts decimal degrees to radians
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
