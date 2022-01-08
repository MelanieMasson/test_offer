package com.atos.test_offer.APIController;

import com.atos.test_offer.Services.UserService;
import com.atos.test_offer.Entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.InvalidObjectException;

@RestController
@RequestMapping("/api/user")
public class UserAPIController {

    @Autowired
    UserService userService;

    //http://localhost:8080/api/user
    @GetMapping(value="", produces = "application/json")
    public ResponseEntity<Iterable<UserEntity>> getAllUser(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    //http://localhost:8080/api/user/{id}
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserEntity> getUser(@PathVariable("id") int id){
        try{
            UserEntity user = userService.findUserById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , " User not found. "  );
        }
    }

    //http://localhost:8080/api/user
    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity user) {
        try{
            userService.saveUser(user);

            return ResponseEntity.ok(user);
        }catch ( InvalidObjectException e ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }


}
