package com.example.springprepare.memo.dto;

import com.example.springprepare.memo.entity.Memo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemoRequestDto {
    private String username;
    private String contents;

    public MemoRequestDto(Memo memo) {
        this.username = memo.getUsername();
        this.contents = memo.getContents();
    }
}
