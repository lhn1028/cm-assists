package cn.org.craftsmen.ms.assist.repositories;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import cn.org.craftsmen.ms.assist.entities.JavaLocale;

@Profile("test")
public interface JavaLocalesRepository extends CrudRepository<JavaLocale, Long> {

}
