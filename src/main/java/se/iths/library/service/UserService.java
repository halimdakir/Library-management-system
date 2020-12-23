package se.iths.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.iths.library.models.Roles;
import se.iths.library.entity.User;
import se.iths.library.repository.UserRepository;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
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
                .orElseGet(() -> {
                    newUser.setId(id);
                    return userRepository.save(newUser);
                });
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
    public Iterable<User> findUsersByLogin_Roles(Roles roles){
        return userRepository.findUsersByLogin_Roles(roles);
    }
}
