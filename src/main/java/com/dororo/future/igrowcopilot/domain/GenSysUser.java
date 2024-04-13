package com.dororo.future.igrowcopilot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenSysUser {
    private Integer id;

    private String name;

    private String password;

    private String isDeleted;

    private String createTime;

    private String updateTime;
}