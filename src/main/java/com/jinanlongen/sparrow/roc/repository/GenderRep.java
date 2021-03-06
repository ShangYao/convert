package com.jinanlongen.sparrow.roc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jinanlongen.sparrow.roc.domain.Gender;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年5月9日
 */
public interface GenderRep extends JpaRepository<Gender, Long> {

  List<Gender> findByCode(String genderId);

}
