package com.paxos.challenge.messagestore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class MessageDto {

    @JsonProperty("message")
    private String message;

    public MessageDto(){}

    public MessageDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDto that = (MessageDto) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }
}
