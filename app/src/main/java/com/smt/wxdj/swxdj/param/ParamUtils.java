package com.smt.wxdj.swxdj.param;

import com.smt.wxdj.swxdj.utils.ConstTool;
import com.smt.wxdj.swxdj.utils.DateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParamUtils {


    /**
     * 当前作业的吊机参数
     * @param userId
     * @return
     */
    public static Map<String, Object> getCurChaneParam(String userId) {
        Map<String, Object> param = new HashMap<>();
        List<FilterEntry> list = new ArrayList<>();
        List<FilterEntry> sorts = new ArrayList<>();
        list.add(new FilterEntry("TicketType", null, "C"));
//        {"member":"TicketType","value":"C"},
        list.add(new FilterEntry("DriverId", 2, userId));
        String time =DateUtils.getTime();
        list.add(new FilterEntry("beginDate", 2,time ));
        list.add(new FilterEntry("endDate", 2, time));
        param.put("pageSize", 0);
        param.put("pageNo", 0);
        param.put("filters", list);
        param.put("sorts", sorts);
        return param;
    }

    public static Map<String, Object> getCurListStack(String CraneId) {
        Map<String, Object> param = new HashMap<>();
        List<FilterEntry> list = new ArrayList<>();
        List<FilterEntry> sorts = new ArrayList<>();
        list.add(new FilterEntry("status", null, "Y"));
//        {"member":"TicketType","value":"C"},
        list.add(new FilterEntry("CraneId", 2, CraneId));
        String time =DateUtils.getTime();
        list.add(new FilterEntry("beginDate", 0,time ));
        list.add(new FilterEntry("endDate", 5, time));
        param.put("pageSize", 0);
        param.put("pageNo", 0);
        param.put("filters", list);
        param.put("sorts", sorts);
        return param;
    }

    /**
     * 当前作业的吊机参数
     * @param cntr
     * @param YardSiteId
     * @return
     */
    public static Map<String, Object> getCurSearchCntrParam(String cntr,String YardSiteId) {
        Map<String, Object> param = new HashMap<>();
        List<FilterEntry> list = new ArrayList<>();
        List<FilterEntry> sorts = new ArrayList<>();
        list.add(new FilterEntry("YardSiteId", null, YardSiteId));
        list.add(new FilterEntry("Cntr", 8, cntr));
        param.put("pageSize", 0);
        param.put("pageNo", 0);
        param.put("filters", list);
        param.put("sorts", sorts);
        return param;
    }

}

final class FilterEntry {
    String member;
    Object operator;
    String value;

    public FilterEntry(String member, Object operator, String value) {
        this.member = member;
        this.operator = operator;
        this.value = value;
    }
}
