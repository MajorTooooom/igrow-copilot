package com.dororo.future.igrowcopilot.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class MapperJavaTypeGroups {
    private DefaultDTO defaultX;

    @Data
    @NoArgsConstructor
    public static class DefaultDTO {
        private String name;
        private List<ElementListDTO> elementList;

        @Data
        @NoArgsConstructor
        public static class ElementListDTO {
            private String columnType;
            private String javaType;
        }
    }
}