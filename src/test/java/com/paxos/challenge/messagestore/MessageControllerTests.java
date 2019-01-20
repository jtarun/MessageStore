package com.paxos.challenge.messagestore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paxos.challenge.messagestore.controller.MessageController;
import com.paxos.challenge.messagestore.dto.MessageDto;
import com.paxos.challenge.messagestore.dto.MessageDigestDto;
import com.paxos.challenge.messagestore.services.MessageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Unit tests for message controller.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(MessageController.class)
public class MessageControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MessageService messageService;

    private JacksonTester<MessageDto> jsonMessageDtoConverter;
    private JacksonTester<MessageDigestDto> jsonMessageDigestConverter;

    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void returnsHashForValidMessageCreate() throws Exception {
        String message = "foo";
        String hash = "2c26b46b68ffc68ff99b453c1d30413413422d706483bfa0f98a5e886266e7ae";

        given(messageService.create(message))
                .willReturn(hash);

        MockHttpServletResponse response = postMessage(message);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        assertThat(response.getContentAsString())
                .isEqualTo(jsonMessageDigestConverter.write(new MessageDigestDto(hash)).getJson());
    }

    @Test
    public void returnsBadRequestForEmptyMessageCreate() throws Exception {
        String message = "";

        MockHttpServletResponse response = postMessage(message);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void returnsBadRequestForNullMessageCreate() throws Exception {
        String message = null;

        MockHttpServletResponse response = postMessage(message);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void returnsNotFoundForAbsentMessageFetch() throws Exception {
        String message = "NA";
        String hash = "XYZ";

        given(messageService.getByHash(hash))
                .willReturn(Optional.ofNullable(null));

        MockHttpServletResponse response = getMessage(hash);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }


    @Test
    public void returnsMessageForValidMessageFetch() throws Exception {
        String message = "foo";
        String hash = "2c26b46b68ffc68ff99b453c1d30413413422d706483bfa0f98a5e886266e7ae";

        given(messageService.getByHash(hash))
                .willReturn(Optional.of(message));

        MockHttpServletResponse response = getMessage(hash);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        assertThat(response.getContentAsString())
                .isEqualTo(jsonMessageDtoConverter.write(new MessageDto(message)).getJson());
    }

    private MockHttpServletResponse postMessage(String message) throws Exception {
        return mvc.perform(
                post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonMessageDtoConverter.write(new MessageDto(message)).getJson()))
                .andReturn().getResponse();
    }

    private MockHttpServletResponse getMessage(String hash) throws Exception {
        return mvc.perform(
                get("/messages/" + hash)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
    }
}
