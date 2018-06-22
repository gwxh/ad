package com.dsp.ad.service.impl;

import com.dsp.ad.entity.Admin;
import com.dsp.ad.entity.User;
import com.dsp.ad.enums.UserEnum;
import com.dsp.ad.repository.AdRepository;
import com.dsp.ad.repository.AdminRepository;
import com.dsp.ad.repository.PlanRepository;
import com.dsp.ad.repository.UserRepository;
import com.dsp.ad.service.AdminService;
import com.dsp.ad.util.MD5Util;
import com.dsp.ad.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private AdRepository adRepository;

    @Override
    public Admin selectAdminByName(String username) {
        return adminRepository.selectAdminByName(username);
    }

    @Override
    public List<User> selectAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User selectUserById(int userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    @Override
    public void createUser(User user) {
        String encryptPwd = MD5Util.md5(user.getPassword());
        int money = user.getMoney() * 100;
        user.setPassword(encryptPwd);
        user.setMoney(money);
        user.setStatus(UserEnum.Status.ENABLE.value);
        user.setCreateTime(TimeUtil.now());
        userRepository.save(user);
    }

    @Override
    public void editUser(User userInfo) {
        User user = selectUserById(userInfo.getId());
        if (user != null) {
            String encryptPwd = MD5Util.md5(user.getPassword());
            user.setPassword(encryptPwd);
            user.setMoney(userInfo.getMoney() * 100);
            user.setNote(userInfo.getNote());
            user.setMobile(userInfo.getMobile());
            user.setEmail(userInfo.getEmail());
            user.setQq(userInfo.getQq());
            user.setUpdateTime(TimeUtil.now());
            userRepository.save(user);
        }
    }

    @Override
    public void disableUser(int userId) {
        User user = selectUserById(userId);
        if (user != null) {
            user.setStatus(UserEnum.Status.DISABLE.value);
            userRepository.save(user);
        }
    }

    @Override
    public void enableUser(int userId) {
        User user = selectUserById(userId);
        if (user != null) {
            user.setStatus(UserEnum.Status.ENABLE.value);
            userRepository.save(user);
        }
    }
}
