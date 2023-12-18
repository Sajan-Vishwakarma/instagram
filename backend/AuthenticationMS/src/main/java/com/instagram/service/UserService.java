package com.instagram.service;

import com.instagram.dto.LoginDTO;
import com.instagram.dto.PasswordResetDTO;
import com.instagram.dto.UserDTO;
import com.instagram.exception.UserException;

import java.util.List;

public interface UserService {

    public List<UserDTO> getAllUsers();
    public UserDTO getUser(String username) throws UserException;
    public List<UserDTO> getMatchingUsers(String username) throws UserException;
    public UserDTO getUser(Integer userID) throws UserException;
    public String registerUser(UserDTO userDTO) throws UserException;
    public String updateUser(Integer userId, UserDTO userDTO) throws  UserException;
    public UserDTO verifyUser(LoginDTO loginDTO) throws UserException;
    public String resetPassword(PasswordResetDTO pwdResetDto) throws UserException;
}
