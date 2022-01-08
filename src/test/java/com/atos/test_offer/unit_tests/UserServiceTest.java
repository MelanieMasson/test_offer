package com.atos.test_offer.unit_tests;

import com.atos.test_offer.Entities.UserEntity;
import com.atos.test_offer.Repositories.UserRepository;
import com.atos.test_offer.Services.UserService;
import org.assertj.core.util.IterableUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.InvalidObjectException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    private UserEntity addNewUser() throws ParseException {
        UserEntity user = new UserEntity();

        user.setUsername("username");
        user.setCountry("FRANCE");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        user.setBirthday(new Date(dateFormat.parse("1901-01-01").getTime()));

        return user;
    }

    @Test
    public void findAllUsers() throws ParseException {
        int nbUsers = 3;
        List<UserEntity> users = new ArrayList<UserEntity>();
        UserEntity user;
        //UserEntity user = new UserEntity();

        for (int i = 0; i < nbUsers; i++){
            user = addNewUser();
            users.add(user);
        }

        when(userRepository.findAll()).thenReturn(users);
        assertEquals(IterableUtil.sizeOf(userService.findAllUsers()), nbUsers);
    }

    @Test
    public void findUserById() throws ParseException {
        UserEntity user = addNewUser();

        when(userRepository.findById(any(int.class))).thenReturn(Optional.of(user));
        assertNotNull(userService.findUserById(user.getId()));
    }


    @Test
    public void saveUser() throws InvalidObjectException, ParseException {
        UserEntity user = addNewUser();

        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        assertNotEquals(userService.saveUser(user).getId(), 1);
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //Attributes problems test
    //Missing attributes

    @Test
    public void addWithoutUsername() throws ParseException {
        UserEntity user = addNewUser();
        user.setUsername(null);

        assertThrows(InvalidObjectException.class, () -> {
            userService.saveUser(user).getId();

        });
    }

    @Test
    public void addWithoutCountry() throws ParseException {
        UserEntity user = addNewUser();
        user.setCountry(null);

        assertThrows(InvalidObjectException.class, () -> {
            userService.saveUser(user).getId();
        });
    }

    @Test
    public void addWithoutBirthdate() throws ParseException {
        UserEntity user = addNewUser();
        user.setBirthday(null);

        assertThrows(InvalidObjectException.class, () -> {
            userService.saveUser(user).getId();
        });
    }


    //Invalid attribute


    @Test
    public void addInvalidUsername() throws ParseException {
        UserEntity user = addNewUser();
        user.setPhone("1");

        assertThrows(InvalidObjectException.class, () -> {
            userService.saveUser(user).getId();
        });
    }

    @Test
    public void addInvalidCountry() throws ParseException {
        UserEntity user = addNewUser();
        user.setCountry("Fr");

        assertThrows(InvalidObjectException.class, () -> {
            userService.saveUser(user).getId();
        });
    }

    @Test
    public void addInvalidBirthday() throws ParseException {
        UserEntity user = addNewUser();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        user.setBirthday(new Date(dateFormat.parse("2222-02-02").getTime()));


        assertThrows(InvalidObjectException.class, () -> {
            userService.saveUser(user).getId();
        });
    }

    @Test
    public void addInvalidAge() throws ParseException {
        UserEntity user = addNewUser();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        user.setBirthday(new Date(dateFormat.parse("2011-11-11").getTime()));

        assertThrows(InvalidObjectException.class, () -> {
            userService.saveUser(user).getId();
        });
    }

    @Test
    public void addInvalidPhone() throws ParseException {
        UserEntity user = addNewUser();
        user.setPhone("1234");

        assertThrows(InvalidObjectException.class, () -> {
            userService.saveUser(user).getId();
        });
    }

}