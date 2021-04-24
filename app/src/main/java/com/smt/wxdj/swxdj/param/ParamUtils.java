package com.smt.wxdj.swxdj.param;

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
//        list.add(new FilterEntry("TicketType", null, "C"));
        //{"member":"TicketType","value":"C"},
        list.add(new FilterEntry("DriverId", 2, userId));
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
