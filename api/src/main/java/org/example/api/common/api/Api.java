package org.example.api.common.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> { // Api 스펙의 최상위를 담당할 Api 클래스

    private Result result;

    @Valid
    private T body;


    /**
     * 성공에 대한 static OK 메서드
     */
    public static <T> Api<T> OK(T data){
        Api<T> api = new Api<>();
        api.result = Result.OK();
        api.body = data;
        return api;
    }
}
