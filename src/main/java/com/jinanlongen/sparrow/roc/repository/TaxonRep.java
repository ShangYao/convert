package com.jinanlongen.sparrow.roc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.jinanlongen.sparrow.roc.domain.Taxon;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年5月9日
 */
public interface TaxonRep extends JpaRepository<Taxon, Long> {
  List<Taxon> findByAncestryIsNull();

  List<Taxon> findByAncestry(String id);

  @Query(value = "SELECT * FROM taxons where ancestry like ? ", nativeQuery = true)
  List<Taxon> getTaxon3(String id);

  List<Taxon> findByCode(String code);
}
