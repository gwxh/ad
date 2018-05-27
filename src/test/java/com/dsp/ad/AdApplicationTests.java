package com.dsp.ad;

import com.dsp.ad.entity.Admin;
import com.dsp.ad.repository.AdminRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdApplication.class)
public class AdApplicationTests {

	@Autowired
	private AdminRepository adminRepository;

	@Test
	public void contextLoads() {
		Admin admin = new Admin();
		admin.setUsername("admin");
		admin.setPassword("135246");
		admin.setLoginIP(1);
		admin.setLoginTime(2);
		admin.setStatus(1);
		admin.setRoleId(1);
		adminRepository.save(admin);
	}

}
