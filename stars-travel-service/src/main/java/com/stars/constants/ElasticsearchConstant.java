package com.stars.constants;

/**
 * @author : samuel
 * @Description : ES 搜索常量
 * @Date : 16-4-11
 */
public class ElasticsearchConstant {

    public final static String ES_APPLICATION_ADDRESS = ConfigUtil.getInstance().getProperties().getProperty("ES.ADDRESS");  //ES服务器集群地址

    public final static String ES_CLUSTER_NAME = "cluster.name"; //ES应用名称属性

    public final static String ES_CLUSTER_VALUE = "travelsElasticSearch";        //ES应用名称值

    public final static String ES_INDEX = "foot";           //ES索引名称

    public final static String ES_TYPE_JOURNEY = "journey";    //行程表索引

    public final static String ES_TYPE_MICROBLOG = "microblog";    //微游记索引

    public final static String ES_ANALYSIS_IK = "ik";            //ik中文分词器

}
