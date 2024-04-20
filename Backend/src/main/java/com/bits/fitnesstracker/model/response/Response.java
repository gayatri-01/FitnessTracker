package com.bits.fitnesstracker.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Response<T> {
    int status;
    String message;
    T results;
}

