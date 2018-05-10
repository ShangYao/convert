package com.jinanlongen.sparrow.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jinanlongen.sparrow.roc.domain.Brand;
import com.jinanlongen.sparrow.roc.domain.Gender;
import com.jinanlongen.sparrow.roc.domain.Taxon;
import com.jinanlongen.sparrow.roc.repository.BrandRep;
import com.jinanlongen.sparrow.roc.repository.GenderRep;
import com.jinanlongen.sparrow.roc.repository.TaxonRep;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年5月9日
 */
@Service
public class InitService {
  @Autowired
  private BrandRep brandRep;
  @Autowired
  private GenderRep genderRep;
  @Autowired
  private TaxonRep taxonRep;
  private List<Brand> brands;
  private List<Gender> genders;
  private List<Taxon> topTaxons;

  public void init() {
    System.out.println("init run");
    brands = brandRep.findAll();
    genders = genderRep.findAll();
    topTaxons = taxonRep.findByAncestryIsNull();
  }

  public List<Brand> getBrands() {
    if (brands == null)
      init();
    return brands;
  }

  public List<Gender> getGenders() {
    if (genders == null)
      init();
    return genders;
  }

  public List<Taxon> getTopTaxons() {
    if (topTaxons == null)
      init();
    return topTaxons;
  }
}
