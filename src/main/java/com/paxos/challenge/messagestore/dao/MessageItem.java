package com.paxos.challenge.messagestore.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Message")
public class MessageItem {
    @Id
    @Column(name = "hash")
    private String hash;

    @Column(name = "message")
    private String message;

    public MessageItem(){}

    public MessageItem(String hash, String message) {
        this.hash = hash;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
