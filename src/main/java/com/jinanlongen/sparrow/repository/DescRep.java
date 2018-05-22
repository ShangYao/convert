package com.jinanlongen.sparrow.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jinanlongen.sparrow.domain.Desc;

public interface DescRep extends JpaRepository<Desc, Long> {

  List<Desc> findByMerchandiseId(long id);

}
