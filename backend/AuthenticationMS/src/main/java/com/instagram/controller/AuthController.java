package com.instagram.controller;

import com.instagram.dto.LoginDTO;
import com.instagram.dto.PasswordResetDTO;
import com.instagram.dto.UserDTO;
import com.instagram.exception.UserException;
import com.instagram.service.UserService;
import com.instagram.utility.SuccessInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private Environment environment;

    @GetMapping("allusers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> allUsersDtos = userService.getAllUsers();
        return new ResponseEntity<>(allUsersDtos, HttpStatus.OK);
    }

    @GetMapping("match/{username}")
    public ResponseEntity<List<UserDTO>> getMatchingUsers(@PathVariable String username) throws UserException {
        List<UserDTO> userDTOS = userService.getMatchingUsers(username);
        return new ResponseEntity<>(userDTOS,HttpStatus.OK);
    }

    @GetMapping("{userID}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer userID) throws UserException {
        UserDTO user = userService.getUser(userID);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("user/{username}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String username) throws UserException {
        UserDTO user = userService.getUser(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<SuccessInfo> registerUser(@RequestBody UserDTO userDTO) throws UserException {
        String username = userService.registerUser(userDTO);
        SuccessInfo successInfo = new SuccessInfo();
        successInfo.setSuccessCode(HttpStatus.CREATED.value());
        successInfo.setSuccessMessage(environment.getProperty("user.REGISTRATION_SUCCESS") + " : " + username);
        return new ResponseEntity<>(successInfo, HttpStatus.CREATED);
    }

    @PostMapping("loginuser")
    public ResponseEntity<UserDTO> loginUser(@RequestBody LoginDTO loginDTO) throws UserException {
        UserDTO user = userService.verifyUser(loginDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<SuccessInfo> updateuser(@PathVariable Integer id, @RequestBody UserDTO userDTO) throws UserException {
        String name = userService.updateUser(id, userDTO);
        SuccessInfo successInfo = new SuccessInfo();
        successInfo.setSuccessCode(HttpStatus.OK.value());
        successInfo.setSuccessMessage(environment.getProperty("user.UPDATE_SUCCESS") + " : " + name);
        return new ResponseEntity<>(successInfo, HttpStatus.OK);
    }

    @PostMapping("reset-password")
    public ResponseEntity<SuccessInfo> resetUserPassword(@RequestBody PasswordResetDTO pwdResetDto) throws UserException {
        String name = userService.resetPassword(pwdResetDto);
        SuccessInfo successInfo = new SuccessInfo();
        successInfo.setSuccessCode(HttpStatus.OK.value());
        successInfo.setSuccessMessage(environment.getProperty("user.PASSWORD_RESET_SUCCESS") + " : " + name);
        return new ResponseEntity<>(successInfo, HttpStatus.OK);
    }
}