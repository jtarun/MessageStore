package com.paxos.challenge.messagestore.dto;

import java.util.Objects;

public class MessageDigestDto {
    private String digest;

    public MessageDigestDto(){}

    public MessageDigestDto(String digest) {
        this.digest = digest;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDigestDto that = (MessageDigestDto) o;
        return Objects.equals(digest, that.digest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(digest);
    }
}
