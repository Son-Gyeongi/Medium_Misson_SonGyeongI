package com.ll.medium.global.rsData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class RsData<T> { // Rs는 보고서라는 뜻
    private final String resultCode;
    private final String msg;
    private T data;

    // 성공 메시지
    public boolean isSuccess() {
        return resultCode.startsWith("S-"); // resultCode가 S로 시작하면 성공이다.
    }

    // 실패 메시지
    public boolean isFail() {
        return !isSuccess();
    }
}
