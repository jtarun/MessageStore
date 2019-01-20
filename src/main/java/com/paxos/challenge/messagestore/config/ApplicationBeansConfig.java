package com.paxos.challenge.messagestore.config;

import com.paxos.challenge.messagestore.repository.IMessageRepository;
import com.paxos.challenge.messagestore.services.hash.IHashService;
import com.paxos.challenge.messagestore.services.hash.HashServiceFactory;
import com.paxos.challenge.messagestore.services.MessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import java.security.NoSuchAlgorithmException;

@SpringBootConfiguration
public class ApplicationBeansConfig {

    @Bean
    public MessageService getMessageService(IHashService hashService,
                                            IMessageRepository messageRepo) {
        return new MessageService(hashService, messageRepo);
    }

    @Bean
    public IHashService getHashService(@Value("${app.hash.algo}") String algorithm) throws NoSuchAlgorithmException {
        return HashServiceFactory.getHashService(algorithm);
    }

}
