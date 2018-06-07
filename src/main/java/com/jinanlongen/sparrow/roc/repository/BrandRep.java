package com.jinanlongen.sparrow.roc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jinanlongen.sparrow.roc.domain.Brand;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年5月9日
 */
public interface BrandRep extends JpaRepository<Brand, Long> {

  List<Brand> findByCode(String code);

}
