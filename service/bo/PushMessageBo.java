package cn.com.tcsl.fast.kds.server.service.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * <b>功能：</b>推送消息对象<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/3/13 20:05&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Data
public class PushMessageBo implements Serializable {

    private static final long serialVersionUID = -6683540541577056251L;

    /**
     * 类型：1：给指定POS发消息 5:给全部POS发消息
     */
    private String type;

    /**
     * POS_ID
     */
    private String key;

    /**
     * 消息内容,需要是json字符串
     */
    private String message;

}
