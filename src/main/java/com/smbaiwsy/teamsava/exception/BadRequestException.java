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
package com.smbaiwsy.teamsava.exception;

import lombok.Getter;
/**
 * Used to handle the bad request exception.
 *
 * @author anamattuzzi-stojanovic
 *
 */
public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = -6507934884227938446L;

    @Getter
    private String errorCode;

    /**
     * Instantiates a new bad request exception.
     *
     * @param message the message
     */
    public BadRequestException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

}