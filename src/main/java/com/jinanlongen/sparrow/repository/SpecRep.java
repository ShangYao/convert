package com.jinanlongen.sparrow.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jinanlongen.sparrow.domain.Spec;

public interface SpecRep extends JpaRepository<Spec, Long> {

  List<Spec> findByMerchandiseId(long id);

}
