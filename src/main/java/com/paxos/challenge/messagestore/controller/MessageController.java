package com.paxos.challenge.messagestore.controller;

import com.paxos.challenge.messagestore.dto.MessageDto;
import com.paxos.challenge.messagestore.dto.MessageDigestDto;
import com.paxos.challenge.messagestore.services.MessageService;
import com.paxos.challenge.messagestore.exception.InputValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping(path = "/messages", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageDigestDto> createMessageHash(@RequestBody MessageDto messageDto)
            throws InputValidationException {

        // Validate input.
        if (messageDto == null
                || messageDto.getMessage() == null
                || messageDto.getMessage().isEmpty()) {
            throw new InputValidationException("bad message!!!");
        }

        String hash = messageService.create(messageDto.getMessage());

        return ResponseEntity.ok().body(new MessageDigestDto(hash));
    }


    @GetMapping(path = "/messages/{hash}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageDto> getMessageHash(@PathVariable("hash") String hash)
            throws InputValidationException {

        // validate input.
        if (hash.isEmpty()) {
            throw new InputValidationException("Hash is empty in the path!!!");
        }

        // if message is not obtained, throw error.
        String message = messageService.getByHash(hash)
                .orElseThrow(() -> new EmptyResultDataAccessException("No message found for hash: " + hash, 1));

        return ResponseEntity.ok(new MessageDto(message));
    }

}
