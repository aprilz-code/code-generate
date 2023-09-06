package com.aprilz.generate.controller.codegen.vo.table;

import com.aprilz.generate.common.api.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.aprilz.generate.utils.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;


@ApiModel("管理后台 - 表定义分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CodegenTablePageReqVO extends PageParam {

    @ApiModelProperty(value = "主键编号", example = "1024")
    private String dataSourceConfigId;

    @ApiModelProperty(value = "表名称", example = "表名称", notes = "模糊匹配")
    private String tableName;

    @ApiModelProperty(value = "表描述", example = "表描述", notes = "模糊匹配")
    private String tableComment;

    @ApiModelProperty(value = "创建时间", example = "[2022-07-01 00:00:00,2022-07-01 23:59:59]")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
