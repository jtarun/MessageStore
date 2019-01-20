package com.paxos.challenge.messagestore.services.hash;


import java.security.NoSuchAlgorithmException;

public class HashServiceFactory {

    public static IHashService getHashService(String algo) throws NoSuchAlgorithmException {
        IHashService hashService;

        switch (algo) {
            case "SHA-256":
                hashService = new SHAHashService();
                break;
            default:
                throw new NoSuchAlgorithmException();
        }

        return hashService;
    }

}
