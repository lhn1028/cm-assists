package cn.org.craftsmen.ms.assists.repositories;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.org.craftsmen.ms.assists.config.EmbeddedMongoConfig;
import cn.org.craftsmen.ms.assists.domain.ExchangeRates;
import cn.org.craftsmen.ms.assists.repositories.ExchangeRatesRepository;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {EmbeddedMongoConfig.class})
public class ExchangeRatesRepositoryTest {
	
	private static final String EXCHANGE_RATES = "{" + 
			"    \"updateDate\" : \"2018-01-25T08:00:00.000Z\"," + 
			"    \"source\" : \"openexchangerates.org\"," + 
			"    \"base\" : \"USD\"," + 
			"    \"rates\" : {" + 
			"        \"AED\" : 3.673158," + 
			"        \"AFN\" : 69.505," + 
			"        \"ALL\" : 107.55," + 
			"        \"AMD\" : 478.394504," + 
			"        \"ANG\" : 1.776767," + 
			"        \"AOA\" : 186.254501," + 
			"        \"ARS\" : 19.6485," + 
			"        \"AUD\" : 1.236623," + 
			"        \"AWG\" : 1.789995," + 
			"        \"AZN\" : 1.6895," + 
			"        \"BAM\" : 1.5849," + 
			"        \"BBD\" : 2.0," + 
			"        \"BDT\" : 82.884064," + 
			"        \"BGN\" : 1.575517," + 
			"        \"BHD\" : 0.376975," + 
			"        \"BIF\" : 1754.926108," + 
			"        \"BMD\" : 1.0," + 
			"        \"BND\" : 1.305938," + 
			"        \"BOB\" : 6.910011," + 
			"        \"BRL\" : 3.146573," + 
			"        \"BSD\" : 1.0," + 
			"        \"BTC\" : 8.7453917e-05," + 
			"        \"BTN\" : 63.745221," + 
			"        \"BWP\" : 9.617061," + 
			"        \"BYN\" : 1.979446," + 
			"        \"BZD\" : 2.000787," + 
			"        \"CAD\" : 1.232453," + 
			"        \"CDF\" : 1613.322925," + 
			"        \"CHF\" : 0.942255," + 
			"        \"CLF\" : 0.0228," + 
			"        \"CLP\" : 603.1," + 
			"        \"CNH\" : 6.33792," + 
			"        \"CNY\" : 6.3334," + 
			"        \"COP\" : 2817.5," + 
			"        \"CRC\" : 563.904708," + 
			"        \"CUC\" : 1.0," + 
			"        \"CUP\" : 25.5," + 
			"        \"CVE\" : 89.29," + 
			"        \"CZK\" : 20.43905," + 
			"        \"DJF\" : 178.27," + 
			"        \"DKK\" : 5.99471," + 
			"        \"DOP\" : 48.6," + 
			"        \"DZD\" : 113.606," + 
			"        \"EGP\" : 17.686," + 
			"        \"ERN\" : 15.110528," + 
			"        \"ETB\" : 27.2835," + 
			"        \"EUR\" : 0.805222," + 
			"        \"FJD\" : 2.001651," + 
			"        \"FKP\" : 0.701544," + 
			"        \"GBP\" : 0.701544," + 
			"        \"GEL\" : 2.542478," + 
			"        \"GGP\" : 0.701544," + 
			"        \"GHS\" : 4.5464," + 
			"        \"GIP\" : 0.701544," + 
			"        \"GMD\" : 48.61," + 
			"        \"GNF\" : 9000.0," + 
			"        \"GTQ\" : 7.320738," + 
			"        \"GYD\" : 206.85523," + 
			"        \"HKD\" : 7.81685," + 
			"        \"HNL\" : 23.590288," + 
			"        \"HRK\" : 5.986406," + 
			"        \"HTG\" : 63.883134," + 
			"        \"HUF\" : 249.01505," + 
			"        \"IDR\" : 13343.131498," + 
			"        \"ILS\" : 3.40945," + 
			"        \"IMP\" : 0.701544," + 
			"        \"INR\" : 63.615," + 
			"        \"IQD\" : 1185.202494," + 
			"        \"IRR\" : 36119.966156," + 
			"        \"ISK\" : 101.43," + 
			"        \"JEP\" : 0.701544," + 
			"        \"JMD\" : 124.39275," + 
			"        \"JOD\" : 0.709247," + 
			"        \"JPY\" : 109.02478571," + 
			"        \"KES\" : 102.44," + 
			"        \"KGS\" : 68.794002," + 
			"        \"KHR\" : 4044.191667," + 
			"        \"KMF\" : 397.04," + 
			"        \"KPW\" : 900.0," + 
			"        \"KRW\" : 1061.85," + 
			"        \"KWD\" : 0.299848," + 
			"        \"KYD\" : 0.829473," + 
			"        \"KZT\" : 320.97," + 
			"        \"LAK\" : 8276.4," + 
			"        \"LBP\" : 1512.195," + 
			"        \"LKR\" : 153.79," + 
			"        \"LRD\" : 127.96831," + 
			"        \"LSL\" : 11.845," + 
			"        \"LYD\" : 1.332493," + 
			"        \"MAD\" : 9.14685," + 
			"        \"MDL\" : 16.760344," + 
			"        \"MGA\" : 3220.0," + 
			"        \"MKD\" : 49.585," + 
			"        \"MMK\" : 1333.210206," + 
			"        \"MNT\" : 2420.955785," + 
			"        \"MOP\" : 8.015031," + 
			"        \"MRO\" : 355.0," + 
			"        \"MRU\" : 35.41," + 
			"        \"MUR\" : 32.403," + 
			"        \"MVR\" : 15.409873," + 
			"        \"MWK\" : 725.555," + 
			"        \"MXN\" : 18.522325," + 
			"        \"MYR\" : 3.8986," + 
			"        \"MZN\" : 59.0," + 
			"        \"NAD\" : 11.885," + 
			"        \"NGN\" : 358.577572," + 
			"        \"NIO\" : 31.05," + 
			"        \"NOK\" : 7.739307," + 
			"        \"NPR\" : 101.436256," + 
			"        \"NZD\" : 1.356536," + 
			"        \"OMR\" : 0.385011," + 
			"        \"PAB\" : 1.0," + 
			"        \"PEN\" : 3.211709," + 
			"        \"PGK\" : 3.23295," + 
			"        \"PHP\" : 50.834," + 
			"        \"PKR\" : 110.755," + 
			"        \"PLN\" : 3.33952," + 
			"        \"PYG\" : 5664.852124," + 
			"        \"QAR\" : 3.641076," + 
			"        \"RON\" : 3.757785," + 
			"        \"RSD\" : 95.682434," + 
			"        \"RUB\" : 55.8366," + 
			"        \"RWF\" : 855.437838," + 
			"        \"SAR\" : 3.75115," + 
			"        \"SBD\" : 7.772868," + 
			"        \"SCR\" : 13.439813," + 
			"        \"SDG\" : 7.015," + 
			"        \"SEK\" : 7.912029," + 
			"        \"SGD\" : 1.306074," + 
			"        \"SHP\" : 0.701544," + 
			"        \"SLL\" : 7663.973419," + 
			"        \"SOS\" : 576.366453," + 
			"        \"SRD\" : 7.468," + 
			"        \"SSP\" : 130.2634," + 
			"        \"STD\" : 20045.035527," + 
			"        \"STN\" : 20.18," + 
			"        \"SVC\" : 8.710191," + 
			"        \"SYP\" : 515.05499," + 
			"        \"SZL\" : 11.905," + 
			"        \"THB\" : 31.4295," + 
			"        \"TJS\" : 8.784328," + 
			"        \"TMT\" : 3.499986," + 
			"        \"TND\" : 2.400244," + 
			"        \"TOP\" : 2.24363," + 
			"        \"TRY\" : 3.745278," + 
			"        \"TTD\" : 6.7526," + 
			"        \"TWD\" : 29.075276," + 
			"        \"TZS\" : 2245.15," + 
			"        \"UAH\" : 28.934," + 
			"        \"UGX\" : 3627.883069," + 
			"        \"USD\" : 1.0," + 
			"        \"UYU\" : 28.494716," + 
			"        \"UZS\" : 8155.0," + 
			"        \"VEF\" : 10.01245," + 
			"        \"VND\" : 22704.982397," + 
			"        \"VUV\" : 104.274483," + 
			"        \"WST\" : 2.524086," + 
			"        \"XAF\" : 528.190716," + 
			"        \"XAG\" : 0.05686684," + 
			"        \"XAU\" : 0.00073454," + 
			"        \"XCD\" : 2.70255," + 
			"        \"XDR\" : 0.689911," + 
			"        \"XOF\" : 528.190716," + 
			"        \"XPD\" : 0.00090092," + 
			"        \"XPF\" : 96.088491," + 
			"        \"XPT\" : 0.00098088," + 
			"        \"YER\" : 250.3," + 
			"        \"ZAR\" : 11.918942," + 
			"        \"ZMW\" : 9.67," + 
			"        \"ZWL\" : 322.355011" + 
			"    }" + 
			"}";
	
	private ObjectMapper objectMapper = new ObjectMapper();
	@Autowired
	private ExchangeRatesRepository repo;
	
	@Before
	public void setUp() throws JsonParseException, JsonMappingException, IOException {
		ExchangeRates exchangeRates = objectMapper.readValue(EXCHANGE_RATES, ExchangeRates.class);
		repo.save(exchangeRates);
	}
	
	@After
	public void tearDown() {
		repo.deleteAll();
	}

	@Test
	public void testFindLastExchangeRates() {
		assertNotNull(repo);
		ExchangeRates rates = repo.findLastExchangeRates();
		
		assertNotNull(rates);
	}

	@Test
	public void testSaveExchangeRates() throws JsonParseException, JsonMappingException, IOException {
		ExchangeRates exchangeRates = objectMapper.readValue(EXCHANGE_RATES, ExchangeRates.class);
		assertNotNull(exchangeRates);
		
		assertEquals(1, repo.count());
		repo.saveExchangeRates(exchangeRates);
		assertEquals(2, repo.count());
	}
}
