package cn.com.tcsl.fast.kds.server.util;

/**********
 * 描述：
 * @author 卢晓彬
 * @Create：2018-11-16
 *
 * 修订历史：日期     修订人     修订内容
 *
 */
public class StrUtil {


    /**
     * <b>功能描述：</b>截取取消原因字符串<br>
     * <b>修订记录：</b><br>
     * <li>20181127&nbsp;&nbsp;|&nbsp;&nbsp;卢晓彬&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     * @param str 处理字符串
     * @param len 长度
     * @return String
     *
     */
    public static String cutString(String str,Integer len){
        if (str!=null&&!str.isEmpty()&&
                len.compareTo(str.length())<0){
            str = str.substring(0,len);
        }
        return str;
    }
}
