package com.demo.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;


public class MyBatisPlusGenerator {
    static String[] tableNames = {
//            "com_api_mon_stat_perday","com_api_mon_stat_timer","com_mon_stat_perday",
//            "com_mon_stat_timer","com_url_mon_stat_perday","com_url_mon_stat_timer"
//            "csss_app_com_apply"
//            "application_grant_info","flow_info","use_apply_info"
            "day_count","fiveminute_count","month_count","year_count"
    };


    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(System.getProperty("user.dir") + "/src/main/java");
        System.out.println(System.getProperty("user.dir"));
        globalConfig.setAuthor("auto");
        globalConfig.setFileOverride(true); //是否覆盖
        globalConfig.setActiveRecord(false);// 不需要ActiveRecord特性的请改为false
        globalConfig.setOpen(false);
        globalConfig.setEnableCache(false);// XML 二级缓存
        globalConfig.setBaseResultMap(true);// XML ResultMap
        globalConfig.setBaseColumnList(true);// XML columList
        globalConfig.setEntityName("%sDo");
        globalConfig.setServiceName("I%sManager");
        globalConfig.setMapperName("%sRepo");
        globalConfig.setServiceImplName("%sManagerImpl");
        globalConfig.setDateType(DateType.ONLY_DATE);
        mpg.setGlobalConfig(globalConfig);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("username");
        dsc.setPassword("password");
        dsc.setUrl("jdbc:mysql://xx.xx.xx.xx:[port]/[db]?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(tableNames); // 需要生成的表
        strategy.setTablePrefix("t_");
        strategy.setEntityLombokModel(true);
        // 自定义实体父类
        // 自定义实体，公共字段
        strategy.setChainModel(true);
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.demo.service");
        packageConfig.setEntity("entity");
        packageConfig.setMapper("mapper");
        packageConfig.setService("manager");
        packageConfig.setServiceImpl("manager.impl");
        packageConfig.setXml("mapper.xml");

        mpg.setPackageInfo(packageConfig);

        // 关闭默认 xml 生成，调整生成 至 根目录
        TemplateConfig tc = new TemplateConfig();
//        tc.setController("");
//        tc.setMapper("");
//        tc.setService("");
//        tc.setServiceImpl("");
//        tc.setXml("");
        mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();
    }
}
