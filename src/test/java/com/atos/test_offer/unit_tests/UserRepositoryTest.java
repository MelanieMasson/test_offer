package com.atos.test_offer.unit_tests;

import com.atos.test_offer.Entities.UserEntity;
import com.atos.test_offer.Repositories.UserRepository;
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
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    private UserEntity addNewUser(String test) throws ParseException {
        UserEntity user = new UserEntity();

        user.setUsername("username");
        user.setCountry("FRANCE");
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        user.setBirthday(new Date(df.parse("01-01-1901").getTime()));

        return user;
    }

    @Test
    public void getAllUser() throws ParseException {
        int nbUsers = 3;
        //int i = 0;
        UserEntity user;

        for (int i = 0; i < nbUsers; i++) {
            user = addNewUser(" Test getAllUser method, user n°" + i + " ");
            userRepository.save(user);
        }
        assertTrue(IterableUtil.sizeOf(userRepository.findAll()) >= nbUsers);
    }

    @Test
    public void getUserById() throws ParseException, InvalidObjectException {
        UserEntity user = addNewUser(" Test getUserById method ");
        userRepository.save(user);
        assertNotNull(userRepository.findById(user.getId()));
    }

    @Test
    public void addUser() throws ParseException {
        UserEntity user = addNewUser(" Test addUser method ");
        user = userRepository.save(user);
        assertNotEquals(user.getId(), 0);
    }
}