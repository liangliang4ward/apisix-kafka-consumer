package com.demo.service.manager.impl;

import com.demo.service.entity.FiveminuteCountDo;
import com.demo.service.mapper.FiveminuteCountRepo;
import com.demo.service.manager.IFiveminuteCountManager;
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
public class FiveminuteCountManagerImpl extends ServiceImpl<FiveminuteCountRepo, FiveminuteCountDo> implements IFiveminuteCountManager {

}
