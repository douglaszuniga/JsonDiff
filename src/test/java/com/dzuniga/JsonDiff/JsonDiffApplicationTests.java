package com.dzuniga.JsonDiff;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonDiffApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void applicationStarts() {
		JsonDiffApplication.main(new String[]{});
	}

}
