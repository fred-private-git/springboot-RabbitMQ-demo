package com.zj.mq_test;

import com.zj.mq_test.mq.MqService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MqTestApplicationTests {

	@Autowired
	MqService mqService;

	@Test
	public void mqTest() throws InterruptedException {
		mqService.sendAddUser();
		Thread.sleep(10000000);
	}

}
