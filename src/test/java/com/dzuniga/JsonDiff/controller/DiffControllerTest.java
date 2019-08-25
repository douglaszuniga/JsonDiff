package com.dzuniga.JsonDiff.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dzuniga.JsonDiff.model.Result;
import com.dzuniga.JsonDiff.repository.JsonRecord;
import com.dzuniga.JsonDiff.repository.JsonRecordRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DiffControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private JsonRecordRepository repository;

    @Test
    public void shouldReturn202AcceptedWhenCallingLeft() throws Exception {
        String valid_base64_one = "eyAiZ2xvc3NhcnkiOiB7ICJ0aXRsZSI6ICJleGFtcGxlIGdsb3NzYXJ5IiwgIkdsb3NzRGl2IjogeyAidGl0bGUiOiAiUyIsICJHbG9zc0xpc3QiOiB7ICJHbG9zc0VudHJ5IjogeyAiSUQiOiAiU0dNTCIsICJTb3J0QXMiOiAiU0dNTCIsICJHbG9zc1Rlcm0iOiAiU3RhbmRhcmQgR2VuZXJhbGl6ZWQgTWFya3VwIExhbmd1YWdlIiwgIkFjcm9ueW0iOiAiU0dNTCIsICJBYmJyZXYiOiAiSVNPIDg4Nzk6MTk4NiIsICJHbG9zc0RlZiI6IHsgInBhcmEiOiAiQSBtZXRhLW1hcmt1cCBsYW5ndWFnZSwgdXNlZCB0byBjcmVhdGUgbWFya3VwIGxhbmd1YWdlcyBzdWNoIGFzIERvY0Jvb2suIiwgIkdsb3NzU2VlQWxzbyI6IFsiR01MIiwgIlhNTCJdIH0sICJHbG9zc1NlZSI6ICJtYXJrdXAiIH0gfSB9IH0gfQ==";
        mockMvc.perform(
                put("/v1/diff/{id}/left", 200L)
                        .accept(MediaType.TEXT_PLAIN)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(valid_base64_one)
        )
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void shouldReturn202AcceptedWhenCallingRight() throws Exception {
        String valid_base64_two = "eyAiZ2xvc3NhcnkiOiB7ICJ0aXRsZSI6ICJleGFtcGxlIiwgIkdsb3NzRGl2IjogeyAidGl0bGUiOiAiUyIsICJHbG9zc0xpc3QiOiB7ICJHbG9zc0VudHJ5IjogeyAiSUQiOiAiU0dNTCIsICJTb3J0QXMiOiAiU0dNTCIsICJHbG9zc1Rlcm0iOiAiU3RhbmRhcmQgR2VuZXJhbGl6ZWQgTWFya3VwIExhbmd1YWdlIiwgIkFjcm9ueW0iOiAiU0dNTCIsICJBYmJyZXYiOiAiSVNPIDg4Nzk6MTk4NiIsICJHbG9zc0RlZiI6IHsgInBhcmEiOiAiQSBtZXRhLW1hcmt1cCBsYW5ndWFnZSwgdXNlZCB0byBjcmVhdGUgbWFya3VwIGxhbmd1YWdlcyBzdWNoIGFzIERvY0Jvb2suIiwgIkdsb3NzU2VlQWxzbyI6IFsiR01MIiwgIlhNTCJdIH0sICJHbG9zc1NlZSI6ICJtYXJrdXAiIH0gfSB9IH0gfQ==";
        mockMvc.perform(
                put("/v1/diff/{id}/right", 201L)
                        .accept(MediaType.TEXT_PLAIN)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(valid_base64_two)
        )
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void shouldReturn400BadRequestWhenCallingRightWithBlankInput() throws Exception {
        mockMvc.perform(
                put("/v1/diff/{id}/right", 210L)
                        .accept(MediaType.TEXT_PLAIN)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("")
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturn404NotFoundWhenTheIdIsNotFoundInTheJsonRecord() throws Exception {
        mockMvc.perform(
                get("/v1/diff/{id}", 202L)
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn200WWhenBothJsonAreEqual() throws Exception {
        String json = "{\"status\":\"202 ACCEPTED\",\"message\":\"message\"}";
        JsonRecord record = JsonRecord.builder().id(204L).right(json)
                .left(json).build();

        repository.save(record);

        mockMvc.perform(
                get("/v1/diff/{id}", 204L)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value(Result.EQUAL.toString()));
    }

    @Test
    public void shouldReturn200WWhenBothJsonAreNotEqual() throws Exception {
        String json =   "{\"status\":\"202 ACCEPTED\",\"message\":\"message\"}";
        String json2 =  "{\"status\":\"202 ACCEPTEDS\",\"message\":\"message\"}";
        JsonRecord record = JsonRecord.builder().id(205L).right(json2)
                .left(json).build();

        repository.save(record);

        mockMvc.perform(
                get("/v1/diff/{id}", 205L)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value(Result.NOT_EQUAL_SIZE.toString()));
    }

    @Test
    public void shouldReturn200WWhenBothJsonAreNotEqualButEqualSize() throws Exception {
        String json =   "{\"status\":\"202 ACCEPTED\",\"message\":\"message\"}";
        String json2 =  "{\"status\":\"444 SOMOTHIN\",\"message\":\"message\"}";
        JsonRecord record = JsonRecord.builder().id(206L).right(json2)
                .left(json).build();

        repository.save(record);

        mockMvc.perform(
                get("/v1/diff/{id}", 206L)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value(Result.EQUAL_SIZE.toString()))
                .andExpect(jsonPath("$.insight[0].offset").value(11))
                .andExpect(jsonPath("$.insight[0].length").value(3))
                .andExpect(jsonPath("$.insight[1].offset").value(15))
                .andExpect(jsonPath("$.insight[1].length").value(8));
    }

    @Test
    public void shouldReturn406BadRequestWhenCallingLeftOrRightWithInvalidBase64String() throws Exception {
        String non_valid_base64 = "sdasdjkkjs(()93";
        mockMvc.perform(
                put("/v1/diff/{id}/right", 207L)
                        .content(non_valid_base64)
        )
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}