package cn.com.tcsl.fast.kds.server.service.client;

import cn.com.tcsl.fast.starter.feign.config.FeignLogConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * <b>功能：</b>吾享接口<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/3/14 13:43&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@FeignClient(name = "wuuPayApi",url = "${wuu.api.url}",configuration = FeignLogConfiguration.class)
public interface WuuUpladApi {


    //  保存繁忙程度
    @RequestMapping(path = "/wuuxiangbase/externalapi/org/mcbusy/saveUpdateMcBusyInfo.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,headers = {"Tcsl-ProductCode=${wuu.api.product.code}","Tcsl-productVersion=${wuu.api.product.version}"})
    ResponseEntity<String> saveUpdateMcBusyInfo(Map<String, ?> data, @RequestHeader("tcslKey") String headerTcslKey, @RequestHeader("Tcsl-Shardingkey") String mcId);


    




}
