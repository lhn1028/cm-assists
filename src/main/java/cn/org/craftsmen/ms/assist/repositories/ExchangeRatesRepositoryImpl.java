package cn.org.craftsmen.ms.assist.repositories;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import cn.org.craftsmen.ms.assist.domain.ExchangeRates;

@CacheConfig(cacheNames = "exchangeRates")
public class ExchangeRatesRepositoryImpl implements ExchangeRatesRepositoryCustom {

	private final MongoTemplate mongo;
	
	@Autowired
	public ExchangeRatesRepositoryImpl(MongoTemplate mongo) {
		this.mongo = mongo;
	}
	
	@Cacheable
	@Override
	public ExchangeRates findLastExchangeRates() {
		Query query = new Query();
		query.limit(1);
		query.with(new Sort(Sort.Direction.DESC, "updateDate"));
		
		List<ExchangeRates> rates = mongo.find(query, ExchangeRates.class);
		return 0 >= rates.size() ? null : rates.get(0);
	}

	@CacheEvict
	@Override
	public void saveExchangeRates(ExchangeRates exchangeRates) {
		mongo.insert(exchangeRates);
	}

}
