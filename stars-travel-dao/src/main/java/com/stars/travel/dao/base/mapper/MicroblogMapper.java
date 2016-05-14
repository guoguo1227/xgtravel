package com.stars.travel.dao.base.mapper;

import com.stars.travel.dao.base.BaseMapper;
import com.stars.travel.model.base.Microblog;
import com.stars.travel.model.base.MicroblogCriteria;
import com.stars.travel.model.base.MicroblogWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MicroblogMapper extends BaseMapper {
    int countByExample(MicroblogCriteria example);

    int deleteByExample(MicroblogCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(MicroblogWithBLOBs record);

    int insertSelective(MicroblogWithBLOBs record);

    List<MicroblogWithBLOBs> selectByExampleWithBLOBs(MicroblogCriteria example);

    List<Microblog> selectByExample(MicroblogCriteria example);

    MicroblogWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MicroblogWithBLOBs record, @Param("example") MicroblogCriteria example);

    int updateByExampleWithBLOBs(@Param("record") MicroblogWithBLOBs record, @Param("example") MicroblogCriteria example);

    int updateByExample(@Param("record") Microblog record, @Param("example") MicroblogCriteria example);

    int updateByPrimaryKeySelective(MicroblogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(MicroblogWithBLOBs record);

    int updateByPrimaryKey(Microblog record);
}