package com.dsp.ad.repository;

import com.dsp.ad.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("from User u where u.username = ?1")
    User selectUserByName(String username);
}
