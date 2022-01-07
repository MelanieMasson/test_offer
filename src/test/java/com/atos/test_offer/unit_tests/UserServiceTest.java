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

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    private UserEntity addNewUser(String test) throws ParseException {
        UserEntity user = new UserEntity();

        user.setUsername("username");
        user.setCountry("FRANCE");
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        user.setBirthday(new Date(dateFormat.parse("01-01-1901").getTime()));

        return user;
    }

    @Test
    public void getAllUser() throws ParseException {
        int nbUsers = 3;
        List<UserEntity> users = new ArrayList<UserEntity>();
        UserEntity user;
        //UserEntity user = new UserEntity();

        for (int i = 0; i < nbUsers; i++){
            user = addNewUser(" Test getAllUser method, user n°" + i + " ");
            users.add(user);
        }

        when(userRepository.findAll()).thenReturn(users);
        assertEquals(IterableUtil.sizeOf(userService.getAllUser()), nbUsers);
    }

    @Test
    public void getUserById() throws ParseException {
        UserEntity user = addNewUser(" Test getUserById method ");

        when(userRepository.findById(any(int.class))).thenReturn(java.util.Optional.of(user));
        assertNotNull(userService.getUserById(user.getId()));
    }


    @Test
    public void addUser() throws InvalidObjectException, ParseException {
        UserEntity user = addNewUser(" Test addUser method ");

        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        assertNotEquals(userService.addUser(user).getId(), 0);
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //Attributes problems test
    //Missing attributes

    @Test
    public void addWithoutUsername() throws ParseException {
        UserEntity user = addNewUser(" Test addUser method (username null) ");
        user.setUsername(null);

        assertThrows(InvalidObjectException.class, () -> {
            userService.addUser(user).getId();
            //throw new InvalidObjectException(" No username entered. ");
        });
    }

    @Test
    public void addWithoutCountry() throws ParseException {
        UserEntity user = addNewUser(" Test addUser method (country null) ");
        user.setCountry(null);

        assertThrows(InvalidObjectException.class, () -> {
            userService.addUser(user).getId();
            //throw new InvalidObjectException(" No country entered. ");
        });
    }

    @Test
    public void addWithoutBirthdate() throws ParseException {
        UserEntity user = addNewUser(" Test addUser method (birthday null) ");
        user.setBirthday(null);

        assertThrows(InvalidObjectException.class, () -> {
            userService.addUser(user).getId();
            //throw new InvalidObjectException(" No birthday entered (format required : yyyy-mm-dd). ");
        });
    }


    //Invalid attribute

    @Test
    public void addInvalidUsername() throws ParseException {
        UserEntity user = addNewUser(" Test addUser method (invalid username) ");
        user.setPhone("1");

//        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        assertThrows(InvalidObjectException.class, () -> {
            userService.addUser(user).getId();
            //throw new InvalidObjectException(" The size of the username must be between 2 and 30 letters, without special characters. ");
        });
    }

    @Test
    public void addInvalidCountry() throws ParseException {
        UserEntity user = addNewUser(" Test addUser method (invalid country) ");
        user.setCountry("Fr");

//        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        assertThrows(InvalidObjectException.class, () -> {
            userService.addUser(user).getId();
            //throw new InvalidObjectException(" Only French residents are allowed to create an account. ");
        });
    }

    @Test
    public void addInvalidBirthday() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        UserEntity user = addNewUser(" Test addUser method (invalid birthday) ");
        user.setBirthday(new Date(dateFormat.parse("22-02-2222").getTime()));

//        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        assertThrows(InvalidObjectException.class, () -> {
            userService.addUser(user).getId();
            //throw new InvalidObjectException(" This birthday is invalid. ");
        });
    }

    @Test
    public void addInvalidAge() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        UserEntity user = addNewUser(" Test addUser method (invalid age) ");
        user.setBirthday(new Date(dateFormat.parse("01-01-2010").getTime()));

//        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        assertThrows(InvalidObjectException.class, () -> {
            userService.addUser(user).getId();
            //throw new InvalidObjectException(" Only adult are allowed to create an account. ");
        });
    }

    @Test
    public void addInvalidPhone() throws ParseException {
        UserEntity user = addNewUser(" Test addUser method (invalid phone number) ");
        user.setPhone("1234");

//        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        assertThrows(InvalidObjectException.class, () -> {
            userService.addUser(user).getId();
            //throw new InvalidObjectException(" This phone number is invalid (only French phone numbers are allowed). ");
        });
    }

}