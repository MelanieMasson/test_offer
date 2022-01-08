package com.atos.test_offer.integration_tests;

import com.atos.test_offer.Entities.UserEntity;
import com.atos.test_offer.Repositories.UserRepository;
import com.atos.test_offer.Services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.IterableUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserAPIControllerTest {

    @MockBean
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    public UserService userService;

    private UserEntity addNewUser(String name) throws ParseException {
        UserEntity user = new UserEntity();
        user.setUsername(name);
        user.setCountry("FRANCE");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-dd-mm");
        user.setBirthday(new Date(dateFormat.parse("1901-01-01").getTime()));

        return user;
    }

    @Test
    public void getAllUsers() throws Exception {
        int nbUsers = 3;

        for (int i = 0; i < nbUsers; i++)
            userRepository.save(addNewUser(" Test getAllUsers method, user n°" + i + " "));


        assertTrue(IterableUtil.sizeOf(userService.findAllUsers()) >= nbUsers);
    }

    /*
    @Test
    public void getUserById() throws Exception {
        UserEntity user = addNewUser(" Test getUserById method ");
        userRepository.save(user);
        mockMvc.perform(get("/api/user/" + user.getId())).andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value(user.getUsername()));
    }*/

    @Test
    public void getUserById() throws Exception {
        UserEntity user = new UserEntity();
        user.setUsername("username");
        user.setCountry("FRANCE");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-dd-mm");
        user.setBirthday(new Date(dateFormat.parse("1901-01-01").getTime()));

        userRepository.save(user);
        mockMvc.perform(get("/api/user/" + user.getId())).andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(user.getUsername()));
    }


    @Test
    public void addUser() throws Exception {
        UserEntity user = addNewUser(" Test addUser method ");

        mockMvc.perform(post("/api/user").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.username").value(user.getUsername()));
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //Bad request

    @Test
    public void getUserNonExistent() throws Exception {
        mockMvc.perform(get("/api/user/u")).andExpect(status().is4xxClientError()).andExpect(status().reason(" This user don't exist. "));
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //Attributes problems test
    //Missing attributes

    @Test
    public void addWithoutUsername() throws Exception {
        UserEntity user = addNewUser(" Test addUser method (username null) ");
        user.setUsername(null);

        mockMvc.perform(post("/api/user").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError()).andExpect(status().reason(" No username entered. "));
    }

    @Test
    public void addWithoutCountry() throws Exception {
        UserEntity user = addNewUser(" Test saveUser method (country null) ");
        user.setCountry(null);

        mockMvc.perform(post("/api/user").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError()).andExpect(status().reason(" No country entered. "));
    }

    @Test
    public void addWithoutBirthday() throws Exception {
        UserEntity user = addNewUser(" Test addUser method (birthday null) ");
        user.setBirthday(null);

        mockMvc.perform(post("/api/user").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError()).andExpect(status().reason(" No birthday entered (format required : yyyy-mm-dd). "));
    }


    //Invalid attribute
    @Test
    public void addInvalidUsername() throws Exception {
        UserEntity user = addNewUser(" Test addUser method (invalid username) ");
        user.setUsername("1");

        mockMvc.perform(post("/api/user").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError()).andExpect(status().reason(" The size of the username must be between 2 and 30 letters, without special characters. "));
    }

    @Test
    public void addInvalidCountry() throws Exception {
        UserEntity user = addNewUser(" Test addUser method (invalid country) ");
        user.setCountry("Fr");

        mockMvc.perform(post("/api/user").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError()).andExpect(status().reason(" Only French residents are allowed to create an account. "));
    }

    @Test
    public void addInvalidBirthday() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd-");
        UserEntity user = addNewUser(" Test addUser method (invalid birthday) ");
        user.setBirthday(new Date(dateFormat.parse("2222-02-02").getTime()));

        mockMvc.perform(post("/api/user").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError()).andExpect(status().reason(" This birthday is invalid. "));
    }

    @Test
    public void addInvalidAge() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd-");
        UserEntity user = addNewUser(" Test addUser method (invalid age) ");
        user.setBirthday(new Date(dateFormat.parse("2010-01-01").getTime()));

        mockMvc.perform(post("/api/user").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError()).andExpect(status().reason(" Only adult are allowed to create an account. "));
    }

    @Test
    public void addInvalidPhone() throws Exception {
        UserEntity user = addNewUser(" Test addUser method (invalid phone number) ");
        user.setPhone("1234");

        mockMvc.perform(post("/api/user").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError()).andExpect(status().reason(" This phone number is invalid (only French phone numbers are allowed). "));
    }
}
