package cn.org.craftsmen.ms.assists.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import cn.org.craftsmen.ms.assists.entities.ExchangeRates;

/***
 * Exchange rates repository
 * @author shawn
 *
 */
public interface ExchangeRatesRepository extends MongoRepository<ExchangeRates, String>, ExchangeRatesRepositoryCustom {

}
