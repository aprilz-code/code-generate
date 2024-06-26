package com.aprilz.generate.mapper;


import com.aprilz.generate.common.mybatis.mapper.BaseMapperX;
import com.aprilz.generate.common.mybatis.query.LambdaQueryWrapperX;
import com.aprilz.generate.controller.codegen.vo.table.CodegenTablePageReqVO;
import com.aprilz.generate.entity.CodegenTableDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CodegenTableMapper extends BaseMapperX<CodegenTableDO> {

    default CodegenTableDO selectByTableNameAndDataSourceConfigId(String tableName, String dataSourceConfigId) {
        return selectOne(CodegenTableDO::getTableName, tableName,
                CodegenTableDO::getDataSourceConfigId, dataSourceConfigId);
    }

    default IPage<CodegenTableDO> selectPage(CodegenTablePageReqVO pageReqVO) {
        return selectPage(pageReqVO, new LambdaQueryWrapperX<CodegenTableDO>()
                .eqIfPresent(CodegenTableDO::getDataSourceConfigId,pageReqVO.getDataSourceConfigId())
                .likeIfPresent(CodegenTableDO::getTableName, pageReqVO.getTableName())
                .likeIfPresent(CodegenTableDO::getTableComment, pageReqVO.getTableComment())
                .betweenIfPresent(CodegenTableDO::getCreateTime, pageReqVO.getCreateTime())
                .orderByDesc(CodegenTableDO::getUpdateTime));
    }

    default List<CodegenTableDO> selectListByDataSourceConfigId(String dataSourceConfigId) {
        return selectList(CodegenTableDO::getDataSourceConfigId, dataSourceConfigId);
    }

}
