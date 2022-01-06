package com.atos.test_offer.APIController;

import com.atos.test_offer.Services.UserService;
import com.atos.test_offer.Entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.InvalidObjectException;

//@RestController
@Controller
@RequestMapping("/api/user")
public class UserAPIController {

    @Autowired
    UserService userService;

    //http://localhost:8080/api/user
    //display user(s) and details
    @GetMapping(value="", produces = "application/json")
    public ResponseEntity<Iterable<UserEntity>> getAll(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    //http://localhost:8080/api/user/id
    //find user by id and display user's details
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserEntity> get(@PathVariable("id") int id){
        try{
            UserEntity user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "User not found" );
        }

    }

    //http://localhost:8080/api/user
    //add user
    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<UserEntity> add(@RequestBody UserEntity user) {
        try{
            userService.addUser(user);

            return ResponseEntity.ok(user);
        }catch ( InvalidObjectException e ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }


}
