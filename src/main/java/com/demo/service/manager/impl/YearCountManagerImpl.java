package com.demo.service.manager.impl;

import com.demo.service.entity.YearCountDo;
import com.demo.service.mapper.YearCountRepo;
import com.demo.service.manager.IYearCountManager;
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
public class YearCountManagerImpl extends ServiceImpl<YearCountRepo, YearCountDo> implements IYearCountManager {

}
