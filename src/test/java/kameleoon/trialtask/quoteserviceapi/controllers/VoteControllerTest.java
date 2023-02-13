package kameleoon.trialtask.quoteserviceapi.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class VoteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void vote() throws Exception{
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/quote/vote");
        request.param("quoteId", "10");
        request.param("userId", "5");
        request.param("isUpvote", "false");

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(content().string(containsString("\"rating\":-1")))
                .andExpect(status().isOk());
    }

    @Test
    public void voteForNonExistentQuote() throws Exception{
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/quote/vote");
        request.param("quoteId", "100");
        request.param("userId", "5");
        request.param("isUpvote", "false");

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void voteByNonExistentUser() throws Exception{
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/quote/vote");
        request.param("quoteId", "1");
        request.param("userId", "100");
        request.param("isUpvote", "false");

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getVoteInfo() throws Exception {
        this.mockMvc.perform(get("/quote/6/voteinfo"))
                .andDo(print())
                .andExpect(content().string(containsString("\"rating\":3")))
                .andExpect(status().isOk());
    }

    @Test
    public void getNonExistentVoteInfo() throws Exception {
        this.mockMvc.perform(get("/quote/100/voteinfo"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getTopQuotes() throws Exception {
        this.mockMvc.perform(get("/quote/best/10"))
                .andDo(print())
                .andExpect(content().string(containsString("Advice is like snow;")))
                .andExpect(status().isOk());
    }
}
