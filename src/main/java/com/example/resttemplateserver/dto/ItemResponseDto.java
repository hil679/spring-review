package com.example.resttemplateserver.dto;

import com.example.resttemplateserver.entity.Item;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ItemResponseDto {
    //중요!!
    // Service에서 ItemResponseDto list로 반환하는게 아니라,
    // ItemResponseDto만들고 내부에 java collection 만들고 거기에 데이터 넣어서 쓰는 방식도 자주 사용
    // 이 responseDto도 꼭 알아두기!
    private final List<Item> items = new ArrayList<>();

    public void setItems(Item item) {
        items.add(item);
    }
}
