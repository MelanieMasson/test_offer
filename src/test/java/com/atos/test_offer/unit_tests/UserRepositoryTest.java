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
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        user.setBirthday(new Date(df.parse("1901-01-01").getTime()));

        return user;
    }

    @Test
    public void findAllUsers() throws ParseException {
        int nbUsers = 3;
        //int i = 0;
        UserEntity user;

        for (int i = 0; i < nbUsers; i++) {
            user = addNewUser(" Test findAllUsers method ");
            userRepository.save(user);
        }
        assertTrue(IterableUtil.sizeOf(userRepository.findAll()) >= nbUsers);
    }

    @Test
    public void findUserById() throws ParseException, InvalidObjectException {
        UserEntity user = addNewUser(" Test findUserById method ");
        userRepository.save(user);
        assertNotNull(userRepository.findById(user.getId()));
    }

    @Test
    public void saveUser() throws ParseException {
        UserEntity user = addNewUser(" Test saveUser method ");
        user = userRepository.save(user);
        assertNotEquals(user.getId(), 0);
    }
}