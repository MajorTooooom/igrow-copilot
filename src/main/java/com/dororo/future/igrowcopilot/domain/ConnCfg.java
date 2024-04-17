package com.dororo.future.igrowcopilot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConnCfg {
    @NotNull(groups = {Delete.class, Update.class}, message = "id不能为空")
    private Integer id;

    private Integer userId;

    @NotBlank(groups = {Add.class}, message = "配置名称不能为空")
    private String cfgName;

    @NotBlank(groups = {Add.class, TableOptions.class}, message = "url不能为空")
    private String url;

    @NotBlank(groups = {Add.class, TableOptions.class}, message = "连接用户名不能为空")
    private String userName;

    @NotBlank(groups = {Add.class, TableOptions.class}, message = "连接密码不能为空")
    private String password;

    private String isDeleted;

    private String createTime;

    private String updateTime;


    public interface Add {
    }

    public interface Delete {
    }

    public interface Update {
    }

    public interface TableOptions {
    }
}