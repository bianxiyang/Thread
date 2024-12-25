package cn.com.tcsl.fast.kds.server.util;

import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.lang.StringUtils;

/**
 * <b>功能：</b>特殊字符过滤工具类<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/3/13 20:10&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
public class PrItemFormatUtils {

    public static final String regEx = "[`~@#$%̥́ • ˍ ̀ू^&*()+=|{}\\[\\]<>/~！@#￥%……&*——+|{}【】\\ufe0f]";

    /**
     * 去掉特殊标点符号
     *
     * @param origStr 源字符串
     * @return
     */
    public static String specialCharFilter(String origStr) {
        return StringUtils.isNotBlank(origStr) ? origStr.replaceAll(regEx, "").trim() : origStr;
    }

    /**
     * 去除表情特殊字符
     *
     * @param origStr 源字符串
     * @return
     */
    public static String emojiFilter(String origStr) {
        return StringUtils.isNotBlank(origStr) ? EmojiParser.removeAllEmojis(origStr).trim() : origStr;
    }

    /**
     * 去除表情和标点符号
     *
     * @param origStr
     * @return
     */
    public static String emojiAndSpecialCharFilter(String origStr) {
        return StringUtils.isNotBlank(origStr) ? EmojiParser.removeAllEmojis(origStr.replaceAll(regEx, "")).trim() : origStr;
    }

}
