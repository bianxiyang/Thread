package cn.com.tcsl.fast.kds.server.event.kds;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * <b>功能：</b>推送修改时间事件<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/2/20 16:31&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
public class PushModifyTimeEvent extends ApplicationEvent {

    @Getter
    private Integer shopId;

    @Getter
    private Map<String, Object> paramMap;

    public PushModifyTimeEvent(Object source, Integer shopId, Map<String, Object> paramMap) {
        super(source);
        this.shopId = shopId;
        this.paramMap = paramMap;
    }
}