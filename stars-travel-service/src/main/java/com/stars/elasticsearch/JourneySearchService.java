package com.stars.elasticsearch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stars.travel.dao.ext.mapper.JourneyVoMapper;
import com.stars.travel.model.condition.SearchCondition;
import com.stars.travel.model.ext.JourneyVo;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import static com.stars.constants.ElasticsearchConstant.*;

/**
 * Description : 行程分享索引查询服务
 * Author : guo
 * Date : 2016/7/31 13:36
 */
@Service
public class JourneySearchService {

    private final static Logger logger = LoggerFactory.getLogger(JourneySearchService.class);

    public static Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd HH:mm:ss").create();

    @Autowired
    private JourneyVoMapper journeyVoMapper;
    /**
     * @Description : 查询符合条件的所有id
     * @param condtion
     * @return
     */
    public List<Integer> queryJourneyVoList(SearchCondition condtion){

        List<Integer> ids = new ArrayList<>();
        if(condtion == null) {
            return ids;
        }

        BoolQueryBuilder boolQueryBuilder = null;

        // 依据查询索引库名称创建查询索引
        SearchRequestBuilder searchRequestBuilder = ElasticSearchTool.client.prepareSearch(ES_INDEX);
        //设置查询文档,表名
        searchRequestBuilder.setTypes(ES_TYPE_JOURNEY);
        //设置查询类型
        searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);

        //设置查询条件
        boolQueryBuilder = getQueryBuilder(condtion);

        // 设置是否按查询匹配度排序
        searchRequestBuilder.setExplain(true);

        SearchResponse response = searchRequestBuilder
                .setQuery(boolQueryBuilder)
                .execute()
                .actionGet();

        for (SearchHit hit : response.getHits().getHits()) {
            String fields = hit.getSourceAsString();
            JourneyVo vo = gson.fromJson(fields,JourneyVo.class);
            ids.add(vo.getId());
        }
        return ids;
    }

    /**
     * @Description :　拼接查询字段
     * @param condtion
     * @return
     */
    private static BoolQueryBuilder getQueryBuilder(SearchCondition condtion){
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if(null != condtion){
            //内容
            if(!StringUtils.isBlank(condtion.getSearchContent())){

                BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
                boolQuery.should(QueryBuilders.matchPhraseQuery("content",condtion.getSearchContent()));
                boolQuery.should(QueryBuilders.matchPhraseQuery("description",condtion.getSearchContent()));
                boolQuery.should(QueryBuilders.matchPhraseQuery("title",condtion.getSearchContent()));
                boolQuery.should(QueryBuilders.matchPhraseQuery("destination",condtion.getSearchContent()));
                boolQueryBuilder.must(boolQuery);
            }
            //目的地
            if(!StringUtils.isBlank(condtion.getDestination())){
                boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("destination", condtion.getSearchContent()));
            }
        }
        //未删除
        boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("isEnable", true));

        return boolQueryBuilder;
    }

    /**
     * @Description:添加索引
     * @param id
     */
    public void addJourneyIndex(Integer id){
        if(null != id){

            SearchCondition searchCondition = new SearchCondition();
            searchCondition.setId(id);
            List<JourneyVo> vo = journeyVoMapper.queryJourneyVoList(searchCondition);
            if(!CollectionUtils.isEmpty(vo)){
                JourneyVo journeyVo = vo.get(0);
                if(null != journeyVo){
                    //添加索引
                    updateJourneyIndex(journeyVo);
                }
            }
        }
    }

    /**
     * @Description:更新索引
     * @param vo
     */
    public void updateJourneyIndex(JourneyVo vo){

        //判断索引是否存在
        String json = ElasticSearchTool.getIndexById(ES_INDEX,ES_TYPE_JOURNEY,vo.getId().toString());
        if(StringUtils.isBlank(json)){
            addJourneyIndex(vo);  //索引不存在建立索引
        }else{
            String objJoson = gson.toJson(vo);
            ElasticSearchTool.updateById(objJoson,ES_INDEX,ES_TYPE_JOURNEY,vo.getId().toString()); //更新索引
        }
    }

    /**
     * @Description:添加行程分享索引
     * @param vo
     */
    public void addJourneyIndex(JourneyVo vo){
        String objJoson = gson.toJson(vo);
        ElasticSearchTool.createIndex(objJoson,ES_INDEX,ES_TYPE_JOURNEY,vo.getId().toString());
    }
}
