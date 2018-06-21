package com.dsp.ad.service;

import com.dsp.ad.entity.Admin;
import com.dsp.ad.entity.User;

import java.util.List;

public interface AdminService {

    Admin selectAdminByName(String username);

    List<User> selectAllUser();

    void createUser(User user);
}
