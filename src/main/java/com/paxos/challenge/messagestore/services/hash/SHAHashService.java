package com.paxos.challenge.messagestore.services.hash;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class SHAHashService implements IHashService {

    public String getHash(String message) {

        return Hashing.sha256()
                .hashString(message, StandardCharsets.UTF_8)
                .toString();
    }

}
