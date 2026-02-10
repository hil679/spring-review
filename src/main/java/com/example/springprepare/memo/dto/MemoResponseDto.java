package com.example.springprepare.memo.dto;

import com.example.springprepare.memo.entity.Memo;
import lombok.Getter;

@Getter
public class MemoResponseDto {
    private Long id;
    private String username;
    private String contents;

    public MemoResponseDto(Memo memo) {
        this.username = memo.getUsername();
        this.contents = memo.getContents();
    }

    public MemoResponseDto(Long id, String username, String contents) {
        this.id = id;
        this.username = username;
        this.contents = contents;
    }
}
