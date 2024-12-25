package cn.com.tcsl.fast.kds.server.dao;

import cn.com.tcsl.fast.kds.server.model.KdsOrder;
import cn.com.tcsl.fast.kds.vo.KdsOrderList;

import java.util.List;
import java.util.Map;
/**
 * <b>功能：</b>KDS订单持久层<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/3/13 19:58&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
public interface KdsOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(KdsOrder kdsOrder);

    int insertSelective(KdsOrder kdsOrder);

    KdsOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(KdsOrder kdsOrder);

    int updateByPrimaryKey(KdsOrder kdsOrder);

    List<KdsOrder> queryKdsOrder(Map<String,Object> map);


    List<KdsOrder> queryKdsOrderPick(Map<String,Object> map);

    List<KdsOrder> queryKdsOrderPickRefactor(Map<String,Object> map);



    List<KdsOrderList> queryKdsOrderList(Map<String,Object> map);

}