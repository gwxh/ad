package com.dsp.ad.service.impl;

import com.dsp.ad.entity.Admin;
import com.dsp.ad.repository.AdminRepository;
import com.dsp.ad.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin selectAdminByName(String username) {
        return adminRepository.selectAdminByName(username);
    }
}
