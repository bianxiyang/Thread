package cn.com.tcsl.fast.kds.server.dao;



import cn.com.tcsl.fast.kds.server.model.KdsItemStep;

import java.util.List;
import java.util.Map;

public interface KdsItemStepMapper {
    int deleteByPrimaryKey(Long id);

    int insert(KdsItemStep record);

    int insertSelective(KdsItemStep record);

    KdsItemStep selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(KdsItemStep record);

    int updateByPrimaryKey(KdsItemStep record);

    List<KdsItemStep> queryKdsItemStep(Map<String, Object> map);
}