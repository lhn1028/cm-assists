package cn.org.craftsmen.ms.assists.services;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.org.craftsmen.ms.assists.AssistsApplication;
import cn.org.craftsmen.ms.assists.services.TranslationService;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= {AssistsApplication.class})
public class TranslationServiceImplTest {
	
	@Autowired
	private TranslationService service;

	@Test
	public void testTranslate() {
		assertNotNull(service);
		
		final String CONTENT = "北京时间1月21日消息，据美国权威体育媒体报道，NBA已经进入了中期，5名专家又齐聚一堂，开始分析一些重磅问题了。专家们认为，勒布朗-詹姆斯已经无法给克里夫兰带去总冠军了，MVP倒是还那么点机会";
		final Locale FROM = Locale.CHINA;
		final Locale TO = Locale.US;
		
		String result = service.translate(CONTENT, FROM, TO);
		System.out.println(result);
	}

}
