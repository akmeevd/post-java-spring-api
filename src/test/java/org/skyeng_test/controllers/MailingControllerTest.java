package org.skyeng_test.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyeng_test.dto.MailingDto;
import org.skyeng_test.models.Mailing;
import org.skyeng_test.models.Routing;
import org.skyeng_test.models.Status;
import org.skyeng_test.models.Type;
import org.skyeng_test.services.MailingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MailingController.class)
public class MailingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MailingService mailingService;
    private final JSONObject mailingJson = new JSONObject();
    private final Mailing mailing = new Mailing();
    private final Routing routing = new Routing();

    @BeforeEach
    public void setup() throws JSONException, IOException {
        mailing.setId(1L);
        mailing.setType(Type.LETTER);
        mailing.setStatus(Status.REGISTERED);
        routing.setId(1L);
    }

    @Test
    public void create() throws Exception {
        Mockito.doReturn(mailing).when(mailingService).create(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/mailings")
                        .content(mailingJson.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mailing.getId()))
                .andExpect(jsonPath("$.type").value(mailing.getType().toString()));
    }

    @Test
    public void start() throws Exception {
        Mockito.doReturn(routing).when(mailingService).start(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.patch("/mailings/routing/" + routing.getId() + "/start"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(routing.getId()));
    }
    @Test
    public void toIntPost() throws Exception {
        Mockito.doReturn(routing).when(mailingService).toIntPost(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.patch("/mailings/routing/" + routing.getId() + "/to-int-post"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(routing.getId()));
    }
    @Test
    public void fromIntPost() throws Exception {
        Mockito.doReturn(routing).when(mailingService).fromIntPost(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.patch("/mailings/routing/" + routing.getId() + "/from-int-post"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(routing.getId()));
    }
    @Test
    public void finish() throws Exception {
        Mockito.doReturn(routing).when(mailingService).finish(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.patch("/mailings/routing/" + routing.getId() + "/finish"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(routing.getId()));
    }

    @Test
    public void findRouting() throws Exception {
        Mockito.doReturn(routing).when(mailingService).findRouting(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.get("/mailings/" + mailing.getId() + "/routing"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(routing.getId()));
    }

    @Test
    public void getInfo() throws Exception {
        MailingDto mailingDto = new MailingDto();
        mailingDto.setStatus(Status.REGISTERED);
        Mockito.doReturn(mailingDto).when(mailingService).findInfo(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.get("/mailings/" + mailing.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(mailingDto.getStatus().toString()));
    }
}
