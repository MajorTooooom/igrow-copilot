package com.zhien.common.core.domain;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zhien.common.core.lamda.InformNextAction;
import com.zhien.common.utils.DateUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class R extends HashMap<String, Object> {
    //
    private static final long serialVersionUID = -8157613083634272196L;

    public R() {
        put("code", 0);
        put("msg", "success");
    }

    public static R error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(500, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R data(Object obj) {
        R r = new R();
        r.put("data", obj);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    public static R page(long total, int pageSize, List list) {
        R r = new R();
        r.put("code", 0);
        r.put("msg", "success");
        r.put("total", total);
        r.put("pageSize", pageSize);
        r.put("rows", JSONArray.parseArray(JSONObject.toJSONStringWithDateFormat(list, DateUtils.YYYY_MM_DD_HH_MM_SS)));
        return r;
    }

    public static R page(long total, int pageSize, int pageNum, List list) {
        R r = new R();
        Long totalPage = total % pageSize == 0 ? total / pageSize : (total / pageSize) + 1;
        r.put("code", 0);
        r.put("msg", "success");
        r.put("total", total);
        r.put("totalPage", totalPage);
        r.put("pageSize", pageSize);
        r.put("pageNum", pageNum);
        r.put("rows", JSONArray.parseArray(JSONObject.toJSONStringWithDateFormat(list, DateUtils.YYYY_MM_DD_HH_MM_SS)));
        return r;
    }

    public static R page(long total, int pageSize, JSONArray array) {
        R r = new R();
        r.put("code", 0);
        r.put("msg", "success");
        r.put("total", total);
        r.put("pageSize", pageSize);
        r.put("rows", array);
        return r;
    }

    public static R page(List<?> list) {
        PageInfo<?> pageInfo = new PageInfo(list);
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("rows", list);
        m.put("pageNum", pageInfo.getPageNum());
        m.put("pageSize", pageInfo.getPageSize());
        m.put("total", pageInfo.getTotal());
        m.put("totalPage", pageInfo.getTotal());
        if (pageInfo.getTotal() != 0) {
            Long totalPage = pageInfo.getTotal() % pageInfo.getPageSize() == 0 ?
                    pageInfo.getTotal() / pageInfo.getPageSize()
                    : (pageInfo.getTotal() / pageInfo.getPageSize()) + 1;
            m.put("totalPage", totalPage);
        } else {
            m.put("totalPage", 0);
        }
        return R.ok(m);
    }

    public R then(InformNextAction func) {
        if ((Integer) this.get("code") == 0) {
            return func.action();
        } else {
            return this;
        }
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}