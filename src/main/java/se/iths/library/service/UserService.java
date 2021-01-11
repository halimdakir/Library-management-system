package se.iths.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.iths.library.dto.UserInfoDTO;
import se.iths.library.exception.NotFoundException;
import se.iths.library.models.Roles;
import se.iths.library.entity.User;
import se.iths.library.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user){
        return userRepository.save(user);
    }
    public User updateUser(User newUser, Long id){
        return userRepository.findById(id)
                .map(user -> {
                    user.setFullName(newUser.getFullName());
                    user.setBirthDate(newUser.getBirthDate());
                    user.setAddress(newUser.getAddress());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new NotFoundException("User not found with id :" + id)
                );
    }
    public void deleteUserById(Long id){
        Optional<User> foundMember = userRepository.findById(id);
        userRepository.deleteById(foundMember.get().getId());
    }
    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }
    public List<UserInfoDTO> findUsersByLogin_Roles(Roles roles){
        return userRepository.findByLogin_Roles(roles);
    }
    public List<UserInfoDTO> getAllUserInfos(){
        return userRepository.getAllUserInfos();
    }
    public User findUserByLoginEmail(String email){
        return userRepository.findByLogin_Email(email);
    }
}
