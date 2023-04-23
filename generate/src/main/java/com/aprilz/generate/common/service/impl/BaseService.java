package com.aprilz.generate.common.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.aprilz.generate.common.BaseEntity;
import com.aprilz.generate.common.mybatis.mapper.BaseMapperX;
import com.aprilz.generate.common.service.IBaseService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 基础服务实现
 *
 * @author Xjr
 * @version 1.0
 * @date 2023-03-24 16:46
 */
public abstract class BaseService<M extends BaseMapperX<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements IBaseService<T> {

    /**
     * 批量新增一次性最大条数
     * <p>
     * 查看以下MySql配置判断最大条数优先级
     * SHOW VARIABLES LIKE '%max_allowed_packet%';
     * SHOW VARIABLES LIKE '%net_buffer_length%';
     * SHOW VARIABLES LIKE '%innodb_buffer_pool_size%';
     * SHOW VARIABLES LIKE '%innodb_log_buffer_size%';
     * </p>
     */
    private static final int MAX_BATCH_SIZE = 3000;

    /**
     * 重写MyBatisPlus批量新增方法
     *
     * @param entityList 对象集合
     * @return true=成功,false=失败
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveBatch(Collection<T> entityList) {
        this.saveBatch(entityList, MAX_BATCH_SIZE);
        return true;
    }

    /**
     * 重写MyBatisPlus批量新增方法
     *
     * @param entityList 对象集合
     * @param batchSize  true=成功,false=失败
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        if (CollectionUtil.isEmpty(entityList)) {
            // 为空也返回true
            return true;
        }

        // 数据填充
    //    FillUtil.preInsertBatch(entityList);

        // 每片数
        int burstSize = batchSize > MAX_BATCH_SIZE || batchSize <= 0 ? MAX_BATCH_SIZE : batchSize;

        // 分片数
        int limit = (entityList.size() + burstSize - 1) / burstSize;

        // 批量拆分
        Stream.iterate(0, n -> n + 1).limit(limit).forEach(i -> {
            List<T> list = entityList.stream().skip(i * burstSize).limit(burstSize).collect(Collectors.toList());
            baseMapper.insertBatchSomeColumn(list);
        });
        return true;
    }

    @Override
    public T getOne(Wrapper<T> queryWrapper) {
        return this.getOne(queryWrapper, true);
    }

    @Override
    public T getOne(Wrapper<T> queryWrapper, boolean throwEx) {
        if (queryWrapper instanceof LambdaQueryWrapper) {
            ((LambdaQueryWrapper<T>) queryWrapper).last("LIMIT 1");
        }
        return super.getOne(queryWrapper, throwEx);
    }
}
