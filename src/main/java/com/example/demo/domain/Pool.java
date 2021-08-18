package com.example.demo.domain;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pool {
    @JsonProperty("poolId")
    private Long id;
    @JsonProperty("poolValues")
    private List<Long> values;
}
