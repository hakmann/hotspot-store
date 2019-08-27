# Hotspot-Store Application
###API 

- Register store info from openapi  
호출예시)  
curl -X POST "http://hotspot.cloudzcp.io/store/api/v1/store/register/openapi/rows/1000?pageNo=1" -H "accept: */*"  

TODO: 
1. 타임아웃 문제 해결
2. Batch
3. Bulk Update or Insert

- Find store list by current location  
호출예시)  
curl -X GET "http://hotspot.cloudzcp.io/store/api/v1/store/find/location/126.73/37.53" -H "accept: */*"  

TODO:  
1. 좌표 값이 주어졌을 때 반경을 좀더 정확하게 계산하도록 수정. (현재는 단순히 일정 값을 +- 하여 가져옴.)

