package cn.com.tcsl.fast.kds.server.controller;

import cn.com.tcsl.fast.common.model.ResponseResult;
import cn.com.tcsl.fast.kds.cnd.KdsMissedOrderCnd;
import cn.com.tcsl.fast.kds.server.service.KdsCOMPService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <b>功能：kds补偿（KdsCompensateController）</b><br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/03/17 17:53&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;杨彦岭&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Slf4j
@RestController
@RequestMapping(value = "${spring.mvc.prefix}/kdsCompensate")
public class KdsCOMPController {

    @Resource
    private KdsCOMPService kdsCOMPService;
    @ApiOperation(value = "kds发餐补偿机制", notes = "kds发餐补偿机制", httpMethod = "POST")
    @PostMapping(path = "/pushMissedOrder")
    public ResponseEntity<ResponseResult> pushMissedOrder(@Valid @RequestBody KdsMissedOrderCnd query) {
        return ResponseEntity.ok(kdsCOMPService.pushMissedOrder(query));
    }

}
