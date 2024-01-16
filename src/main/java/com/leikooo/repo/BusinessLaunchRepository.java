package com.leikooo.repo;

import com.leikooo.pojo.BusinessLaunch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/16
 * @description
 */
@Repository
public interface BusinessLaunchRepository extends JpaRepository<BusinessLaunch, Integer> {

}
