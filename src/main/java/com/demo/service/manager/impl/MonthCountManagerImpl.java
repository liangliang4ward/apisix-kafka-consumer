package com.demo.service.manager.impl;

import com.demo.service.entity.MonthCountDo;
import com.demo.service.mapper.MonthCountRepo;
import com.demo.service.manager.IMonthCountManager;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author auto
 * @since 2022-04-11
 */
@Service
public class MonthCountManagerImpl extends ServiceImpl<MonthCountRepo, MonthCountDo> implements IMonthCountManager {

}
