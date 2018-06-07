package com.jinanlongen.sparrow;

import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.jinanlongen.sparrow.service.EsOperationService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsClientTest {
  @Autowired
  TransportClient client;
  @Autowired
  EsOperationService esOperationService;

  @Test
  public void testEsClient() {
    esOperationService.fromDB(24l);
  }

  @Test
  public void testEsDelete() {
    DeleteRequestBuilder deleteRequestBuilder =
        client.prepareDelete("sparrow", "merchandise", "21");
    DeleteResponse deleteResponse = deleteRequestBuilder.get();
    System.out.println(deleteResponse.status());
  }
}
