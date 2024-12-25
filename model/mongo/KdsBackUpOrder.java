package cn.com.tcsl.fast.kds.server.model.mongo;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
/**
 * <b>功能：</b>kds备份单据实体类<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/3/13 20:03&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Data
@Document(collection = "kds_backup_order")
public class KdsBackUpOrder implements Serializable {


    @Id
    private Long id;
    /**
     * 店铺id
     */
    private Integer shopId;
    /**
     * 订单唯一标识
     */
    @Indexed
    private Long bsId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 过期时间(7天)
     */
    /*@Indexed(expireAfterSeconds = 604800)
    private Date expireTime;*/

    @Indexed(expireAfterSeconds = 10)
    private Date expireTime;


    private String jsonObject;


}
