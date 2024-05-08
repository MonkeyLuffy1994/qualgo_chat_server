package com.qualgo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class MessageResponse<T> {
    private String message;
    private int status;
    private T data;

    public static MessageResponse<Void> ofSuccess() {
        MessageResponse<Void> result = new MessageResponse<>();
        result.status = HttpStatus.OK.value();
        result.message = HttpStatus.OK.name();
        return result;
    }

    public static <T> MessageResponse<T> ofSuccess(T data) {
        MessageResponse<T> result = new MessageResponse<>();
        result.status = HttpStatus.OK.value();
        result.message = HttpStatus.OK.name();
        result.data = data;
        return result;
    }

    public static MessageResponse<Void> ofError(HttpStatus status) {
        MessageResponse<Void> result = new MessageResponse<>();
        result.status  = status.value();
        result.message = status.name();
        return result;
    }

}
