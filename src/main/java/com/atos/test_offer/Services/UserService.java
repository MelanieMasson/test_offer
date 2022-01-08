package com.atos.test_offer.Services;

import com.atos.test_offer.Entities.UserEntity;
import com.atos.test_offer.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    //-----------------------------------------------------------------------------------------

    //find user(s) and display details
    //find all
    public Iterable<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    //find user by id
    public UserEntity findUserById(int id) {
        return userRepository.findById(id).get();
    }

    //-----------------------------------------------------------------------------------------

    public UserEntity saveUser(UserEntity user) throws InvalidObjectException {
        checkUser(user);
        userRepository.save(user);

        return user;
    }

    public Date getAge( Date birthday) {
        return Date.valueOf(birthday.toLocalDate().plusYears(18));
    }

    private void checkUser(UserEntity user) throws InvalidObjectException {

        // check  username
        if (user.getUsername() == null) {
            System.out.println(user.getUsername());
            throw new InvalidObjectException(" The user must have a username ");

        } else if (user.getUsername().length() < 2) {
            throw new InvalidObjectException(" The size of the username must be between 2 and 30 characters. ");
        }

        // check country
        if (user.getCountry() == null) {
            throw new InvalidObjectException(" The user must have a country ");
        } else if (user.getCountry().length() < 2) {
            throw new InvalidObjectException(" This country is invalid. ");
        } else {
            if (!(user.getCountry().toUpperCase().equals("FRANCE"))) {
                throw new InvalidObjectException(" Only French residents are allowed to create an account. ");
            }
        }

        //check age
        Date currentDate = Date.valueOf(LocalDate.now());
        if (user.getBirthday() == null) {
            throw new InvalidObjectException("The user must have a birthday (date format : yyyy-mm-dd)");
        } else if (user.getBirthday().after(currentDate)) {
            throw new InvalidObjectException(" This birthday is invalid. ");
        } else {
            if (getAge(user.getBirthday()).after(currentDate)) {
                throw new InvalidObjectException(" Only adult are allowed to create an account. ");
            }
        }

        //check phone number
        if (user.getPhone()!= null) {
            Pattern VALID_PHONE=Pattern.compile("^(?:(?:\\+|00)33[\\s.-]{0,3}(?:\\(0\\)[\\s.-]{0,3})?|0)[1-9](?:(?:[\\s.-]?\\d{2}){4}|\\d{2}(?:[\\s.-]?\\d{3}){2})$");
            Matcher matcher = VALID_PHONE.matcher(user.getPhone());
            if (!matcher.find() || user.getPhone().length() < 10) {
                throw new InvalidObjectException(" This phone number is invalid (only French phone numbers are allowed). ");
            }
        }
    }
}
