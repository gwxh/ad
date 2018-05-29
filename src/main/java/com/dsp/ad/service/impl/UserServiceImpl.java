package com.dsp.ad.service.impl;

import com.dsp.ad.entity.User;
import com.dsp.ad.repository.UserRepository;
import com.dsp.ad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User selectUserByName(String username) {
        return userRepository.selectUserByName(username);
    }
}
