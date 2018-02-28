package cn.org.craftsmen.ms.assist.services;

import static org.junit.Assert.*;

import java.util.Currency;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.org.craftsmen.ms.assist.AssistApplication;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= {AssistApplication.class})
public class ExchangeRatesServiceImplTest {
	
	@Autowired
	private ExchangeRatesService service;

	@Test
	public void testConvert() {
		assertNotNull(service);
		
		final Currency from = Currency.getInstance("CNY");
		final Currency to = Currency.getInstance("USD");
		final double amount = 100;
		final double rate = 6.4373;
		
		double result = service.convert(amount, from, to);
		assertTrue(0.5 > result - (amount / rate));
		
		System.out.println(result);
	}

}
