package com.bonav.caura.users;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public User getUserById(long id){
        if(userRepository.findById(id).isPresent()){
            return userRepository.findById(id).get();
        }
        return null;
    }
    public User login(String email,String password){
        return userRepository.findByEmailAndPassword(email,password);
    }

    public User saveUser(User user) throws Exception{
        try {
            user=userRepository.save(user);
            return user;
        }
        catch(Exception e){
            log.error(e.getMessage(),e.getClass().getSimpleName(),e);
            throw new Exception("Failed to register user "+user.email);
        }
    }
}
