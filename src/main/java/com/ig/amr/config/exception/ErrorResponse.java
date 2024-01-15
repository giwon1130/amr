package com.ig.amr.config.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자를 protected로 설정하여 외부에서 직접 인스턴스 생성을 방지합니다.
public class ErrorResponse {
    private String message; // 오류 메시지
    private int status; // HTTP 상태 코드
    private List<FieldError> errors; // 필드별 오류 목록

    // 에러 코드에 따른 ErrorResponse 객체를 생성하는 메소드
    private ErrorResponse(final ErrorCode code) {
        this.message = code.getMessage(); // 에러 메시지 설정
        this.status = code.getStatus().value(); // HTTP 상태 코드 설정
    }

    // 에러 코드와 필드 오류 목록을 포함하는 ErrorResponse 객체를 생성하는 메소드
    private ErrorResponse(final ErrorCode code, final List<FieldError> errors) {
        this.message = code.getMessage();
        this.status = code.getStatus().value();
        this.errors = errors;
    }

    // 에러 코드에 해당하는 ErrorResponse 객체를 생성하는 정적 메소드
    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }

    // 에러 코드와 BindingResult 객체를 사용하여 ErrorResponse 객체를 생성하는 정적 메소드
    public static ErrorResponse of(final ErrorCode code, final BindingResult bindingResult) {
        return new ErrorResponse(code, FieldError.of(bindingResult));
    }

    // FieldError 클래스는 특정 필드에 대한 오류 정보를 담는 클래스입니다.
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field; // 오류가 발생한 필드 이름
        private String value; // 해당 필드에서 거부된 값

        // 필드 이름과 거부된 값을 사용하여 FieldError 객체를 생성하는 생성자
        private FieldError(final String field, final String value) {
            this.field = field;
            this.value = value;
        }

        // BindingResult 객체로부터 FieldError 목록을 생성하는 정적 메소드
        private static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                              .map(error -> new FieldError(
                                  error.getField(),
                                  error.getRejectedValue() == null ? "" : error.getRejectedValue().toString()))
                              .collect(Collectors.toList());
        }
    }
}
