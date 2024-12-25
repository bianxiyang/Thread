package cn.com.tcsl.fast.kds.server.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateSplitUtils {


    public static List<Date> findDates(String dateType, Date dBegin, Date dEnd,int time) throws Exception {
        List<Date> listDate = new ArrayList<>();
        listDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dEnd);
        while (calEnd.after(calBegin)) {
            if ("H".equals(dateType)){
                calBegin.add(Calendar.HOUR, time);
            }
            if ("M".equals(dateType)){
                calBegin.add(Calendar.MONTH, time);
            }
            if ("D".equals(dateType)){
                calBegin.add(Calendar.DATE, time);
            }
            if ("N".equals(dateType)){
                calBegin.add(Calendar.MINUTE , time);
            }
            listDate.add(calBegin.getTime());
        }
        return listDate;
    }


    public static List<String> findDatesStr(String dateType, Date dBegin, Date dEnd,int time) throws Exception {
        List<String> listDate = new ArrayList<>();
        listDate.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dEnd);
        while (calEnd.after(calBegin)) {
            if ("H".equals(dateType)){
                calBegin.add(Calendar.HOUR, time);
            }
            if ("M".equals(dateType)){
                calBegin.add(Calendar.MONTH, time);
            }
            if ("D".equals(dateType)){
                calBegin.add(Calendar.DATE, time);
            }
            if ("N".equals(dateType)){
                calBegin.add(Calendar.MINUTE , time);
            }
            listDate.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calBegin.getTime()));
        }
        return listDate;
    }



    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startDate = "2023-11-15".concat(" 10:01:58");
        String endDate = "2023-11-15".concat(" 10:10:59");
        Date dBegin = sdf.parse(startDate);
        Date dEnd = sdf.parse(endDate);
        List<String> list = findDatesStr("N", dBegin, dEnd,1);
        int size = list.size();
        for (int i = 0; i < size-1; i++) {
            System.out.println("开始时间"+list.get(i));
            System.out.println("开始时间"+list.get(i+1));
            System.out.println();
        }
        System.out.println("开始时间"+list.get(size-1));
        System.out.println("开始时间"+endDate);
    }

}
