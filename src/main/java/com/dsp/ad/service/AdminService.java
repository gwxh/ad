package com.dsp.ad.service;

import com.dsp.ad.entity.Admin;

public interface AdminService {

    Admin selectAdminByName(String username);
}
