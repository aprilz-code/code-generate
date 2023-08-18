package com.aprilz.generate.service.codegen;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.aprilz.generate.config.DataSourceProperties;
import com.aprilz.generate.entity.DataSourceConfigDO;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据库表 Service 实现类
 */
@Service
public class DatabaseTableServiceImpl implements DatabaseTableService {

//    @Resource
//    private DataSourceConfigService dataSourceConfigService;

    @Resource
    private DataSourceProperties dataSourceProperties;

    @Override
    public List<TableInfo> getTableList(Long dataSourceConfigId, String nameLike, String commentLike) {
        List<TableInfo> tables = getTableList0(dataSourceConfigId, null);
        return tables.stream().filter(tableInfo -> (StrUtil.isEmpty(nameLike) || tableInfo.getName().contains(nameLike))
                        && (StrUtil.isEmpty(commentLike) || tableInfo.getComment().contains(commentLike)))
                .collect(Collectors.toList());
    }

    @Override
    public TableInfo getTable(Long dataSourceConfigId, String name) {
        return CollUtil.getFirst(getTableList0(dataSourceConfigId, name));
    }

    //真实查询数据库内表地址
    public List<TableInfo> getTableList0(Long dataSourceConfigId, String name) {
        // 获得数据源配置
        //DataSourceConfigDO config = dataSourceConfigService.getDataSourceConfig(dataSourceConfigId);
        DataSourceConfigDO config = new DataSourceConfigDO();
        config.setId(1L);
        config.setName("master");
        config.setPassword(dataSourceProperties.getPassword());
        config.setUsername(dataSourceProperties.getUsername());
        config.setUrl(dataSourceProperties.getUrl());
        // Assert.notNull(config, "数据源({}) 不存在！", dataSourceConfigId);

        // 使用 MyBatis Plus Generator 解析表结构
        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder(config.getUrl(), config.getUsername(),
                config.getPassword()).build();
        StrategyConfig.Builder strategyConfig = new StrategyConfig.Builder();
        if (StrUtil.isNotEmpty(name)) {
            strategyConfig.addInclude(name);
        }
        GlobalConfig globalConfig = new GlobalConfig.Builder().dateType(DateType.TIME_PACK).build(); //  使用 java.time 包下的 LocalDateTime java8 新的时间类型
        ConfigBuilder builder = new ConfigBuilder(null, dataSourceConfig, strategyConfig.build(),
                null, globalConfig, null);
        // 按照名字排序
        List<TableInfo> tables = builder.getTableInfoList();
        tables.sort(Comparator.comparing(TableInfo::getName));
        return tables;
    }

}
