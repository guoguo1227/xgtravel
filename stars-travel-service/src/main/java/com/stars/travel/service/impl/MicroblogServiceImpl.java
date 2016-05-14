package com.stars.travel.service.impl;

import com.stars.travel.dao.base.mapper.MicroblogMapper;
import com.stars.travel.model.base.MicroblogCriteria;
import com.stars.travel.model.base.MicroblogWithBLOBs;
import com.stars.travel.service.MicroblogService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description :
 * Author : guo
 * Date : 2016/2/25 21:34
 */
@Service
public class MicroblogServiceImpl implements MicroblogService {

    private MicroblogMapper microblogMapper;

    @Override
    public List<MicroblogWithBLOBs> querySharedMicroblogs() {
        List<MicroblogWithBLOBs> list = null;
        MicroblogCriteria criteria = new MicroblogCriteria();
        criteria.createCriteria().andIsSharedEqualTo(true).andIsEnableEqualTo(true); //可用，分享
        criteria.setOrderByClause(" createtime desc");
        list = microblogMapper.selectByExampleWithBLOBs(criteria);
        return list;
    }

    @Override
    public List<MicroblogWithBLOBs> queryMicroblogByUser(String phone) {
        List<MicroblogWithBLOBs> list = null;
        MicroblogCriteria criteria = new MicroblogCriteria();
        criteria.createCriteria().andPhoneEqualTo(phone).andIsEnableEqualTo(true); //可用
        criteria.setOrderByClause(" createtime desc");

        list = microblogMapper.selectByExampleWithBLOBs(criteria);
        return list;
    }

    @Override
    public MicroblogWithBLOBs addMicroblog(MicroblogWithBLOBs microblogWithBLOBs) {
        if(null != microblogWithBLOBs && null != microblogWithBLOBs.getPhone()){
            int i = microblogMapper.insertSelective(microblogWithBLOBs);
            if(i>0){
                return microblogWithBLOBs;
            }
        }
        return null;
    }

    @Override
    public MicroblogWithBLOBs updateMicroblog(MicroblogWithBLOBs microblogWithBLOBs) {
        if(null != microblogWithBLOBs && null != microblogWithBLOBs.getPhone()){
            int i = microblogMapper.updateByPrimaryKeySelective(microblogWithBLOBs);
            if(i>0){
                return microblogWithBLOBs;
            }
        }
        return null;
    }

    @Override
    public boolean deleteMicroblog(Integer id) {
        if(null != id){
            int i = microblogMapper.deleteByPrimaryKey(id);
            if( i>0 ){
                return true;
            }
        }
        return false;
    }
}
