package cn.com.tcsl.fast.kds.server.util;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <b>功能：</b>拼音转换类<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2022/10/31 17:03&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
public class PinyinUtil {

    /**
     * pinyin4j提供的拼音转换类
     */
    private static HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();

    /**
     * 返回汉语拼音全拼,如果包含非汉字非汉字不改变
     */
    public static String getPinyin(String s) throws BadHanyuPinyinOutputFormatCombination {
        if (StringUtils.isBlank(s)) return null;

        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);//大写

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(s.charAt(i), format);
            String pinyin = null;
            if (CollectionUtils.isEmpty(Arrays.asList(pinyins))) {
                //非汉字,取原字符
                pinyin = String.valueOf(s.charAt(i));
            } else {
                pinyin = pinyins[0];
                pinyin = pinyin.substring(0, pinyin.length() - 1);//去掉末尾读音
            }
            result.append(pinyin);
        }
        return result.toString();
    }

    /**
     * 返回汉语拼音简拼(大写),如果包含非汉字非汉字不改变.<br>
     * 例: 鱼香肉丝 - YXRS, 鱼香abc肉丝 - YXabcRS, 城市 - CS.
     *
     * @param s 需要获取拼音的文字
     * @return 首字母拼音大写, 如果非汉字则原封不变返回.
     */
    public static String getPinyinj(String s) {
        if (StringUtils.isBlank(s)) return null;

        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);//大写

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            String[] pinyins = null;
            try {
                pinyins = PinyinHelper.toHanyuPinyinStringArray(s.charAt(i), format);
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                throw new RuntimeException(e);
            }
            String pinyin = null;
            if (CollectionUtils.isEmpty(Arrays.asList(pinyins))) {
                //非汉字,取原字符
                pinyin = String.valueOf(s.charAt(i));
            } else {
                pinyin = pinyins[0];
                pinyin = pinyin.substring(0, 1);//取出拼音首字
            }
            result.append(pinyin);
        }
        return result.toString();
    }

    /**
     * <b>功能描述：</b>获取助记符<br>
     * <b>修订记录：</b><br>
     * <li>20180302&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public static String getMnemonic(String pinyin, String name) {
        if (StringUtils.isEmpty(pinyin)) {
            return getPinyinj(name);
        }
        return pinyin;
    }

    public static boolean isContainEn(String str) {
        Pattern p = Pattern.compile("[a-zA-Z]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }


    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }


    public static void main(String[] args) throws Exception {

       /* String key = "yx1";
        String key2  = "1";
        if(isContainEn(key)){
            System.out.println(getPinyinj("鱼香肉丝").contains(key.toUpperCase()));
        }

        if(isContainEn(key2)){
            System.out.println(getPinyinj("鱼香肉丝").contains(key2));
        }else {
            System.out.println("字符串有数字");
        }*/


       /* System.out.println("全拼: " + getPinyin("鱼香肉丝"));
        System.out.println("简拼: " + getPinyinj("鱼香肉丝"));

        System.out.println("js94153-02-221102-0006".toUpperCase());*/

        System.out.println(isContainChinese("eee"));


    }

}
