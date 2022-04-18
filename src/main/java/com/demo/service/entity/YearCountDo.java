package com.demo.service.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author auto
 * @since 2022-04-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("year_count")
public class YearCountDo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;

    private Long count;

    private Long successCount;

    @TableField("about_5xx_count")
    private Long about5xxCount;

    @TableField("about_4xx_count")
    private Long about4xxCount;

    private String serviceId;

    private String username;

    private Date countDate;

    private Date createTime;

    private Date updateTime;


}
