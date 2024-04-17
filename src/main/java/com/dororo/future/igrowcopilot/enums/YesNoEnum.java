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
public enum YesNoEnum {
    NO(0, "NO"),
    YES(1, "YES"),
    ;
    public final Integer value;
    public final String name;

    public static List<Integer> codeList() {
        return Arrays.stream(YesNoEnum.values()).map(YesNoEnum::getValue).collect(Collectors.toList());
    }

    public static YesNoEnum getByValue(Integer val) {
        Assert.notNull(val, "数值不能为空");
        Assert.isTrue(codeList().contains(val), "数值[{}]不存在", val);
        for (YesNoEnum anEnum : YesNoEnum.values()) {
            if (anEnum.getValue().equals(val)) {
                return anEnum;
            }
        }
        return null;
    }


    public static YesNoEnum getByName(String name) {
        Assert.notNull(name, "名称不能为空");
        for (YesNoEnum anEnum : YesNoEnum.values()) {
            if (anEnum.getName().equals(name)) {
                return anEnum;
            }
        }
        return null;
    }

    public static String tips() {
        String tip = Arrays.stream(YesNoEnum.values())
                .map(anEnum -> StrUtil.format("{}={}", anEnum.getValue(), anEnum.getName()))
                .collect(Collectors.joining(",", "[", "]"));
        return tip;
    }


    public static List<JSONObject> toOptions() {
        List<JSONObject> options = Arrays.stream(YesNoEnum.values())
                .map(anEnum -> JSONUtil.createObj().putOpt("value", anEnum.value).putOpt("label", anEnum.name))
                .collect(Collectors.toList());
        return options;
    }
}
