package cn.com.tcsl.fast.kds.server.util;

import cn.com.tcsl.fast.framework.exception.ApplicationError;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>功能：</b>Rest接口请求工具类<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;
 * 变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20180718&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Component
@Slf4j
public class RestClient {

    /**
     * <b>功能描述：</b>POST请求——请求参数和header都为空<br>
     * <b>修订记录：</b><br>
     * <li>20180718&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param url 请求地址
     */
    public static Map<String, Object> post(String url) throws RestClientException {
        return post(url, "");
    }

    /**
     * <b>功能描述：</b>POST请求——header为空<br>
     * <b>修订记录：</b><br>
     * <li>20180718&nbsp;&nbsp;|&nbsp;&nbsp;赵伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param url  请求地址
     * @param body 请求参数
     */
    public static <T> Map<String, Object> post(String url, T body) throws RestClientException {
        return post(url, null, body);
    }


    /**
     * <b>功能描述：</b>POST请求<br>
     * <b>修订记录：</b><br>
     * <li>20180301&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param url  请求地址
     * @param body 请求参数
     */
    public static <T> Map<String, Object> post(String url, MultiValueMap<String, String> headers, T body)
            throws RestClientException {
        return request(url, HttpMethod.POST, headers, body);
    }

    /**
     * <b>功能描述：</b>根据请求方法请求接口<br>
     * <b>修订记录：</b><br>
     * <li>20180718&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param url     请求地址
     * @param method  请求方法
     * @param body    请求参数
     * @param headers 请求头
     */
    public static <T> Map<String, Object> request(String url, HttpMethod method, MultiValueMap<String,
            String> headers, T body) throws RestClientException {

        // 响应封装到map中
        Map<String, Object> map = new HashMap<>();

        // 设置超时
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(30000);
        requestFactory.setReadTimeout(30000);

        // 构造rest请求模板对象
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        // 构造请求实体
        HttpEntity<T> request = new HttpEntity<>(body, headers);
        try {
            // rest请求
            ResponseEntity<String> response = restTemplate.exchange(url, method, request, String.class);
            // 响应结果处理
            if (response != null) {
                HttpStatus httpStatus = response.getStatusCode();
                if (httpStatus != null) {
                    map.put("httpCode", httpStatus.toString());
                }
                String message = response.getBody();
                if (!StringUtils.isEmpty(message)) {
                    map.put("message", message);
                }
            }
        } catch (HttpStatusCodeException var1) {
            map.put("message", var1.getMessage());
            map.put("httpCode", var1.getStatusCode().toString());
        } catch (RestClientException var2) {
            map.put("message", var2.getMessage());
        }
        return map;
    }


    public static <T> T getResponseEntity(ResponseEntity<String> responseEntity, Class<T> tClass) {
        int httpStatusCode = responseEntity.getStatusCodeValue();
        String entityText = responseEntity.getBody();
        // 记录日志 {"code":"0","result":0,"msg":"请求成功","errorMsg":"","data":{"id":"9012100000000037","code":"780","name":"hsk自定义支付方式","discountRate":0.8000,"ticketValue":0.0000,"paywayTypeId":"511","incomeMoney":0.0000,"isEnableTicketClass":false,"partakeIntegral":0,"isUseTicketFirst":false,"exchangeRate":1.00000,"isStandardMoney":false,"isUseTicketRules":false}}
        log.info("返回状态：{0}, 返回的文本：{1}", httpStatusCode, entityText);
        T object = null;
        if (httpStatusCode == 200) {
            Map<String, Object> result = (Map<String, Object>) JSONObject.parse(entityText);
            if ("0".equals(String.valueOf(result.get("code")))) {
                object = JSONObject.parseObject(JSONObject.toJSONString(result.get("data")), tClass);
            } else {
                throw new ApplicationError("S1.ERR.10020", String.valueOf(result.get("msg")));
            }
        } else {
            throw new RuntimeException("系统异常,httpStatusCode:" + httpStatusCode);
        }
        return object;
    }

}
