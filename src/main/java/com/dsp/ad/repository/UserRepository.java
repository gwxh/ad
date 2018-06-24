package com.dsp.ad.repository;

import com.dsp.ad.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("from User u where u.username = ?1")
    User selectUserByName(String username);

    @Transactional
    @Modifying
    @Query("update User u set u.status=?2 where u.id=?1")
    void updateStatus(int userId, int status);
}
