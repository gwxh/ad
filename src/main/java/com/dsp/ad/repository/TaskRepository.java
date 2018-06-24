package com.dsp.ad.repository;

import com.dsp.ad.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository  extends JpaRepository<Task, Integer> {

}
