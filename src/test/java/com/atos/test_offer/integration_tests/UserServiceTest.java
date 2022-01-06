package com.atos.test_offer.integration_tests;

import com.atos.test_offer.Services.UserService;
import com.atos.test_offer.Entities.UserEntity;
import org.assertj.core.util.IterableUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InvalidObjectException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    UserService userService;

    private UserEntity addNewUser(String test) throws ParseException {
        UserEntity user = new UserEntity();

        user.setUsername("username");
        user.setCountry("France");
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        user.setBirthday(new Date(dateFormat.parse("01-01-1901").getTime()));

        return user;
    }

    @Test
    public void getAllUser() throws ParseException, InvalidObjectException {
        UserEntity user;
        int numberUser = 3;

        for (int i = 0; i < numberUser; i++) {
            user = addNewUser("Test getAllUser method, user n°" + i);
            userService.addUser(user);
        }
        assertTrue(IterableUtil.sizeOf(userService.getAllUser()) >= numberUser);
    }

    @Test
    public void getUserById() throws ParseException, InvalidObjectException {
        UserEntity user = addNewUser("Test getUserById method");
        userService.addUser(user);

        assertNotNull(userService.getUserById(user.getId()));
    }

    @Test
    public void addUser() throws ParseException, InvalidObjectException {
        UserEntity user = addNewUser("Test addUser method");
        user = userService.addUser(user);

        assertNotEquals(user.getId(), 0);
    }
}