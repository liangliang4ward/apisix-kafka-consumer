package com.demo.model;

import lombok.Data;

/**
 * @author gaoll
 * @time 2022/4/11 11:19
 **/
@Data
public class AccessLog {

    /**
     * 调用方
     */
    private String username;

    /**
     * 时间
     */
    private long startTime;

    /**
     * 路由id
     */
    private String routeId;

    /**
     * 服务id
     */
    private String serviceId;

    /**
     * 返回时间
     */
    private String responseStatus;
}
