package com.paxos.challenge.messagestore.repository;

import com.paxos.challenge.messagestore.dao.MessageItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMessageRepository extends CrudRepository<MessageItem, String> {

}
