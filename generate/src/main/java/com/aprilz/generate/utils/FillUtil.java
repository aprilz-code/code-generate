//package com.aprilz.generate.utils;
//
//
//
//
//import com.aprilz.generate.common.BaseEntity;
//
//import java.time.LocalDateTime;
//import java.util.Collection;
//import java.util.Objects;
//
///**
// * 数据填充
// *
// * @author Xjr
// * @version 1.0
// * @date 2023-03-24 15:21
// */
//public class FillUtil {
//
//    /**
//     * 插入之前执行方法，需要手动调用
//     *
//     * @param entity 对象
//     * @param <T>    泛型
//     */
//    public static <T extends BaseEntity> void preInsert(T entity) {
//        // 操作人
//        String userId = Objects.isNull(entity.getCreateBy()) ? "admin" : entity.getCreateBy();
//        // 时间
//        LocalDateTime localDateTime = Objects.isNull(entity.getCreateTime()) ? LocalDateTime.now() : entity.getCreateTime();
//        entity.setCreateTime(localDateTime);
//        entity.setUpdateTime(localDateTime);
//        entity.setCreateBy(userId);
//        entity.setUpdateBy(userId);
//        entity.setDelFlag(Boolean.FALSE);
//    }
//
//    /**
//     * 插入之前执行方法，需要手动调用
//     *
//     * @param entityList 对象集合
//     * @param <T>        泛型
//     */
//    public static <T extends BaseEntity> void preInsertBatch(Collection<T> entityList) {
//        // 操作人
//        // 时间
//        LocalDateTime localDateTime = LocalDateTime.now();
//        entityList.forEach(entity -> {
//            entity.setCreateTime(localDateTime);
//            entity.setUpdateTime(localDateTime);
//            entity.setCreateBy("admin");
//            entity.setUpdateBy("admin");
//            entity.setDelFlag(Boolean.FALSE);
//        });
//    }
//
//    /**
//     * 更新之前执行方法，需要手动调用
//     *
//     * @param entity 对象
//     * @param <T>    泛型
//     */
//    public static <T extends BaseEntity> void preUpdate(T entity) {
//        // 时间
//        LocalDateTime localDateTime = Objects.isNull(entity.getUpdateTime()) ? LocalDateTime.now() : entity.getUpdateTime();
//        // 操作人
//        String userId = Objects.isNull(entity.getUpdateBy()) ? "admin" : entity.getUpdateBy();
//        entity.setUpdateTime(localDateTime);
//        entity.setUpdateBy(userId);
//    }
//
//    /**
//     * 新增或更新之前执行方法，需要手动调用
//     *
//     * @param entity 对象
//     * @param <T>    泛型
//     */
//    public static <T extends BaseEntity> void preInsertOrUpdate(T entity) {
//        // 时间
//        LocalDateTime localDateTime = Objects.isNull(entity.getUpdateTime()) ? LocalDateTime.now() : entity.getUpdateTime();
//        // 操作人
//        Long userId = Objects.isNull(entity.getUpdateBy()) ? 1L : entity.getUpdateBy();
//        // 新增数据填充
//        if (Objects.isNull(entity.getId()) || entity.getId().longValue() <= 0L) {
//            entity.setId(null);
//            entity.setCreateTime(localDateTime);
//            entity.setCreateBy(userId);
//            entity.setDelFlag(0);
//        }
//        entity.setUpdateBy(userId);
//        entity.setUpdateTime(localDateTime);
//    }
//}
