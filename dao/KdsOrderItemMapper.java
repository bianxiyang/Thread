package cn.com.tcsl.fast.kds.server.dao;

import cn.com.tcsl.fast.kds.server.model.KdsOrderItem;

import java.util.List;
import java.util.Map;
/**
 * <b>功能：</b>单据品项持久层<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/3/13 19:58&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
public interface KdsOrderItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(KdsOrderItem record);

    int insertSelective(KdsOrderItem record);

    KdsOrderItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(KdsOrderItem record);

    int updateByPrimaryKey(KdsOrderItem record);

    List<KdsOrderItem> queryKdsOrderItems(Map<String, Object> map);

    List<KdsOrderItem> queryKdsOrderItemsAndMethod(Map<String, Object> map);





}