package com.jinanlongen.sparrow;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lokra.seaweedfs.core.FileSource;
import org.lokra.seaweedfs.core.FileTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.jinanlongen.sparrow.service.BasicUserService;
import com.jinanlongen.sparrow.service.RefinedMineService;

/**
 * 类说明
 * 
 * @author shangyao
 * @date 2018年1月27日
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {
  @Autowired
  private BasicUserService userService;
  @Autowired
  RefinedMineService refinedMineService;

  @Test
  public void updateUserTableByLDAPTest() {
    // userService.updateUserTableByLDAP();
  }

  @Test
  public void seaweedTest2() {
    // Httptest.post("http://192.168.200.71:8888/", "Users/user/Downloads/shoes.jpg");
    // File file = new File("/Users/user/Downloads/shoes.jpg");
    // boolean result = Httptest.post2(file, "http://192.168.200.71:8888/sparrow/shoes.jpg");
    // System.out.println(result);
    // System.out.println(Httptest.delete("http://192.168.200.71:8888/sparrow/8/2018511141243.jpg"));
  }

  @Test
  public void seaweedTest() {
    // MultipartEntityBuilder entity=new
    System.out.println("Hello, World");

    FileSource fileSource = new FileSource();
    fileSource.setHost("192.168.200.71");
    fileSource.setPort(8888);
    try {
      fileSource.startup();
      System.out.println("Finished starting up!");
    } catch (IOException e) {
      System.out.println("Aiya! Cannot connect!");
      return;
    }

    try {
      InputStream is = new FileInputStream("D:/1.jpg");
      FileTemplate template = new FileTemplate(fileSource.getConnection());
      // template.saveFileByStream("flow.jpg", is);
    } catch (FileNotFoundException e) {
      System.out.println("Cannot save the file!");
      return;
    } catch (IOException e) {
      System.out.println("Cannot write to Seaweed!");
      return;
    }

    System.out.println("Success and done!");
  }

}
