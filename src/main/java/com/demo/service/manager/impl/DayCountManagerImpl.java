package com.demo.service.manager.impl;

import com.demo.service.entity.DayCountDo;
import com.demo.service.mapper.DayCountRepo;
import com.demo.service.manager.IDayCountManager;
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
public class DayCountManagerImpl extends ServiceImpl<DayCountRepo, DayCountDo> implements IDayCountManager {

}
