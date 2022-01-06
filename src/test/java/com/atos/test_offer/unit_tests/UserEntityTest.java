package com.atos.test_offer.unit_tests;

import com.atos.test_offer.Entities.UserEntity;
import com.atos.test_offer.Entities.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    private UserEntity user;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
    }

    @Test
    void getId() {
        assertEquals(this.user.getId(), 0);
    }

    @Test
    void setId() {
        int testId = 3;
        this.user.setId(testId);
        assertEquals(this.user.getId(), testId);
    }

    @Test
    void getUsername() {
        assertNull(this.user.getUsername());
    }

    @Test
    void setUsername() {
        String testUsername = "testUsername";
        this.user.setUsername(testUsername);
        assertEquals(this.user.getUsername(), testUsername);
    }

    @Test
    void getBirthday() {
        assertNull(this.user.getBirthday());
    }

    @Test
    void setBirthday() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date testdate = new Date(dateFormat.parse("01-01-1901").getTime());
        this.user.setBirthday(testdate);
        assertEquals(user.getBirthday(), testdate);
    }

    @Test
    void getCountry() {
        assertNull(this.user.getCountry());
    }

    @Test
    void setCountry() {
        String testCountry = "FRANCE";
        this.user.setCountry(testCountry);
        assertEquals(this.user.getCountry(), testCountry);
    }

    @Test
    void getPhone() {
        assertNull(this.user.getPhone());
    }

    @Test
    void setPhone() {
        String testPhone = "01 23 45 67 89";
        this.user.setPhone(testPhone);
        assertEquals(this.user.getPhone(), testPhone);
    }

    @Test
    void getGender() {
        assertNull(this.user.getGender());
    }

    @Test
    void setGender() {
        Gender testGender = Gender.Male;
        this.user.setGender(testGender);
        assertEquals(this.user.getGender(), testGender);
    }
}