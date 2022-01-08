package com.atos.test_offer.integration_tests;

import com.atos.test_offer.Entities.UserEntity;
import com.atos.test_offer.Repositories.UserRepository;
import com.atos.test_offer.Services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.IterableUtil;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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

@SpringBootTest
@AutoConfigureMockMvc
public class UserAPIControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    public UserService userService;

    private UserEntity addNewUser() throws ParseException {
        UserEntity user = new UserEntity();
        user.setId(1);
        user.setUsername("username");
        user.setCountry("FRANCE");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        user.setBirthday(new Date(dateFormat.parse("1901-01-01").getTime()));

        return user;
    }

    @Test
    public void getAllUsers() throws Exception {
        int nbUsers = 3;

        System.out.println(" Test getAllUsers method ");
        for (int i = 0; i < nbUsers; i++)
            userRepository.save(addNewUser());


        mockMvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.greaterThanOrEqualTo(nbUsers)));
    }

    @Test
    public void getUserById() throws Exception {
        UserEntity user = addNewUser();
        System.out.println(" Test getUserById method ");
        userRepository.save(user);
        mockMvc.perform(get("/api/user/" + user.getId()))
                .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value(user.getUsername()));
    }


    @Test
    public void addUser() throws Exception {
        UserEntity user = addNewUser();
        System.out.println(" Test addUser method ");

        mockMvc.perform(post("/api/user")
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username")
                        .value(user.getUsername()));
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //Attributes problems test
    //Missing attributes

    @Test
    public void addWithoutUsername() throws Exception {
        UserEntity user = addNewUser();
        System.out.println(" Test addUser (username null) ");
        user.setUsername(null);

        mockMvc.perform(post("/api/user").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andExpect(status().reason(" The user must have a username. "));
    }

    @Test
    public void addWithoutCountry() throws Exception {
        UserEntity user = addNewUser();
        System.out.println(" Test addUser (country null) ");
        user.setCountry(null);

        mockMvc.perform(post("/api/user")
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason(" The user must have a country. "));
    }

    @Test
    public void addWithoutBirthday() throws Exception {
        UserEntity user = addNewUser();
        System.out.println(" Test addUser method (birthday null) ");
        user.setBirthday(null);

        mockMvc.perform(post("/api/user")
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason(" The user must have a birthday (date format : yyyy-mm-dd). "));
    }


    //Invalid attribute
    @Test
    public void addInvalidUsername() throws Exception {
        UserEntity user = addNewUser();
        System.out.println(" Test addUser method (invalid username) ");
        user.setUsername("1");

        mockMvc.perform(post("/api/user").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andExpect(status().reason(" The size of the username must be between 2 and 30 characters. "));
    }

    @Test
    public void addInvalidCountry() throws Exception {
        UserEntity user = addNewUser();
        System.out.println(" Test addUser method (invalid country) ");
        user.setCountry("Fr");

        mockMvc.perform(post("/api/user").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError()).andExpect(status().reason(" Only French residents are allowed to create an account. "));
    }

    @Test
    public void addInvalidBirthday() throws Exception {
        UserEntity user = addNewUser();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        System.out.println(" Test addUser method (invalid birthday) ");
        user.setBirthday(new Date(dateFormat.parse("2222-02-02").getTime()));

        mockMvc.perform(post("/api/user").content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError()).andExpect(status().reason(" This birthday is invalid. "));
    }

    @Test
    public void addInvalidAge() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        UserEntity user = addNewUser();
        System.out.println(" Test addUser method (invalid age) ");
        user.setBirthday(new Date(dateFormat.parse("2010-01-01").getTime()));

        mockMvc.perform(post("/api/user").content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError()).andExpect(status().reason(" Only adult are allowed to create an account. "));
    }

    @Test
    public void addInvalidPhone() throws Exception {
        UserEntity user = addNewUser();
        System.out.println(" Test addUser method (invalid phone number) ");
        user.setPhone("1234");

        mockMvc.perform(post("/api/user").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError()).andExpect(status().reason(" This phone number is invalid (only French phone numbers are allowed). "));
    }
}
