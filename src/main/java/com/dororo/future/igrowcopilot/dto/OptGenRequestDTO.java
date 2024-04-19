package com.dororo.future.igrowcopilot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptGenRequestDTO {
    private Integer userId;
    private Integer tableCfgId;
    private Integer genCfgId;
    private String mode;
}
