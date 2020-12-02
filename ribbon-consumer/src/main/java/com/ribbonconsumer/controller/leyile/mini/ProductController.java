package com.ribbonconsumer.controller.leyile.mini;

import com.core.base.controller.BaseController;
import com.core.base.exception.QianException;
import com.core.base.util.ModelUtil;
import com.core.base.util.StrUtil;
import com.ribbonconsumer.config.swagger.DocVer;
import com.ribbonconsumer.service.leyile.ProductService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = {DocVer.Z9.MAUSER.USER.KEY})
@RestController
@RequestMapping("/product")
public class ProductController extends BaseController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @ApiOperation(value = "查看评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", defaultValue = "1", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "contentId", value = "被点赞作品id", defaultValue = "20", dataType = "String"),
    })
    @PostMapping("/getEvaluationList")
    public Object contentId(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long contentId = ModelUtil.getLong(params, "contentId");
        long userid = ModelUtil.getLong(params, "userId");
        if (contentId == 0) {
            return toError("contentId为空");
        }
        return toJsonOk(productService.getEvaluationList(contentId, userid));
    }

    @ApiOperation(value = "发布作品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id",  dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "content", value = "内容", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "title", value = "标题", dataType = "String"),
    })
    @PostMapping("/focusUser")
    public Object focusUser(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long userid = ModelUtil.getLong(params, "userId");
        long meterialId = ModelUtil.getLong(params, "meterialId");
        String content = ModelUtil.getStr(params, "content");
        String title = ModelUtil.getStr(params, "title");
        if (userid == 0 || StrUtil.isEmpty(content, title)) {
            return toError("参数错误");
        }
        productService.insertContent(userid, title, content, meterialId);
        return toJsonOk("success");
    }

    @ApiOperation(value = "首页推荐作品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id",  dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "pageIndex", value = "页长", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页码", dataType = "String"),
    })
    @GetMapping("/getContentList")
    public Object getContentList(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        long userid = ModelUtil.getLong(params, "userId");
        int pageIndex = ModelUtil.getInt(params, "pageIndex", 1);
        int pageSize = ModelUtil.getInt(params, "pageSize", 20);
        result.put("data", productService.getContentList(userid, pageSize, pageIndex));
        return toJsonOk(result);
    }


    @ApiOperation(value = "添加用户查看足迹")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id",  dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "productId", value = "产品id", dataType = "String"),
    })
    @PostMapping("/insertFootprint")
    public Object insertFootprint(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long userid = ModelUtil.getLong(params, "userId");
        long productId = ModelUtil.getLong(params, "productId");
        if (userid == 0) {
            return toError("参数错误");
        }
        productService.insertFootprint(userid, productId);
        return toJsonOk("success");
    }


    @ApiOperation(value = "所有分类列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id",  dataType = "String"),
    })
    @GetMapping("/getClassifyList")
    public Object getClassifyList(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        long userid = ModelUtil.getLong(params, "userId");
        result.put("data", productService.getClassifyList(userid));
        return toJsonOk(result);
    }

    @ApiOperation(value = "我关注的分类")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id",  dataType = "String"),
    })
    @GetMapping("/getFocusList")
    public Object getFocusList(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        long userid = ModelUtil.getLong(params, "userId");
        if (userid == 0) {
            return toError("参数错误");
        }
        result.put("data", productService.getFocusList(userid));
        return toJsonOk(result);
    }

}
