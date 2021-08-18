package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import com.example.demo.domain.Pool;
import com.example.demo.domain.PoolRequest;
import com.example.demo.domain.Quantile;

@RestController
@RequestMapping("/v1/demo")
public class DemoController {

    private final String APPENDED = "appended";
    private final String INSERTED = "inserted";
    HashMap<Long, List<Long>> pools = new HashMap<>();

    @PostMapping
    public ResponseEntity<String> append(@RequestBody Pool pool) {
        String result = APPENDED;
        Long poolId = pool.getId();
        if (!pools.containsKey(poolId)) {
            pools.put(poolId, pool.getValues());
            result = INSERTED;
        } else {
            pools.get(poolId).addAll(pool.getValues());
        }
        Collections.sort(pools.get(poolId));

        return new ResponseEntity<String>( result, HttpStatus.ACCEPTED );
    }

    @PostMapping("/quantile")
    public ResponseEntity<Quantile> getQuantile(@RequestBody PoolRequest request) {
        Double percentile = request.getPercentile();

        List<Long> values = pools.get(request.getId());
        Quantile result = new Quantile();


        if (Objects.isNull(values)) {
            return new ResponseEntity<Quantile>(HttpStatus.NOT_FOUND);
        }

        int numberElements = values.size();
        int index = (int) Math.floor(percentile / 100 * (numberElements + 1));
        index = Math.min(numberElements - 1, Math.max(index - 1, 0));
        result.setNumberElement(numberElements);
        result.setValue(values.get(index));

        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }


}
