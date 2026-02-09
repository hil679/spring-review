package com.example.springprepare.response;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/response")
public class ResponseController {

    @GetMapping("/json/string")
    @ResponseBody
    public String helloStringJson() {
        //Java는 JSON 타입을 지원하지 않기 때문에 JSON 형태의 String 타입으로 변환해서 사용해야 합니다.
        return "{\"form\":\"JSON\", \"type\":\"text\"}";
    }

    @GetMapping("/response/json/class")
    @ResponseBody
    public Star helloClassJson() {
        return new Star("Robbie", 95);
    }
}
