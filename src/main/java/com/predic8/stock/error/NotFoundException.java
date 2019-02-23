package com.predic8.stock.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus( NOT_FOUND)
public class NotFoundException extends RuntimeException {
}