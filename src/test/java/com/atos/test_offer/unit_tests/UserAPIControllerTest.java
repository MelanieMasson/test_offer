package com.atos.test_offer.unit_tests;

import com.atos.test_offer.APIController.UserAPIController;
import com.atos.test_offer.Entities.UserEntity;
import com.atos.test_offer.Services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserAPIController.class)
class UserAPIControllerTest {

    @MockBean
    UserService userService;
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    private UserEntity addNewUser(String test) throws ParseException {
        UserEntity user = new UserEntity();

        user.setUsername("username");
        user.setCountry("FRANCE");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        user.setBirthday(new Date(dateFormat.parse("1901-01-01").getTime()));

        return user;
    }

    @Test
    public void getAllUsers() throws Exception {
        List<UserEntity> users = new ArrayList<UserEntity>();
        int nbUsers = 3;

        for(int i = 0; i < nbUsers; i++){
            users.add(addNewUser("Test getAllUsers method"));
        }

        when(userService.findAllUsers()).thenReturn(users);
        mockMvc.perform(get("/api/user")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(nbUsers));
    }

    @Test
    public void getUserById() throws Exception {
        UserEntity user = addNewUser("Test findUserById method");

        when(userService.findUserById(any(int.class))).thenReturn(user);
        mockMvc.perform(get("/api/user/1")).andExpect(status().isOk()).andExpect(jsonPath("$.username").value(user.getUsername()));
    }

    @Test
    public void addUser() throws Exception {
        UserEntity user = addNewUser("Test saveUser method");

        when(userService.saveUser(any(UserEntity.class))).thenReturn(user);
        mockMvc.perform(post("/api/user").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.username").value(user.getUsername()));

    }
}