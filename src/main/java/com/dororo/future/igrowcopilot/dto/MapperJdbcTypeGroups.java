package com.dororo.future.igrowcopilot.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class MapperJdbcTypeGroups {

    private JdbcTypeMappingDTO jdbcTypeMapping;

    @NoArgsConstructor
    @Data
    public static class JdbcTypeMappingDTO {
        private String name;
        private List<ElementListDTO> elementList;

        @NoArgsConstructor
        @Data
        public static class ElementListDTO {
            private String columnType;
            private String jdbcType;
        }
    }
}
