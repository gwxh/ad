package com.dsp.ad.service;

import com.dsp.ad.entity.User;

public interface UserService {

    User selectUserByName(String username);

    void saveUserInfo(User user);
}
