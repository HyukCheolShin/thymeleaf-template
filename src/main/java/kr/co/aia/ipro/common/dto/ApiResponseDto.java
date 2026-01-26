package kr.co.aia.ipro.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiResponseDto<T> {
    private String code;
    private String message;
    private T data;

    public ApiResponseDto(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponseDto<T> success(T data) {
        return new ApiResponseDto<>("SUCCESS", "요청이 성공적으로 처리되었습니다.", data);
    }

    public static <T> ApiResponseDto<T> success() {
        return new ApiResponseDto<>("SUCCESS", "요청이 성공적으로 처리되었습니다.", null);
    }

    public static <T> ApiResponseDto<T> error(String code, String message) {
        return new ApiResponseDto<>(code, message, null);
    }
}
