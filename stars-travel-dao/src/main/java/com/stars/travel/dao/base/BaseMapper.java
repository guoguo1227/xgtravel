package com.stars.travel.dao.base;


import com.stars.travel.model.base.BaseBean;
import com.stars.travel.model.base.BaseCriteria;

import java.util.List;

/**
 * Created by guo on 2015/12/20.
 */
public interface BaseMapper {

   int countByExample(BaseCriteria var1);

   List<BaseBean> selectByExample(BaseCriteria var1);
}
