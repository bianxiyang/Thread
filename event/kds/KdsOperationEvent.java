package cn.com.tcsl.fast.kds.server.event.kds;

import cn.com.tcsl.fast.kds.server.enums.SceneTypeEnum;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * <b>功能：</b>kds操作记录事件<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2024/11/6 13:44&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
public class KdsOperationEvent extends ApplicationEvent {

    @Getter
    private Integer shopId;
    @Getter
    private Object o;
    @Getter
    private SceneTypeEnum sceneTypeEnum;

    @Getter
    private String traceId;


    public KdsOperationEvent(Object source, Integer shopId, Object o, SceneTypeEnum sceneTypeEnum,String traceId) {
        super(source);
        this.shopId = shopId;
        this.o = o;
        this.sceneTypeEnum = sceneTypeEnum;
        this.traceId = traceId;
    }
}
