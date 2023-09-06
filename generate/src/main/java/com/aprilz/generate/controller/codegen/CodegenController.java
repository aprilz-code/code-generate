package com.aprilz.generate.controller.codegen;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ZipUtil;
import com.aprilz.generate.common.api.CommonResult;
import com.aprilz.generate.controller.codegen.vo.CodegenCreateListReqVO;
import com.aprilz.generate.controller.codegen.vo.CodegenDetailRespVO;
import com.aprilz.generate.controller.codegen.vo.CodegenPreviewRespVO;
import com.aprilz.generate.controller.codegen.vo.CodegenUpdateReqVO;
import com.aprilz.generate.controller.codegen.vo.table.CodegenTablePageReqVO;
import com.aprilz.generate.controller.codegen.vo.table.CodegenTableRespVO;
import com.aprilz.generate.controller.codegen.vo.table.DatabaseTableRespVO;
import com.aprilz.generate.entity.CodegenColumnDO;
import com.aprilz.generate.entity.CodegenTableDO;
import com.aprilz.generate.entity.DataSourceConfigDO;
import com.aprilz.generate.mapper.codegen.CodegenConvert;
import com.aprilz.generate.service.codegen.CodegenService;
import com.aprilz.generate.utils.ServletUtils;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.aprilz.generate.common.api.CommonResult.success;


@Api(tags = "管理后台 - 代码生成器")
@RestController
@RequestMapping("/codegen")
@Validated
public class CodegenController {

    @Resource
    private CodegenService codegenService;

    @Resource
    private DynamicDataSourceProperties dynamicDataSourceProperties;


    @GetMapping("/getDataSourceList")
    @ApiOperation("获得数据源配置列表,单数据源返回它本身")
    public CommonResult<List<DataSourceConfigDO>> getDataSourceList() {
        List<DataSourceConfigDO> list = new ArrayList<>();
        Map<String, DataSourceProperty> datasource = dynamicDataSourceProperties.getDatasource();
        datasource.keySet().forEach(data -> {
            DataSourceConfigDO config = new DataSourceConfigDO();
            config.setId(data);
            config.setName(data);
            list.add(config);
        });
        return success(list);
    }

    @GetMapping("/db/table/list")
    @ApiOperation(value = "获得数据库自带的表定义列表", notes = "会过滤掉已经导入 Codegen 的表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "表名，模糊匹配", example = "表名", dataTypeClass = String.class),
            @ApiImplicitParam(name = "comment", value = "描述，模糊匹配", example = "描述", dataTypeClass = String.class)
    })
    public CommonResult<List<DatabaseTableRespVO>> getDatabaseTableList(
            @RequestParam(value = "dataSourceConfigId") String dataSourceConfigId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "comment", required = false) String comment) {
        return success(codegenService.getDatabaseTableList(dataSourceConfigId, name, comment));
    }

    @GetMapping("/table/page")
    @ApiOperation("获得表定义分页")
    public CommonResult<IPage<CodegenTableRespVO>> getCodeGenTablePage(@Valid CodegenTablePageReqVO pageReqVO) {
        IPage<CodegenTableDO> pageResult = codegenService.getCodegenTablePage(pageReqVO);
        return success(CodegenConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/detail")
    @ApiOperation("获得表和字段的明细")
    @ApiImplicitParam(name = "tableId", value = "表编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<CodegenDetailRespVO> getCodegenDetail(@RequestParam("tableId") Long tableId) {
        CodegenTableDO table = codegenService.getCodegenTablePage(tableId);
        List<CodegenColumnDO> columns = codegenService.getCodegenColumnListByTableId(tableId);
        // 拼装返回
        return success(CodegenConvert.INSTANCE.convert(table, columns));
    }

    @ApiOperation("基于数据库的表结构，创建代码生成器的表和字段定义")
    @PostMapping("/create-list")
    public CommonResult<List<Long>> createCodegenList(@Valid @RequestBody CodegenCreateListReqVO reqVO) {
        return success(codegenService.createCodegenList(1L, reqVO));
    }

    @ApiOperation("更新数据库的表和字段定义")
    @PutMapping("/update")
    public CommonResult<Boolean> updateCodegen(@Valid @RequestBody CodegenUpdateReqVO updateReqVO) {
        codegenService.updateCodegen(updateReqVO);
        return success(true);
    }

    @ApiOperation("基于数据库的表结构，同步数据库的表和字段定义")
    @PutMapping("/sync-from-db")
    @ApiImplicitParam(name = "tableId", value = "表编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<Boolean> syncCodegenFromDB(@RequestParam("tableId") Long tableId) {
        codegenService.syncCodegenFromDB(tableId);
        return success(true);
    }

    @ApiOperation("删除数据库的表和字段定义")
    @DeleteMapping("/delete")
    @ApiImplicitParam(name = "tableId", value = "表编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<Boolean> deleteCodegen(@RequestParam("tableId") Long tableId) {
        codegenService.deleteCodegen(tableId);
        return success(true);
    }

    @ApiOperation("预览生成代码")
    @GetMapping("/preview")
    @ApiImplicitParam(name = "tableId", value = "表编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<List<CodegenPreviewRespVO>> previewCodegen(@RequestParam("tableId") Long tableId) {
        Map<String, String> codes = codegenService.generationCodes(tableId);
        return success(CodegenConvert.INSTANCE.convert(codes));
    }

    @ApiOperation("下载生成代码")
    @GetMapping("/download")
    @ApiImplicitParam(name = "tableId", value = "表编号", required = true, example = "1024", dataTypeClass = Long.class)
    public void downloadCodegen(@RequestParam("tableId") Long tableId,
                                HttpServletResponse response) throws IOException {
        // 生成代码
        Map<String, String> codes = codegenService.generationCodes(tableId);
        // 构建 zip 包
        String[] paths = codes.keySet().toArray(new String[0]);
        ByteArrayInputStream[] ins = codes.values().stream().map(IoUtil::toUtf8Stream).toArray(ByteArrayInputStream[]::new);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipUtil.zip(outputStream, paths, ins);
        // 输出
        ServletUtils.writeAttachment(response, "codegen.zip", outputStream.toByteArray());
    }

//    @GetMapping("/dicList")
//    @ApiOperation(value = "获得全部字典类型列表", notes = "包括开启 + 禁用的字典类型，主要用于前端的下拉选项")
//    // 无需添加权限认证，因为前端全局都需要
//    public CommonResult<List<DictTypeSimpleRespVO>> listSimpleDictTypes() {
//        List<DictTypeDO> list = dictTypeService.getDictTypeList();
//        return success(DictTypeConvert.INSTANCE.convertList(list));
//    }

}
