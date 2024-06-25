package org.example.api.common.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result {

    private Integer resultCode;
    private String resultMessage;
    private String resultDescription;

    /**
     * 성공에 대한 Result 정보를 반환 하는 static 메서드
     */
    public static Result OK(){
        return Result
                .builder()
                .resultCode(200)
                .resultMessage("OK")
                .resultDescription("성공")
                .build();
    }
}
