package com.atos.test_offer.integration_tests;

import com.atos.test_offer.Services.UserService;
import com.atos.test_offer.Entities.UserEntity;
import org.assertj.core.util.IterableUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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

    private UserEntity addNewUser() throws ParseException {
        UserEntity user = new UserEntity();
        user.setUsername("username");
        user.setCountry("FRANCE");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-dd-mm");
        user.setBirthday(new Date(dateFormat.parse("1901-01-01").getTime()));

        return user;
    }

    @Test
    public void findAllUser() throws ParseException, InvalidObjectException {
        UserEntity user;
        int nbUsers = 3;

        for (int i = 0; i < nbUsers; i++) {
            user = addNewUser();
            userService.saveUser(user);
        }
        assertTrue(IterableUtil.sizeOf(userService.findAllUsers()) >= nbUsers);
    }

    @Test
    public void findUserById() throws ParseException, InvalidObjectException {
        UserEntity user = addNewUser();
        userService.saveUser(user);

        assertNotNull(userService.findUserById(user.getId()));
    }

    @Test
    public void saveUser() throws ParseException, InvalidObjectException {
        UserEntity user = addNewUser();
        user = userService.saveUser(user);

        assertNotEquals(user.getId(), 1);
    }
}