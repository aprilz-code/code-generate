package com.aprilz.generate.controller.codegen.vo;


import com.aprilz.generate.controller.codegen.vo.column.CodegenColumnRespVO;
import com.aprilz.generate.controller.codegen.vo.table.CodegenTableRespVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("管理后台 - 代码生成表和字段的明细 Response VO")
@Data
public class CodegenDetailRespVO {

    @ApiModelProperty("表定义")
    private CodegenTableRespVO table;

    @ApiModelProperty("字段定义")
    private List<CodegenColumnRespVO> columns;

}
