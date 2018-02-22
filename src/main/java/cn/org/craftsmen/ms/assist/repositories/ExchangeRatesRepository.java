package cn.org.craftsmen.ms.assist.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import cn.org.craftsmen.ms.assist.domain.ExchangeRates;

/***
 * Exchange rates repository
 * @author shawn
 *
 */
public interface ExchangeRatesRepository extends MongoRepository<ExchangeRates, String>, ExchangeRatesRepositoryCustom {

}
