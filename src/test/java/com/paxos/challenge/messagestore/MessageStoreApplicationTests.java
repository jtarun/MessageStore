package com.paxos.challenge.messagestore;

import com.paxos.challenge.messagestore.dto.MessageDto;
import com.paxos.challenge.messagestore.dto.MessageDigestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class MessageStoreApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void contextLoads() {
    }

    @Test
    public void returnsHashForValidMessageCreate() {
        String message = "foo";
        String hash = "2c26b46b68ffc68ff99b453c1d30413413422d706483bfa0f98a5e886266e7ae";

        ResponseEntity<MessageDigestDto> response = postMessage(message);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

        assertThat(new MessageDigestDto(hash).equals(response.getBody())).isEqualTo(true);
    }

    @Test
    public void returnsBadRequestForEmptyMessageCreate() {
        String message = "";

        ResponseEntity<Void> response = postMessageWithoutResponse(message);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void returnsBadRequestForNullMessageCreate() {
        String message = null;

        ResponseEntity<Void> response = postMessageWithoutResponse(message);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void returnsNotFoundForAbsentMessageFetch() {
        String hash = "abc";

        ResponseEntity<Void> response = getMessageWithoutResponse(hash);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    @Test
    public void returnsMessageForValidMessageFetch() throws Exception {
        String message = "foo";
        String hash = "2c26b46b68ffc68ff99b453c1d30413413422d706483bfa0f98a5e886266e7ae";

        // Post a message first.
        postMessage(message);

        // Retrieve the message using hash.
        ResponseEntity<MessageDto> response = getMessage(hash);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

        assertThat(new MessageDto(message).equals(response.getBody())).isEqualTo(true);
    }

    private ResponseEntity<MessageDigestDto> postMessage(String message) {
        return restTemplate
                .postForEntity("/messages/", new MessageDto(message), MessageDigestDto.class);
    }

    private ResponseEntity<MessageDto> getMessage(String hash) {
        return restTemplate.getForEntity("/messages/" + hash, MessageDto.class);
    }

    private ResponseEntity<Void> postMessageWithoutResponse(String message) {
        return restTemplate
                .postForEntity("/messages/", new MessageDto(message), Void.class);
    }

    private ResponseEntity<Void> getMessageWithoutResponse(String hash) {
        return restTemplate.getForEntity("/messages/" + hash, Void.class);
    }

}

