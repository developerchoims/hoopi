package com.ms.hoopi.login;

import com.ms.hoopi.entity.Users;
import com.ms.hoopi.join.UserRepository;
import com.ms.hoopi.join.UsersDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final BCryptPasswordEncoder encoder;

    public LoginServiceImpl(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }
    @Override
    public boolean validateUser(UsersDto user) {
        Users users = userRepository.findByUsersId(user.getUsersId());
        if(users != null){
            return encoder.matches(user.getUsersPw(), users.getUsersPw());
        }
        return false;
    }
}
