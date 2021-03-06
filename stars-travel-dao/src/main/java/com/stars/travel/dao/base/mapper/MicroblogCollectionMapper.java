package com.stars.travel.dao.base.mapper;

import com.stars.travel.dao.base.BaseMapper;
import com.stars.travel.model.base.MicroblogCollection;
import com.stars.travel.model.base.MicroblogCollectionCriteria;
import com.stars.travel.model.base.MicroblogCollectionKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MicroblogCollectionMapper extends BaseMapper {
    int countByExample(MicroblogCollectionCriteria example);

    int deleteByExample(MicroblogCollectionCriteria example);

    int deleteByPrimaryKey(MicroblogCollectionKey key);

    int insert(MicroblogCollection record);

    int insertSelective(MicroblogCollection record);

    List<MicroblogCollection> selectByExample(MicroblogCollectionCriteria example);

    MicroblogCollection selectByPrimaryKey(MicroblogCollectionKey key);

    int updateByExampleSelective(@Param("record") MicroblogCollection record, @Param("example") MicroblogCollectionCriteria example);

    int updateByExample(@Param("record") MicroblogCollection record, @Param("example") MicroblogCollectionCriteria example);

    int updateByPrimaryKeySelective(MicroblogCollection record);

    int updateByPrimaryKey(MicroblogCollection record);
}