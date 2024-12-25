package cn.com.tcsl.fast.kds.server.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

/**
 * <b>功能：</b>日期工具类<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/3/13 20:10&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Slf4j
public class DateUtils {

    /**
     * 获取当前时间的时间戳(10位:不带毫秒)
     */
    public static Long getTimeStamp() {
        LocalDateTime now = LocalDateTime.now();
        return now.toEpochSecond(OffsetDateTime.now().getOffset());
    }

    /**
     * 获取当前时间的时间戳(13位:带毫秒)
     */
    public static Long getTimeStampWithMs() {
        LocalDateTime now = LocalDateTime.now();
        return now.toInstant(OffsetDateTime.now().getOffset()).toEpochMilli();
    }

    /**
     * Long --> String
     */
    public static String long2String(Long time, String pattern) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return dtf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
    }

    /**
     * String --> Long
     */
    public static Long string2Long(String time, String pattern) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime parse = LocalDateTime.parse(time, dtf);
        return LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取本月第一天
     */
    public static LocalDate firstDayOfThisMonth() {
        LocalDate today = LocalDate.now();
        return today.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 获取本月第N天
     */
    public static LocalDate dayOfThisMonth(int n) {
        LocalDate today = LocalDate.now();
        return today.withDayOfMonth(n);
    }

    /**
     * 获取本月最后一天
     */
    public static LocalDate lastDayOfThisMonth() {
        LocalDate today = LocalDate.now();
        return today.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 获取指定月的最后一天
     *
     * @param month 1-12
     */
    public static LocalDate lastDayOfMonth(int year, int month) {
        LocalDate ofDate = LocalDate.of(year, month, 1);
        return ofDate.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 获取本月第一天的开始时间
     */
    public static String startOfThisMonth(String pattern) {
        LocalDateTime ofDateTime = LocalDateTime.of(firstDayOfThisMonth(), LocalTime.MIN);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return ofDateTime.format(dtf);
    }

    /**
     * 取本月最后一天的结束时间
     */
    public static String endOfThisMonth(String pattern) {
        LocalDateTime ofDateTime = LocalDateTime.of(lastDayOfThisMonth(), LocalTime.MAX);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return ofDateTime.format(dtf);
    }

    /**
     * 日期转字符串
     *
     * @param format
     * @param date
     * @return
     */
    public static String dateToStr(String format, Date date) {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format); //设置时间格式
        str = sdf.format(date);
        return str;
    }

    public static long strToDateTime(String str, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        long time = 0L;
        try {
            time = simpleDateFormat.parse(str).getTime();
        } catch (ParseException e) {
            log.error("exception:", e);

        }
        return time;
    }

    public static String secondToDate(long second) {
        String patten = "yyyy-MM-dd HH:mm:ss";
        return new SimpleDateFormat(patten).format(second);
    }

    /**
     * 获取指定时区的时间
     **/
    public static LocalDateTime getCustomizeLocalDateTime(LocalDateTime localDateTime, ZoneIdEnum zoneIdEnum) {
        // 当前系统时区
        ZoneId currentZone = getZone();
        // 新时区
        ZoneId newZone = ZoneId.of(zoneIdEnum.getZoneIdName());
        // 时区转换
        return localDateTime.atZone(currentZone).withZoneSameInstant(newZone).toLocalDateTime();
    }

    /**
     * 获取默认时区
     *
     * @return java.time.ZoneOffset
     **/
    public static ZoneOffset getZone() {
        return OffsetDateTime.now().getOffset();
    }


    public enum ZoneIdEnum {

        /**
         * "Australia/Darwin","澳洲/达尔文"
         */
        ACT("Australia/Darwin", "澳洲/达尔文"),

        /**
         * "Australia/Sydney","澳洲/悉尼"
         */
        AET("Australia/Sydney", "澳洲/悉尼"),

        /**
         * "America/Argentina/Buenos_Aires","美洲/阿根廷/布宜诺斯艾利斯"
         */
        AGT("America/Argentina/Buenos_Aires", "美洲/阿根廷/布宜诺斯艾利斯"),

        /**
         * "Africa/Cairo","非洲/开罗"
         */
        ART("Africa/Cairo", "非洲/开罗"),

        /**
         * "America/Anchorage","美洲/安克雷奇"
         */
        AST("America/Anchorage", "美洲/安克雷奇"),

        /**
         * "America/Sao_Paulo","美洲/圣保罗"
         */
        BET("America/Sao_Paulo", "美洲/圣保罗"),

        /**
         * "Asia/Dhaka","亚洲/达卡"
         */
        BST("Asia/Dhaka", "亚洲/达卡"),

        /**
         * "Africa/Harare","非洲/哈拉雷"
         */
        CAT("Africa/Harare", "非洲/哈拉雷"),

        /**
         * "America/St_Johns","美洲/圣约翰"
         */
        CNT("America/St_Johns", "美洲/圣约翰"),

        /**
         * "America/Chicago","美洲/芝加哥"
         */
        CST("America/Chicago", "美洲/芝加哥"),

        /**
         * "Asia/Shanghai","亚洲/上海"
         */
        CTT("Asia/Shanghai", "亚洲/上海"),

        /**
         * "Africa/Addis_Ababa","非洲/亚的斯亚贝巴"
         */
        EAT("Africa/Addis_Ababa", "非洲/亚的斯亚贝巴"),

        /**
         * "Europe/Paris","欧洲/巴黎"
         */
        ECT("Europe/Paris", "欧洲/巴黎"),

        /**
         * "America/Indiana/Indianapolis","美洲/印第安纳州/印第安纳波利斯"
         */
        IET("America/Indiana/Indianapolis", "美洲/印第安纳州/印第安纳波利斯"),

        /**
         * "Asia/Kolkata","亚洲/加尔各答"
         */
        IST("Asia/Kolkata", "亚洲/加尔各答"),

        /**
         * "Asia/Tokyo","亚洲/东京"
         */
        JST("Asia/Tokyo", "亚洲/东京"),

        /**
         * "Pacific/Apia","太平洋/阿皮亚"
         */
        MIT("Pacific/Apia", "太平洋/阿皮亚"),

        /**
         * "Asia/Yerevan","亚洲/埃里温"
         */
        NET("Asia/Yerevan", "亚洲/埃里温"),

        /**
         * "Pacific/Auckland","太平洋/奥克兰"
         */
        NST("Pacific/Auckland", "太平洋/奥克兰"),

        /**
         * "Asia/Karachi","亚洲/卡拉奇"
         */
        PLT("Asia/Karachi", "亚洲/卡拉奇"),

        /**
         * "America/Phoenix","美洲/凤凰城"
         */
        PNT("America/Phoenix", "美洲/凤凰城"),

        /**
         * "America/Puerto_Rico","美洲/波多黎各"
         */
        PRT("America/Puerto_Rico", "美洲/波多黎各"),

        /**
         * "America/Los_Angeles","美洲/洛杉矶"
         */
        PST("America/Los_Angeles", "美洲/洛杉矶"),

        /**
         * "Pacific/Guadalcanal","太平洋/瓜达尔卡纳尔岛"
         */
        SST("Pacific/Guadalcanal", "太平洋/瓜达尔卡纳尔岛"),

        /**
         * "Asia/Ho_Chi_Minh","亚洲/胡志明市"
         */
        VST("Asia/Ho_Chi_Minh", "亚洲/胡志明市"),

        /**
         * "-05:00","东部标准时间"（纽约、华盛顿）
         */
        EST("-05:00", "东部标准时间"),

        /**
         * "-07:00","山地标准时间"
         */
        MST("-07:00", "山地标准时间"),

        /**
         * "-10:00","夏威夷-阿留申标准时区"
         */
        HST("-10:00", "夏威夷-阿留申标准时区"),
        ;

        private final String zoneIdName;
        private final String zoneIdNameCn;

        public String getZoneIdName() {
            return zoneIdName;
        }

        public String getZoneIdNameCn() {
            return zoneIdNameCn;
        }

        ZoneIdEnum(String zoneIdName, String zoneIdNameCn) {
            this.zoneIdName = zoneIdName;
            this.zoneIdNameCn = zoneIdNameCn;
        }
    }


    public static Date dateRoll(Date date, int i, int d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(i, d);
        date = calendar.getTime();
        return date;
    }


    public static void main(String[] args) {
        System.out.println(firstDayOfThisMonth());
        System.out.println(dayOfThisMonth(7));
        System.out.println(lastDayOfThisMonth());
        System.out.println(lastDayOfMonth(2020, 2));
        System.out.println(startOfThisMonth("yyyy-MM-dd HH:mm:ss"));
        System.out.println(endOfThisMonth("yyyy-MM-dd HH:mm:ss"));
        System.out.println(dateToStr("yyyy-MM-dd HH:mm:ss", new Date()));
        System.out.println(strToDateTime("2021-09-03 15:50:00", "yyyy-MM-dd HH:mm:ss"));
    }
}
