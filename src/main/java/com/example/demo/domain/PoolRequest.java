package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PoolRequest {
    @JsonProperty("poolId")
    private Long id;
    private Double percentile;
}
