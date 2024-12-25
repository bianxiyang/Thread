package cn.com.tcsl.fast.kds.server.event.kds;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * <b>功能：</b>推送吾享事件<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/9/4 14:07&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
public class PushWuuEvent extends ApplicationEvent {

    @Getter
    private Integer shopId;

    @Getter
    private Map<String, Object> paramMap;

    @Getter
    private String mcId;

    public PushWuuEvent(Object source, Integer shopId, Map<String, Object> paramMap, String mcId) {
        super(source);
        this.shopId = shopId;
        this.paramMap = paramMap;
        this.mcId = mcId;
    }
}
