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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createUser() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user/create");
        request.param("username", "ilya");
        request.param("email", "ilya_gorunov@mail.ru");
        request.param("password", "123456");

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(content().string(containsString("ilya")))
                .andExpect(content().string(containsString("ilya_gorunov@mail.ru")))
                .andExpect(status().isOk());
    }

    @Test
    public void loginIsBusyTest() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user/create");
        request.param("username", "Alina");
        request.param("email", "alina@mail.ru");
        request.param("password", "123456");

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void emailIsBusyTest() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user/create");
        request.param("username", "Nao");
        request.param("email", "iamnao@gmail.com");
        request.param("password", "123456");

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void getUser() throws Exception {
        this.mockMvc.perform(get("/user/get/1"))
                .andDo(print())
                .andExpect(content().string(containsString("Alina")))
                .andExpect(status().isOk());
    }

    @Test
    public void getNonExistentUser() throws Exception {
        this.mockMvc.perform(get("/user/get/100"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
