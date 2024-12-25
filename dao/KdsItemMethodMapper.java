package cn.com.tcsl.fast.kds.server.dao;

import cn.com.tcsl.fast.kds.server.model.KdsItemMethod;

import java.util.List;
import java.util.Map;
/**
 * <b>功能：</b>品项做法持久层<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/3/13 19:58&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
public interface KdsItemMethodMapper {
    int deleteByPrimaryKey(Long id);

    int insert(KdsItemMethod kdsItemMethod);

    int insertSelective(KdsItemMethod kdsItemMethod);

    KdsItemMethod selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(KdsItemMethod kdsItemMethod);

    int updateByPrimaryKey(KdsItemMethod kdsItemMethod);

    List<KdsItemMethod> queryKdsItemMethod(Map<String, Object> map);
}