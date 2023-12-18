package com.instagram.service;

import com.instagram.dto.LoginDTO;
import com.instagram.dto.PasswordResetDTO;
import com.instagram.dto.UserDTO;
import com.instagram.entity.User;
import com.instagram.exception.UserException;
import com.instagram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public List<UserDTO> getAllUsers() {
    List<User> allUsers = userRepository.findAll();
    List<UserDTO> allUserDtos = allUsers.stream()
            .map( user -> {
              return new UserDTO(user);
            })
            .collect(Collectors.toList());
    return allUserDtos;
  }

  @Override
  public UserDTO getUser(String username) throws UserException {
    Optional<User> op = userRepository.findByUsername(username);
    User user = op.orElseThrow( ()-> new UserException("user.notfound"));
    return new UserDTO(user);
  }

  @Override
  public List<UserDTO> getMatchingUsers(String username) throws UserException {

    List<User> users = userRepository.findByMatchingUserName(username);
    List<UserDTO> userDTOS = users.stream()
            .map( user -> {
              return new UserDTO(user);
            })
            .collect(Collectors.toList());
    return userDTOS;
  }

  @Override
  public UserDTO getUser(Integer userID) throws UserException {
    Optional<User> op = userRepository.findById(userID);
    User user = op.orElseThrow( ()-> new UserException("user.notfound"));
    return new UserDTO(user);
  }

  @Override
  public String registerUser(UserDTO userDTO) throws UserException {

    Optional<User> op1 = userRepository.findByUsername(userDTO.getUsername());
    if( op1.isPresent()) throw new UserException("user.FOUND");

    Optional<User> op2 = userRepository.findByEmailId(userDTO.getEmailId());
    if( op2.isPresent()) throw new UserException("user.EMAIL_FOUND");

    User user = new User();
    user.setUsername(userDTO.getUsername());
    user.setBio(userDTO.getBio());
    user.setPassword(userDTO.getPassword());
    user.setFullName(userDTO.getFullName());
    user.setEmailId(userDTO.getEmailId());
    user.setProfileImgId(userDTO.getProfileImgId());

    return userRepository.save(user).getUsername();
  }

  @Override
  public String updateUser(Integer userId, UserDTO userDTO) throws UserException {
    Optional<User> op = userRepository.findById(userId);
    User user = op.orElseThrow( () ->  new UserException("user.notfound"));

    Optional<User> op2 = userRepository.findByUsername(userDTO.getUsername());
    if( op2.isPresent() && (op2.get().getUserId() != user.getUserId()))
      throw new UserException("user.USERNAME_ALREADY_TAKEN");

    user.setUsername(userDTO.getUsername());
    user.setPassword(userDTO.getPassword());
    user.setEmailId(userDTO.getEmailId());
    user.setFullName(userDTO.getFullName());
    user.setBio(userDTO.getBio());
    user.setProfileImgId(userDTO.getProfileImgId());
    return user.getUsername();
  }

  @Override
  public UserDTO verifyUser(LoginDTO loginDTO) throws UserException {

    Optional<User> op = userRepository.findByUsername(loginDTO.getUsername());
    User user = op.orElseThrow( () -> new UserException("user.notfound"));

    if( !loginDTO.getPassword().equals(user.getPassword())  )
        throw  new UserException("user.incorrectPassword");

    return new UserDTO(user);
  }

  @Override
  public String resetPassword(PasswordResetDTO pwdResetDto) throws UserException {

    Optional<User> op = userRepository.findByUsername(pwdResetDto.getUsername());
    User user = op.orElseThrow( () -> new UserException("user.notfound"));

    if( !pwdResetDto.getEmailId().equals(user.getEmailId()) ){
      throw new UserException("user.EMAIL_MISMATCH");
    }

    UserDTO u = new UserDTO(user);
    u.setPassword(pwdResetDto.getPassword());
    return updateUser(user.getUserId(), u);
  }
}
