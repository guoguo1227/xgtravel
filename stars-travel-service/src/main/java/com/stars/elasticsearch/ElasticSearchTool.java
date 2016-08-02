package com.stars.elasticsearch;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import static com.stars.constants.ElasticsearchConstant.*;

/**
 * @author : samuel
 * @Description : ES 工具类
 * @Date : 16-4-11
 */
public class ElasticSearchTool {

    private final static Logger logger = LoggerFactory.getLogger(ElasticSearchTool.class);

    public static Client client = null;

     /**
     * @Description : 创建连接
     * @return
     */
    static {
        try {
            Settings settings = Settings.settingsBuilder().put(ES_CLUSTER_NAME,ES_CLUSTER_VALUE).build();
            TransportClient build = TransportClient.builder()
                    .settings(settings)
                    .build();

            logger.info("开始创建ES搜索服务器:"+ES_APPLICATION_ADDRESS+"的连接");
            if (ES_APPLICATION_ADDRESS != null) {
                String[] adds = ES_APPLICATION_ADDRESS.split(",");
                for (String add : adds) {
                    String[] hostport = add.split(":");
                    build.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostport[0]), Integer.parseInt(hostport[1])));
                }
            }
            client = build;
            logger.info("成功创建ES搜索服务器:"+ES_APPLICATION_ADDRESS+"的连接");
        } catch (UnknownHostException e) {
            logger.info("创建ES搜索服务器:" + ES_APPLICATION_ADDRESS + "的连接失败," + e.toString());
        }
    }

    /**
     * @Description : 创建索引
     * @param json  要建索引对象的json字符串
     * @param ESIndex 索引名称
     * @param ESType  索引类型(表名)
     * @param id      索引id
     */
    public static void createIndex(String json,String ESIndex,String ESType,String id){

        if(!StringUtils.isBlank(id)){
            //logger.info("开始创建类型为:"+ESType+",id为:"+id+"的索引");
            IndexResponse response = client.prepareIndex(ESIndex, ESType,id).setSource(json).get();
        }else{
            //logger.info("开始创建类型为:"+ESType+",id为自增的索引");
            IndexResponse response = client.prepareIndex(ESIndex, ESType).setSource(json).get();
        }
        logger.info("成功创建类型为:"+ESType+",id为:"+id+"的索引");
    }

    /**
     * @Description : 从索引中查询单条数据
     * @param ESIndex 索引名称
     * @param ESType  索引类型(表名)
     * @param id      索引id
     */
    public static String getIndexById(String ESIndex,String ESType,String id){
        logger.info("查询id为:"+id+"的"+ESType+"索引数据");
        String json = "";
        GetResponse response = client.prepareGet(ESIndex, ESType, id).get();

        if (response.isExists()) {   //检查文档是否存在
            json = response.getSourceAsString();  //访问_source字段
            logger.info("成功查询id为:"+id+"的"+ESType+"索引数据");
        }
        return json;
    }

    /**
     * @Description :删除索引数据
     * @param ESIndex 索引名称
     * @param ESType  索引类型(表名)
     * @param id      索引id
     */
    public static void deleteIndexById(String ESIndex,String ESType,String id){
        //logger.info("开始删除id为:"+id+"的"+ESType+"索引数据");
        DeleteResponse response = client.prepareDelete(ESIndex, ESType,id)
                .get();
        logger.info("成功删除id为:"+id+"的"+ESType+"索引数据");
    }

    /**
     * @Description : 更新索引
     * @param json  要更新索引对象的json字符串
     * @param ESIndex 索引名称
     * @param ESType  索引类型(表名)
     * @param id      索引id
     */
    public static void updateById(String json,String ESIndex,String ESType,String id){

        //logger.info("开始更新id为:"+id+"的"+ESType+"索引数据");

        IndexRequest indexRequest = new IndexRequest(ESIndex, ESType,id).source(json);
        UpdateRequest updateRequest = null;
        try {
            updateRequest = new UpdateRequest(ESIndex, ESType,id)
                    .doc(json)
                    .upsert(indexRequest);
            client.update(updateRequest).get();
            logger.info("成功更新id为:"+id+"的"+ESType+"索引数据");
        } catch (Exception e) {
            logger.info("更新id为:" + id + "的" + ESType + "索引数据失败," + e.toString());
        }
    }
}
