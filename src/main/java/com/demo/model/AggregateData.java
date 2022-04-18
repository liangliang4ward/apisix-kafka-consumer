package com.demo.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author gaoll
 * @time 2022/4/11 11:21
 **/
@Data
@ToString
public class AggregateData {

    private String key;

    private String serviceId;

    private String username;

    private Date  dataTime;

    private Long count;

    private Long successCount;

    private Long about4xxCount;

    private Long about5xxCount;
}
