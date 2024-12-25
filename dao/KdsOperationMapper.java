package cn.com.tcsl.fast.kds.server.dao;

import cn.com.tcsl.fast.kds.server.model.KdsOperation;

public interface KdsOperationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(KdsOperation record);

    int insertSelective(KdsOperation record);

    KdsOperation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(KdsOperation record);

    int updateByPrimaryKey(KdsOperation record);
}