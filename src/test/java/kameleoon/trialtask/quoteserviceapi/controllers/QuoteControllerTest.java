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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class QuoteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createQuote() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/quote/create");
        request.param("content", "Awesome tests!");
        request.param("userid", "1");

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(content().string(containsString("Awesome tests!")))
                .andExpect(status().isOk());
    }

    @Test
    public void createQuoteWithNonExistentUser() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/quote/create");
        request.param("content", "Awesome tests!");
        request.param("userid", "100");

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void createQuoteWithoutContentField() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/quote/create");
        request.param("userid", "1");

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createQuoteWithEmptyTextField() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/quote/create");
        request.param("content", "");
        request.param("userid", "1");

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateQuote() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.patch("/quote/update");
        request.param("id", "1");
        request.param("newContent", "Wow! This is amazing test!");

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(content().string(containsString("Wow! This is amazing test!")))
                .andExpect(status().isOk());
    }

    @Test
    public void updateNonExistentQuote() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.patch("/quote/update");
        request.param("id", "100");
        request.param("newContent", "Wow! This is amazing test!");

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateQuoteWithoutContent() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.patch("/quote/update");
        request.param("id", "1");
        request.param("newContent", "");

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getQuote() throws Exception {
        this.mockMvc.perform(get("/quote/get/3"))
                .andDo(print())
                .andExpect(content().string(containsString("Now or never!")))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteQuote() throws Exception {
        this.mockMvc.perform(delete("/quote/delete/2"));
        this.mockMvc.perform(get("/quote/get/2"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteNonExistentQuote() throws Exception {
        this.mockMvc.perform(delete("/quote/delete/100"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
