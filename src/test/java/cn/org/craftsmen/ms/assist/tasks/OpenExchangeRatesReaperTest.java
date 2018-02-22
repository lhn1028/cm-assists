package cn.org.craftsmen.ms.assist.tasks;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.org.craftsmen.ms.assist.AssistApplication;
import cn.org.craftsmen.ms.assist.api.ExchangeRatesReaper;
import cn.org.craftsmen.ms.assist.domain.ExchangeRates;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { AssistApplication.class })
public class OpenExchangeRatesReaperTest {

	@Autowired
	@Qualifier("openExchangeRatesReaper")
	private ExchangeRatesReaper reaper;

	@Test
	public void testReap() {
		assertNotNull(reaper);

//		ExchangeRates rates = reaper.reap();
//
//		assertNotNull(rates);
//		assertEquals("USD", rates.getBase());
//		assertTrue(0 < rates.getRates().size());
//		
//		System.out.println(rates);
	}

}
