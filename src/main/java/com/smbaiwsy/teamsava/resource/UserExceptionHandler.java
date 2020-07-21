/*
 * Copyright 2020 the original author.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.smbaiwsy.teamsava.resource;


import com.smbaiwsy.teamsava.dto.ErrorDto;
import com.smbaiwsy.teamsava.exception.BadRequestException;
import com.smbaiwsy.teamsava.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Controller for handling Rest exceptions.
 *
 * @author anamattuzzi-stojanovic
 */
@ControllerAdvice
@Slf4j
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle not found exception.
     *
     * @param e
     *            the exception
     * @return the error response specifying the error type and the message
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDto handleNotFoundException(UserNotFoundException e) {
        log.info("Not found exception. Error code: {} message: {}", e.getErrorCode(), e.getMessage());
        return ErrorDto.builder()
                .code(e.getErrorCode())
                .message(e.getMessage())
                .build();
    }

    /**
     * Handle not found exception.
     *
     * @param e
     *            the exception
     * @return the error response specifying the error type and the message
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDto handleBadRequestException(BadRequestException e) {
        log.info("Not found exception. Error code: {} message: {}", e.getErrorCode(), e.getMessage());
        return ErrorDto.builder()
                .code(e.getErrorCode())
                .message(e.getMessage())
                .build();
    }


    /**
     * Handles any uncaught exception as INTERNAL_SERVER_ERROR.
     *
     * @param e
     *            the exception
     * @param request
     *            the request
     * @return the error response specifying the error type and the message
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDto handleApiException(Exception e, WebRequest request) {
        log.error("Runtime exception. Message: {}", e.getMessage());
        log.error("RuntimeException. StackTrace: ", e);
        return ErrorDto.builder()
                .code("SERVER_ERROR")
                .message(e.getMessage())
                .build();
    }

}