package com.instagram;

import com.instagram.dto.LoginDTO;
import com.instagram.dto.UserDTO;
import com.instagram.entity.User;
import com.instagram.exception.UserException;
import com.instagram.repository.UserRepository;
import com.instagram.service.UserService;
import com.instagram.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class AuthenticationMSApplicationTests {

    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Test
    public void inValidGetUserByUsernameTest() {

        String invalidUsername = "User 23he";
        Mockito.when(userRepository.findByUsername(invalidUsername))
                .thenReturn(Optional.ofNullable(null));

        Assertions.assertEquals("user.notfound",
                Assertions.assertThrows(UserException.class,
                        () -> userService.getUser(invalidUsername)).getMessage());
    }

    @Test
    public void inValidGetUserByIdTest() {
        Integer invalidUserId = -7;
        Mockito.when(userRepository.findById(invalidUserId))
                .thenReturn(Optional.ofNullable(null));

        Assertions.assertEquals("user.notfound",
                Assertions.assertThrows(UserException.class,
                        () -> userService.getUser(invalidUserId)).getMessage());
    }

    @Test
    public void inValidVerifyUserNotFoundTest() {

        String missingUsername = "missing";
        Mockito.when(userRepository.findByUsername(missingUsername))
                .thenReturn(Optional.ofNullable(null));

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(missingUsername);
        loginDTO.setPassword("password");

        Assertions.assertEquals("user.notfound",
                Assertions.assertThrows(UserException.class,
                        () -> userService.verifyUser(loginDTO)).getMessage());
    }

    @Test
    public void validVerifyUserIncorrectPasswordTest() {

        String validUsername = "missing";

        User user = new User();
        user.setUsername(validUsername);
        user.setPassword("passwordMismatch");
        Mockito.when(userRepository.findByUsername(validUsername))
                .thenReturn(Optional.of(user));

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(validUsername);
        loginDTO.setPassword("password");

        Assertions.assertEquals("user.incorrectPassword",
                Assertions.assertThrows(UserException.class,
                        () -> userService.verifyUser(loginDTO)).getMessage());
    }

    @Test
    public void inValidRegisterUserEmailFoundTest() {

        String registeredEmail = "user@gmail.com";
        User user = new User();
        user.setEmailId(registeredEmail);

        Mockito.when(userRepository.findByEmailId(registeredEmail))
                .thenReturn(Optional.of(user));

        UserDTO userDTO = new UserDTO();
        userDTO.setEmailId(registeredEmail);

        Assertions.assertEquals("user.EMAIL_FOUND",
                Assertions.assertThrows(UserException.class,
                        () -> userService.registerUser(userDTO)).getMessage());
    }

    @Test
    public void inValidRegisterUserFoundTest() {

        String registeredUsername = "User1";
        User user = new User();
        user.setUsername(registeredUsername);

        Mockito.when(userRepository.findByUsername(registeredUsername))
                .thenReturn(Optional.of(user));

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(registeredUsername);

        Assertions.assertEquals("user.FOUND",
                Assertions.assertThrows(UserException.class,
                        () -> userService.registerUser(userDTO)).getMessage());
    }

    @Test
    public void invalidUpdateUserNotFoundTest() {

        Integer invalidUserId = -1;
        Mockito.when(userRepository.findById(invalidUserId))
                .thenReturn(Optional.ofNullable(null));

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(invalidUserId);

        Assertions.assertEquals("user.notfound",
                Assertions.assertThrows(UserException.class,
                        () -> userService.updateUser(invalidUserId, userDTO)).getMessage());
    }

    @Test
    public void invalidUpdateUserUsernameAlreadyTaken() {
        Integer validUserOneId = 1;

        User user1 = new User();
        user1.setUserId(validUserOneId);
        user1.setUsername("User1");
        Mockito.when(userRepository.findById(validUserOneId))
                .thenReturn(Optional.of(user1));

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(validUserOneId);
        userDTO.setUsername("User1");

        Integer validUserTwoId = 2;
        User user2 = new User();
        user2.setUserId(validUserTwoId);
        user2.setUsername("User1");
        Mockito.when(userRepository.findByUsername(userDTO.getUsername()))
                .thenReturn(Optional.of(user2));

        Assertions.assertEquals("user.USERNAME_ALREADY_TAKEN",
                Assertions.assertThrows(UserException.class,
                        () -> userService.updateUser(validUserOneId, userDTO)).getMessage());
    }

}
