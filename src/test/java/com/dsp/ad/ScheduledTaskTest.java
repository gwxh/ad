package com.dsp.ad;

import com.dsp.ad.schedule.ScheduledTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wanghh
 * @since 2023-09-21
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdApplication.class)
public class ScheduledTaskTest {

    @Autowired
    private ScheduledTask scheduledTask;

    @Test
    public void test(){
        scheduledTask.calcUserConsume();
    }
}
