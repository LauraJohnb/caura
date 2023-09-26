package com.bonav.caura.users;

import com.bonav.caura.cars.Car;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public List<User>getUsers(){
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        if (userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get();
        }
        return null;
    }

    public User login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public User saveUser(User user) throws Exception {
        try {
            user = userRepository.save(user);
            return user;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getClass().getSimpleName(), e);
            throw new Exception("Failed to register user " + user.email);
        }
    }


    public boolean deleteUser(Long userId) throws Exception {
        try {
            boolean exists = userRepository.existsById(userId);
            if (!exists) {
                throw new IllegalStateException(
                        "user with id" +getUserById(userId) + "does not exists");
            }
            userRepository.deleteById(userId);
            log.info("Successfully deleted user " + userId);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getClass().getSimpleName(), e);
            return false;
        }
    }
}
