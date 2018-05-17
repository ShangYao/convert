package com.jinanlongen.sparrow.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.jinanlongen.sparrow.domain.Merchandise;
import com.jinanlongen.sparrow.domain.SourceUrl;
import com.jinanlongen.sparrow.repository.ColorRep;
import com.jinanlongen.sparrow.repository.LineItemRep;
import com.jinanlongen.sparrow.repository.MerchandiseRep;
import com.jinanlongen.sparrow.repository.SizeRep;
import com.jinanlongen.sparrow.repository.SourceUrlRep;
import com.jinanlongen.sparrow.roc.domain.Taxon;
import com.jinanlongen.sparrow.roc.repository.BrandRep;
import com.jinanlongen.sparrow.roc.repository.GenderRep;
import com.jinanlongen.sparrow.roc.repository.TaxonRep;
import com.jinanlongen.sparrow.util.DateUtils;
import com.jinanlongen.sparrow.util.PageableUtils;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年2月23日
 */
@Service
public class RefinedMineService {
  @Autowired
  private MerchandiseRep mcdRep;
  @Autowired
  private SourceUrlRep urlRep;
  @Autowired
  private TaxonRep taxonRep;
  @Autowired
  private BrandRep brandRep;
  @Autowired
  private GenderRep genderRep;
  @Autowired
  private ColorRep colorRep;
  @Autowired
  private SizeRep sizeRep;
  @Autowired
  private LineItemRep itemRep;

  public String getUrl(String id) {
    return id;

  }

  public Merchandise queryList(final Merchandise merchandise) {
    return init(merchandise);
  }

  public Merchandise toModify(long id) {
    Merchandise merchandise = mcdRep.findOne(id);

    merchandise = getTaxons(merchandise);
    // List<SourceUrl> list = urlRep.findByMerchandiseIdAndState(id, 1);
    List<SourceUrl> list = urlRep.findByMerchandiseId(id);
    merchandise.setSourceUrl(list);
    merchandise.setColors(colorRep.findByMerchandiseId(id));
    merchandise.setSizes(sizeRep.findByMerchandiseId(id));
    merchandise.setLineItems(itemRep.findByMId(id));


    return merchandise;

  }

  private Merchandise getTaxons(Merchandise merchandise) {
    merchandise.setBrand(brandRep.findOne(merchandise.getBrandId()));
    merchandise.setGender(genderRep.findOne(merchandise.getGenderId()));
    Taxon taxon = taxonRep.findOne(merchandise.getTaxonId());
    String[] tId = taxon.getAncestry().trim().split("/");
    int length = tId.length;
    if (length == 1) {
      merchandise.setTaxon1(taxonRep.findOne(Long.parseLong(tId[0])));
      merchandise.setTaxon2(taxon);
    } else if (length == 2) {
      merchandise.setTaxon1(taxonRep.findOne(Long.parseLong(tId[0])));
      merchandise.setTaxon2(taxonRep.findOne(Long.parseLong(tId[1])));
      merchandise.setTaxon3(taxon);
    }

    return merchandise;
  }

  private Merchandise init(Merchandise merchandise) {
    merchandise.setPages(mcdRep.findAll(getSpecification(merchandise), merchandise.getPageable()));
    merchandise.setDraftPage(getStatePage("草稿", merchandise, merchandise.getDraftPageNum()));
    merchandise
        .setCommittedPage(getStatePage("待审核", merchandise, merchandise.getCommittedPageNum()));
    merchandise
        .setDeclinedPage(getStatePage("审核未过", merchandise, merchandise.getDeclinedPageNum()));
    merchandise
        .setPublishedPage(getStatePage("已发布", merchandise, merchandise.getPublishedPageNum()));
    merchandise.setRetiredPage(getStatePage("已下架", merchandise, merchandise.getRetiredPageNum()));
    merchandise.setTrashPage(getStatePage("回收站", merchandise, merchandise.getTrashPageNum()));
    return merchandise;

  }

  private PageableUtils getStatePage(String state, Merchandise merchandise, int page) {
    PageableUtils p = new PageableUtils();
    p.setPage(page);
    merchandise.setState(state);
    p.setPages(mcdRep.findAll(getSpecification(merchandise), p.getPageable()));
    return p;

  }

  private Specification<Merchandise> getSpecification(Merchandise merchandise) {
    return new Specification<Merchandise>() {
      @Override
      public Predicate toPredicate(Root<Merchandise> root, CriteriaQuery<?> query,
          CriteriaBuilder cb) {
        List<Predicate> lstPredicates = new ArrayList<Predicate>();

        if (0 != merchandise.getOwnerId()) {
          lstPredicates.add(cb.equal(root.get("ownerId").as(Long.class), merchandise.getOwnerId()));
        }
        if (StringUtils.isNotBlank(merchandise.getQueryString())) {
          if (StringUtils.isNumeric(merchandise.getQueryString())) {
            lstPredicates.add(cb.like(root.get("itemId").as(String.class),
                "%" + merchandise.getQueryString() + "%"));
          } else {
            lstPredicates.add(cb.like(cb.upper(root.get("title").as(String.class)),
                "%" + merchandise.getQueryString().toUpperCase() + "%"));
          }
        }
        if (StringUtils.isNotBlank(merchandise.getState())) {
          lstPredicates.add(cb.equal(root.get("state").as(String.class), merchandise.getState()));
        }
        if (StringUtils.isNotBlank(merchandise.getBeginCreated())) {
          lstPredicates.add(cb.greaterThanOrEqualTo(root.get("createdAt").as(Date.class),
              DateUtils.getDate(merchandise.getBeginCreated() + " 00-00-00")));
        }
        if (StringUtils.isNotBlank(merchandise.getEndCreated())) {
          lstPredicates.add(cb.lessThanOrEqualTo(root.get("createdAt").as(Date.class),
              DateUtils.getDate(merchandise.getEndCreated() + " 23-59-59")));
        }
        if (StringUtils.isNotBlank(merchandise.getEndUpdated())) {
          lstPredicates.add(cb.lessThanOrEqualTo(root.get("updatedAt").as(Date.class),
              DateUtils.getDate(merchandise.getEndUpdated() + " 23-59-59")));
        }
        if (StringUtils.isNotBlank(merchandise.getBeginUpdated())) {
          lstPredicates.add(cb.greaterThanOrEqualTo(root.get("updatedAt").as(Date.class),
              DateUtils.getDate(merchandise.getBeginUpdated() + " 00-00-00")));
        }
        if (StringUtils.isNotBlank(merchandise.getEndMaintain())) {
          lstPredicates.add(cb.lessThanOrEqualTo(root.get("maintainAt").as(Date.class),
              DateUtils.getDate(merchandise.getEndMaintain() + " 23-59-59")));
        }
        if (StringUtils.isNotBlank(merchandise.getBeginMaintain())) {
          lstPredicates.add(cb.greaterThanOrEqualTo(root.get("maintainAt").as(Date.class),
              DateUtils.getDate(merchandise.getBeginMaintain() + " 00-00-00")));
        }
        Predicate[] arrayPredicates = new Predicate[lstPredicates.size()];
        return cb.and(lstPredicates.toArray(arrayPredicates));
      }
    };
  }

}
