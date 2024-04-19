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
public enum RenderModeEnum {
    STRING_MODE(1, "字符串"),
    ZIP_MODE(2, "打包ZIP"),
    DIR_MODE(3, "渲染到配置项目目录"),
    ;
    public final Integer code;
    public final String name;

    public static List<Integer> codeList() {
        return Arrays.stream(RenderModeEnum.values()).map(RenderModeEnum::getCode).collect(Collectors.toList());
    }

    public static RenderModeEnum getByCode(Integer val) {
        Assert.notNull(val, "数值不能为空");
        Assert.isTrue(codeList().contains(val), "数值[{}]不存在", val);
        for (RenderModeEnum anEnum : RenderModeEnum.values()) {
            if (anEnum.getCode().equals(val)) {
                return anEnum;
            }
        }
        return null;
    }


    public static RenderModeEnum getByName(String name) {
        Assert.notNull(name, "名称不能为空");
        for (RenderModeEnum anEnum : RenderModeEnum.values()) {
            if (anEnum.getName().equals(name)) {
                return anEnum;
            }
        }
        return null;
    }

    public static String tips() {
        String tip = Arrays.stream(RenderModeEnum.values())
                .map(anEnum -> StrUtil.format("{}={}", anEnum.getCode(), anEnum.getName()))
                .collect(Collectors.joining(",", "[", "]"));
        return tip;
    }


    public static List<JSONObject> toOptions() {
        List<JSONObject> options = Arrays.stream(RenderModeEnum.values())
                .map(anEnum -> JSONUtil.createObj().putOpt("value", anEnum.code).putOpt("label", anEnum.name))
                .collect(Collectors.toList());
        return options;
    }
}
