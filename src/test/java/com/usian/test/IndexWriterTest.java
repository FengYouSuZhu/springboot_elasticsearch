package com.usian.test;

import com.asus.ElasticsearchApp;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * @author 枫柚素主
 * @version 1.0
 * @date 2020/6/2 19:31
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ElasticsearchApp.class)
public class IndexWriterTest {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    //创建索引库
    @Test
    public void testCreateIndex() throws IOException {
        //创建“创建索引请求”对象，并设置索引名称
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("java1906");
        //设置索引参数
        createIndexRequest.settings(Settings.builder()
                .put("number_of_shards",2)
                .put("number_of_replicas",0));
        //设置mappin
        createIndexRequest.mapping("course","{\n" +
                "  \"_source\": {\n" +
                "    \"excludes\":[\"description\"]\n" +
                "  }, \n" +
                " \t\"properties\": {\n" +
                "      \"name\": {\n" +
                "          \"type\": \"text\",\n" +
                "          \"analyzer\":\"ik_max_word\",\n" +
                "          \"search_analyzer\":\"ik_smart\"\n" +
                "      },\n" +
                "      \"description\": {\n" +
                "          \"type\": \"text\",\n" +
                "          \"analyzer\":\"ik_max_word\",\n" +
                "          \"search_analyzer\":\"ik_smart\"\n" +
                "       },\n" +
                "       \"studymodel\": {\n" +
                "          \"type\": \"keyword\"\n" +
                "       },\n" +
                "       \"price\": {\n" +
                "          \"type\": \"float\"\n" +
                "       },\n" +
                "       \"pic\":{\n" +
                "\t\t   \"type\":\"text\",\n" +
                "\t\t   \"index\":false\n" +
                "\t    },\n" +
                "       \"timestamp\": {\n" +
                "      \t\t\"type\":   \"date\",\n" +
                "      \t\t\"format\": \"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd\"\n" +
                "    \t }\n" +
                "  }\n" +
                "}", XContentType.JSON);
        //
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }
    //删除索引库
    @Test
    public void testDeleteIndex() throws IOException {
        //创建“删除索引请求”对象
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("java1906");
        //创建 索引操作客户并  端响应对象
        DeleteIndexResponse indexResponse = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
       //打印boolean结果
        System.out.println(indexResponse.isAcknowledged());
    }
}
