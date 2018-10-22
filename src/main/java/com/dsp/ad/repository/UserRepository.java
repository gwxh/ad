package com.dsp.ad.repository;

import com.dsp.ad.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("from User u where u.username = ?1")
    User selectUserByName(String username);

    @Query("from User u where u.status <> 2 order by u.id desc")
    List<User> selectUsers();

    @Transactional
    @Modifying
    @Query("update User u set u.status=?2 where u.id=?1")
    void updateStatus(int userId, int status);

    @Transactional
    @Modifying
    @Query("update User u set u.amount=u.amount-?2 where u.id=?1")
    void consume(int userId, int consumeAmount);
}
