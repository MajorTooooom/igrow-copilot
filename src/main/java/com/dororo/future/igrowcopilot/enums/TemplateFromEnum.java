package com.dororo.future.igrowcopilot.enums;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum TemplateFromEnum {
    BUILT_IN(0, "内置"),
    UPLOAD(1, "上传"),
    ;
    public final Integer code;
    public final String name;

    public static List<Integer> codeList() {
        return Arrays.stream(TemplateFromEnum.values()).map(TemplateFromEnum::getCode).collect(Collectors.toList());
    }

    public static TemplateFromEnum getByCode(Integer val) {
        Assert.notNull(val, "数值不能为空");
        Assert.isTrue(codeList().contains(val), "数值[{}]不存在", val);
        for (TemplateFromEnum anEnum : TemplateFromEnum.values()) {
            if (anEnum.getCode().equals(val)) {
                return anEnum;
            }
        }
        return null;
    }


    public static TemplateFromEnum getByName(String name) {
        Assert.notNull(name, "名称不能为空");
        for (TemplateFromEnum anEnum : TemplateFromEnum.values()) {
            if (anEnum.getName().equals(name)) {
                return anEnum;
            }
        }
        return null;
    }

    public static String tips() {
        String tip = Arrays.stream(TemplateFromEnum.values())
                .map(anEnum -> StrUtil.format("{}={}", anEnum.getCode(), anEnum.getName()))
                .collect(Collectors.joining(",", "[", "]"));
        return tip;
    }


    public static List<JSONObject> toOptions() {
        List<JSONObject> options = Arrays.stream(TemplateFromEnum.values())
                .map(anEnum -> JSONUtil.createObj().putOpt("value", anEnum.code).putOpt("label", anEnum.name))
                .collect(Collectors.toList());
        return options;
    }
}
