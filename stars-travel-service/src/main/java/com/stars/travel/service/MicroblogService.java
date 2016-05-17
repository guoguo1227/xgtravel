package com.stars.travel.service;
import com.stars.travel.model.base.MicroblogWithBLOBs;

import java.util.List;

/**
 * Description : 微游记服务层接口
 * Author : guo
 * Date : 2016/2/25 21:33
 */
public interface MicroblogService {

    public List<MicroblogWithBLOBs> querySharedMicroblogs();

    public List<MicroblogWithBLOBs> queryMicroblogByUser(String phone);

    public MicroblogWithBLOBs addMicroblog(MicroblogWithBLOBs microblogWithBLOBs);

    public MicroblogWithBLOBs updateMicroblog(MicroblogWithBLOBs microblogWithBLOBs);

    public boolean deleteMicroblog(Integer id);
}
