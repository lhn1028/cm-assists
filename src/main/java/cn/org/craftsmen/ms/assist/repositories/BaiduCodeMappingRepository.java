package cn.org.craftsmen.ms.assist.repositories;

import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.Repository;
import cn.org.craftsmen.ms.assist.entities.BaiduCodeMapping;

/***
 * Mapping java Locale to the language code of Baidu
 * Supported language list:
 * ——————————————————————
 * |  auto  自动检测		|
 * |  zh   	中文       		|
 * |  en 	英语			|
 * |  yue 	粤语			|
 * |  wyw 	文言文		|
 * |  jp 	日语			|
 * |  kor 	韩语			|
 * |  fra 	法语			|
 * |  spa 	西班牙语		|
 * |  th 	泰语			|
 * |  ara 	阿拉伯语		|
 * |  ru 	俄语			|
 * |  pt 	葡萄牙语		|
 * |  de 	德语			|
 * |  it 	意大利语		|
 * |  el 	希腊语		|
 * |  nl 	荷兰语		|
 * |  pl 	波兰语		|
 * |  bul 	保加利亚语		|
 * |  est 	爱沙尼亚语		|
 * |  dan 	丹麦语		|
 * |  fin 	芬兰语		|
 * |  cs 	捷克语		|
 * |  rom 	罗马尼亚语		|
 * |  slo 	斯洛文尼亚语	|
 * |  swe 	瑞典语		|
 * |  hu 	匈牙利语		|
 * |  cht 	繁体中文		|
 * |  vie 	越南语		|
 * ——————————————————————
 * @author lhn10
 *
 */
public interface BaiduCodeMappingRepository extends Repository<BaiduCodeMapping, Long> {
	
	/***
	 * Supported language mapping from java Locale object to Baidu language code
	 * @return
	 */
	@Cacheable("availableMappings")
	List<BaiduCodeMapping> findByBaiduLanguageCodeIsNotNull();
	
	/***
	 * Get a BaiduCodeMapping object by language code and country code defined in java Locale object,  
	 * 
	 * @param language
	 * @param country
	 * @return
	 */
	@Cacheable("baiduCodeMappings")
	BaiduCodeMapping findByLanguageAndCountry(String language, String country);
}
