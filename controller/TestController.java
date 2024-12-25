package cn.com.tcsl.fast.kds.server.controller;


import cn.com.tcsl.fast.base.vo.biz.KitchenItemPlanOverAllVo;
import cn.com.tcsl.fast.common.model.ResponseResult;
import cn.com.tcsl.fast.common.util.Maps;
import cn.com.tcsl.fast.framework.controller.GenericController;
import cn.com.tcsl.fast.kds.cnd.KdsUpdateCnd;
import cn.com.tcsl.fast.kds.server.constant.KdsConstant;
import cn.com.tcsl.fast.kds.server.event.EventPublisher;
import cn.com.tcsl.fast.kds.server.event.kds.BackUpOrderEvent;
import cn.com.tcsl.fast.kds.server.event.kds.PushModifyTimeEvent;
import cn.com.tcsl.fast.kds.server.model.KdsSetting;
import cn.com.tcsl.fast.kds.server.service.BaseInterfaceService;
import cn.com.tcsl.fast.kds.server.service.KdsSettingService;
import cn.com.tcsl.fast.kds.server.util.PrItemFormatUtils;
import cn.com.tcsl.fast.kds.vo.KdsQueryOrder;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <b>功能：kds项目测试类</b><br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2022/4/20 14:53&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */

@Slf4j
@RestController
@RequestMapping(value = "${spring.mvc.prefix}/kds")
public class TestController extends GenericController {
    @ApiOperation(value = "post请求调用示例", notes = "test接口", httpMethod = "POST")
    @PostMapping(path = "/test")
    public ResponseEntity<ResponseResult> test(@RequestBody KdsUpdateCnd kdsUpdateCnd) {
        log.debug("test ok!");

        String reg = "^YY[abc](0-9)$";

        return ResponseEntity.ok(ResponseResult.success("test ok!"));

    }

    public static void main(String[] args) {


        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(TestController::aaa,0,1, TimeUnit.SECONDS);

    }


    static void aaa(){
        System.out.println("6666");
    }


}
