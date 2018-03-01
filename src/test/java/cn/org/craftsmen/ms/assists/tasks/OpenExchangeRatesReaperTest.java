package cn.org.craftsmen.ms.assists.tasks;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.org.craftsmen.ms.assists.AssistsApplication;
import cn.org.craftsmen.ms.assists.api.ExchangeRatesReaper;
import cn.org.craftsmen.ms.assists.domain.ExchangeRates;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { AssistsApplication.class })
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
