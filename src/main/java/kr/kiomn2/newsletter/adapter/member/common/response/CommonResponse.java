package kr.kiomn2.newsletter.adapter.member.common.response;

import lombok.Getter;

@Getter
public class CommonResponse<T> {
    private Boolean isError;
    private T body;

    public CommonResponse(Boolean isError, T body) {
        this.isError = isError;
        this.body = body;
    }

    public static <T> CommonResponse<T> success(T body) {
        return new CommonResponse<>(false, body);
    }

    public static CommonResponse<String> error(String message) {
        return new CommonResponse<>(true, message);
    }
}
