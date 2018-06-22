package com.dsp.ad.service;

import com.dsp.ad.entity.Admin;
import com.dsp.ad.entity.User;

import java.util.List;

public interface AdminService {

    Admin selectAdminByName(String username);

    List<User> selectAllUser();

    User selectUserById(int userId);

    void createUser(User user);

    void editUser(User user);

    void disableUser(int userId);

    void enableUser(int userId);
}
