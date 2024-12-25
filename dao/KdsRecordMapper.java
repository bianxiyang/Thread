package cn.com.tcsl.fast.kds.server.dao;

import cn.com.tcsl.fast.kds.server.model.KdsRecord;

import java.util.List;
import java.util.Map;

public interface KdsRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(KdsRecord record);

    int insertSelective(KdsRecord record);

    KdsRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(KdsRecord record);

    int updateByPrimaryKey(KdsRecord record);

    List<KdsRecord> queryKdsRecord(Map<String, Object> map);
}