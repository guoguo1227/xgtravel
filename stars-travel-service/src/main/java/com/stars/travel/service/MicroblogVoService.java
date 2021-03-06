package com.stars.travel.service;

import com.stars.common.utils.Page;
import com.stars.travel.model.base.Microblog;
import com.stars.travel.model.condition.SearchCondition;
import com.stars.travel.model.ext.MicroblogVo;
import com.stars.travel.model.ext.RequestResult;

import java.util.List;

/**
 * Description : 微游记服务接口
 * Author : guo
 * Date : 2016/2/29 23:05
 */
public interface MicroblogVoService {

    /**
     * @Description : 查询分享微游记分页列表
     * @param condition
     * @return
     */
    public Page<MicroblogVo> querySharedMicroblogVoPage(SearchCondition condition, String currentPhone);


    /**
     * @Description : 获取分享微游记列表 -移动端
     * @param condition
     * @return
     */
    public List<MicroblogVo> querySharedMicroblogVoApp(SearchCondition condition, String currentPhone);

    /**
     * @Description: 获取分享微游记搜索接口
     * @param condition
     * @param currentPhone
     * @return
     */
    public List<MicroblogVo> searchMicroblogVoApp(SearchCondition condition, String currentPhone);

    /**
     * @Description: 推荐微游记
     * @return
     */
    public List<Microblog> queryRecommendMicroblog();
    /**
     * @Description : 我的微游记收藏
     * @param condition
     * @param currentPhone
     * @return
     */
    public List<MicroblogVo> queryMyCollection(SearchCondition condition, String currentPhone);
    /**
     * @Description : 查询分享微游记总数
     * @param condition
     * @return
     */
    public int countSharedMicroblogVoList(SearchCondition condition);

    /**
     * @Description : 添加微游记
     * @param microblogVo
     * @return
     */
    public boolean addMicroblog(MicroblogVo microblogVo);

    /**
     * @Description : 删除微游记
     * @param id
     * @return
     */
    public boolean deleteMicroblog(Integer id);

    /**
     * @Description: 删除自己的微游记
     * @param id
     * @param currentPhone
     * @return
     */
    public boolean deleteMyMicroblg(String currentPhone,Integer id);

    /**
     * @Description : 收藏微游记
     * @param id
     * @param currentPhone
     * @return
     */
    public boolean collectMicroblog(Integer id,String currentPhone);

    /**
     * @Description : 取消收藏微游记
     * @param id
     * @param currentPhone
     * @return
     */
    public boolean uncollectMicroblog(Integer id,String currentPhone);

    /**
     * @Description : 验证用户是否收藏,顶赞该微游记
     * @param id
     * @param currentPhone
     * @return
     */
    public boolean ifCollectionTop(Integer id,String currentPhone,Integer type);

    /**
     * @Description : 顶，赞微游记
     * @param id
     * @return
     */
    public RequestResult topMicroblog(Integer id, String currentPhone);

    /**
     * @Description :　取消顶赞微游记
     * @param id
     * @param currentPhone
     * @return
     */
    public RequestResult untopMicroblog(Integer id, String currentPhone);

    public void addMicroblogExtAttr(MicroblogVo vo,String currentPhone);

    }
