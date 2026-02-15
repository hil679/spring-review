package com.example.resttemlateclient.service;

import com.example.resttemlateclient.dto.ItemDto;
import com.example.resttemlateclient.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RestTemplateService {
    /*
        private final해도 resttemplate 처음 자동 등록하지 않음
        따라서 private final + 생성자 주입으로 불가능.
        RestTemplateBuilder사용
     */
    private final RestTemplate restTemplate;

    public RestTemplateService(RestTemplateBuilder restTemplateBuilder) {
        //builder는 안에 코드 보면 RestTemplate객체 반환
        this.restTemplate = restTemplateBuilder.build();
    }

    public ItemDto getCallObject(String query) {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:8081")
                .path("/api/server/get-call-obj")
                .queryParam("query", query) // http://~?query -> 이 ?부분
                .encode()
                .build()
                .toUri();
        log.info("uri = " + uri);

        /*
        get방식으로 해당 uri에 request(서버에 get방식으로 요청)
        2번 파라미터: 응답으로 받을 객체(responseDto)

        ResponseEntity: springframework http에 만들어져있음.

        하나의 row 객체만 가져올 때는 직접 역직렬화 가능
         */
        ResponseEntity<ItemDto> responseEntity = restTemplate.getForEntity(uri, ItemDto.class);

        log.info("statusCode = " + responseEntity.getStatusCode());
        /*
        getBody쪽에 response으로 넘긴 dto들어있음.
         */
        return responseEntity.getBody();
    }

    public List<ItemDto> getCallList() {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:8081")
                .path("/api/server/get-call-list")
                .encode()
                .build()
                .toUri();
        log.info("uri = " + uri);

        //여러 객체 복합적으로 한 번에 가져오기 위해 String으로 다 받음. 이후 다시 변환
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

        log.info("statusCode = " + responseEntity.getStatusCode());
        log.info("Body = " + responseEntity.getBody());

        // 여기가 각 객체로 변환하는 코드 구현
        return fromJSONtoItems(responseEntity.getBody());
    }

    /*
    넘어오는 형식(중첩 JSON):
    {
        "items": [
        {"title":"Mac", "price":3888000},
        {"title":"iPad", "price":1230000},
        ...
                ]
    }
    */
    public List<ItemDto> fromJSONtoItems(String responseEntity) {
        JSONObject jsonObject = new JSONObject(responseEntity);
        JSONArray items  = jsonObject.getJSONArray("items");
        List<ItemDto> itemDtoList = new ArrayList<>();

        for (Object item : items) {
            ItemDto itemDto = new ItemDto((JSONObject) item);
            itemDtoList.add(itemDto);
        }

        return itemDtoList;
    }

    public ItemDto postCall(String query) {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:8081")
                .path("/api/server/post-call/{query}")
                .encode()
                .build()
                .expand(query)
                .toUri();
        log.info("uri = " + uri);

        User user = new User("Robbie", "1234");

        /*
        post 요청 시 body에 넣을 데이터 객체
        전달받은 데이터와 맵핑할 거 (response)
         */
        ResponseEntity<ItemDto> responseEntity = restTemplate.postForEntity(uri, user, ItemDto.class);

        log.info("statusCode = " + responseEntity.getStatusCode());

        return responseEntity.getBody();
    }

    public List<ItemDto> exchangeCall(String token) {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:8081")
                .path("/api/server/exchange-call")
                .encode()
                .build()
                .toUri();
        log.info("uri = " + uri);

        User user = new User("Robbie", "1234");

        //User객체 바로 안 보내고, RequestEntity객체로 만들어서 보냄.
        RequestEntity<User> requestEntity = RequestEntity
                .post(uri)
                .header("X-Authorization", token)
                .body(user);

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        return fromJSONtoItems(responseEntity.getBody());
    }
}
