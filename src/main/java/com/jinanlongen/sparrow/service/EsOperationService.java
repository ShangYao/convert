package com.jinanlongen.sparrow.service;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jinanlongen.sparrow.domain.Merchandise;
import com.jinanlongen.sparrow.domain.es.EsMerchandise;
import com.jinanlongen.sparrow.util.StringUtils;

@Service
public class EsOperationService {
  private Logger logger = LoggerFactory.getLogger(EsOperationService.class);
  private static final String TYPE = "merchandise";
  private static final String INDEX = "sparrow";
  @Autowired
  private TransportClient client;
  @Autowired
  private RefinedMineService refinedMineService;

  @Async
  public void fromDB(long id) {
    Merchandise m = refinedMineService.findOne(id);
    if (m.getState().equals("已发布") || m.getState().equals("已禁用")) {
      EsMerchandise es = new EsMerchandise().generate(m);
      postOne(es);
    } else if (m.getState().equals("回收站")) {
      deleteOne(id);
    } else {
      logger.info("精编状态{}，无需上传es", m.getState());
    }
  }

  @Async
  public void postToEs(long id, String state) {
    if (state.equals("已发布") || state.equals("已禁用")) {
      Merchandise m = refinedMineService.findOne(id);
      EsMerchandise es = new EsMerchandise().generate(m);
      postOne(es);
    } else if (state.equals("回收站")) {
      deleteOne(id);
    } else {
      logger.info("精编状态{}，无需上传es", state);
    }
  }

  @Async
  public void deleteOne(long id) {
    DeleteRequestBuilder deleteRequestBuilder = client.prepareDelete(INDEX, TYPE, id + "");
    DeleteResponse deleteResponse = deleteRequestBuilder.get();
    logger.info("删除es中精编id{},是否删除{}", id, deleteResponse.status());
  }

  public void postOne(EsMerchandise m) {
    BulkRequestBuilder bulkRequest = client.prepareBulk();
    try {
      bulkRequest.add(client.prepareIndex(INDEX, TYPE, m.getId() + "")
          .setSource(jsonBuilder().startObject().field("id", m.getId()).field("title", m.getTitle())
              .field("album_id", m.getAlbum_id()).field("created_at", m.getCreated_at())
              .field("updated_at", m.getUpdated_at()).field("state", m.getState())
              .field("gender", parseJson(m.getGender())).field("brand", parseJson(m.getBrand()))
              .field("taxon", parseJson(m.getTaxon())).field("colors", parseJson(m.getColors()))
              .field("descs", parseJson(m.getDescs())).field("sizes", parseJson(m.getSizes()))
              .field("skus", parseJson(m.getSkus())).field("urls", parseJson(m.getUrls()))
              .field("albums", parseJson(m.getAlbums())).field("specs", parseJson(m.getSpecs()))
              .field("owner", parseJson(m.getManager())).endObject()));
    } catch (IOException e) {
      logger.error("上传es失败,精编ID:{}" + m.getId());
      logger.error("{}", e.toString());
    }
    BulkResponse response = bulkRequest.get();
    if (response.hasFailures()) {
      logger.error("上传es失败,精编ID:{}" + m.getId());
    } else {
      logger.info("上传es成功,精编ID:{}" + m.getId());
    }
  }

  @Async
  public void updateDocumentState(String id, String state) {
    UpdateRequest updateRequest = new UpdateRequest();
    updateRequest.index(INDEX);
    updateRequest.type(TYPE);
    updateRequest.id(id);
    try {
      updateRequest.doc(XContentFactory.jsonBuilder().startObject()
          .field("state", StringUtils.stateTransfor(state)).endObject());
      UpdateResponse response = client.update(updateRequest).get();
      logger.info("修改状态，精编id{},是否更新{}", id, response.status());
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }

  }

  public JSONArray parseArray(List<? extends Object> list) {
    return JSON.parseArray(JSON.toJSONString(list));
  }

  private Object parseJson(Object obj) {
    return JSON.toJSON(obj);
  }


}
