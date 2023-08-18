package com.aprilz.generate.mapper.codegen;


import com.aprilz.generate.controller.codegen.vo.CodegenDetailRespVO;
import com.aprilz.generate.controller.codegen.vo.CodegenPreviewRespVO;
import com.aprilz.generate.controller.codegen.vo.CodegenUpdateReqVO;
import com.aprilz.generate.controller.codegen.vo.column.CodegenColumnRespVO;
import com.aprilz.generate.controller.codegen.vo.table.CodegenTableRespVO;
import com.aprilz.generate.controller.codegen.vo.table.DatabaseTableRespVO;
import com.aprilz.generate.entity.CodegenColumnDO;
import com.aprilz.generate.entity.CodegenTableDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper
public interface CodegenConvert {

    CodegenConvert INSTANCE = Mappers.getMapper(CodegenConvert.class);

    // ========== TableInfo 相关 ==========

    @Mappings({
            @Mapping(source = "name", target = "tableName"),
            @Mapping(source = "comment", target = "tableComment"),
    })
    CodegenTableDO convert(TableInfo bean);

    List<CodegenColumnDO> convertList(List<TableField> list);

    @Mappings({
            @Mapping(source = "name", target = "columnName"),
            @Mapping(source = "type", target = "dataType"),
            @Mapping(source = "comment", target = "columnComment"),
            @Mapping(source = "metaInfo.nullable", target = "nullable"),
            @Mapping(source = "keyFlag", target = "primaryKey"),
            @Mapping(source = "keyIdentityFlag", target = "autoIncrement"),
            @Mapping(source = "columnType.type", target = "javaType"),
            @Mapping(source = "propertyName", target = "javaField"),
    })
    CodegenColumnDO convert(TableField bean);

    // ========== CodegenTableDO 相关 ==========

//    List<CodegenTableRespVO> convertList02(List<CodegenTableDO> list);

    CodegenTableRespVO convert(CodegenTableDO bean);

    default IPage<CodegenTableRespVO> convertPage(IPage<CodegenTableDO> page) {
        return page.convert(this::convert);
    }

    ;

    // ========== CodegenTableDO 相关 ==========

    List<CodegenColumnRespVO> convertList02(List<CodegenColumnDO> list);

    CodegenTableDO convert(CodegenUpdateReqVO.Table bean);

    List<CodegenColumnDO> convertList03(List<CodegenUpdateReqVO.Column> columns);

    List<DatabaseTableRespVO> convertList04(List<TableInfo> list);

    // ========== 其它 ==========

    default CodegenDetailRespVO convert(CodegenTableDO table, List<CodegenColumnDO> columns) {
        CodegenDetailRespVO respVO = new CodegenDetailRespVO();
        respVO.setTable(convert(table));
        respVO.setColumns(convertList02(columns));
        return respVO;
    }

    default List<CodegenPreviewRespVO> convert(Map<String, String> codes) {
        return codes.entrySet().stream().map(entry -> {
            CodegenPreviewRespVO respVO = new CodegenPreviewRespVO();
            respVO.setFilePath(entry.getKey());
            respVO.setCode(entry.getValue());
            return respVO;
        }).collect(Collectors.toList());
    }

}
