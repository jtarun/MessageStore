package com.paxos.challenge.messagestore.services;

import com.paxos.challenge.messagestore.dao.MessageItem;
import com.paxos.challenge.messagestore.repository.IMessageRepository;
import com.paxos.challenge.messagestore.services.hash.IHashService;

import java.util.Optional;

public class MessageService {

    private IHashService hashService;
    private IMessageRepository messageRepo;

    public MessageService(IHashService hashService, IMessageRepository messageRepo) {
        this.hashService = hashService;
        this.messageRepo = messageRepo;
    }

    public String create(String message) {
        String hash = hashService.getHash(message);
        MessageItem item = new MessageItem(hash, message);

        // persist the message
        messageRepo.save(item);

        return hash;
    }

    public Optional<String> getByHash(String hash) {
        return messageRepo.findById(hash)
                .map(MessageItem::getMessage);
    }

}
