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
            @ApiImplicitParam(paramType = "query", name = "contentId", value = "作品id", defaultValue = "20", dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "[\n" +
                    "    {\n" +
                    "        \"evaluationContent｜评价内容\":\"用户第一个评价\",\n" +
                    "        \"id\":1,\n" +
                    "        \"name|用户id\":\"王大帅迁\",\n" +
                    "        \"headpic｜头像\":\"temp-111.jpg\",\n" +
                    "        \"createTime\":null,\n" +
                    "        \"pid\":null,\n" +
                    "        \"num｜点赞数量\":0,\n" +
                    "        \"childList｜子评论\":null\n" +
                    "    }\n" +
                    "]")
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
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "content", value = "内容", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "picture", value = "图片", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "title", value = "标题", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "classId", value = "分类id", dataType = "String"),
    })
    @PostMapping("/insertContent")
    public Object focusUser(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long userid = ModelUtil.getLong(params, "userId");
        String picture = ModelUtil.getStr(params, "picture");
        String content = ModelUtil.getStr(params, "content");
        String title = ModelUtil.getStr(params, "title");
        long classId = ModelUtil.getLong(params, "classId");
        if (userid == 0 || classId == 0 || StrUtil.isEmpty(content, title)) {
            return toError("参数错误");
        }
        productService.insertContent(userid, title, content, picture, classId);
        return toJsonOk("success");
    }

    @ApiOperation(value = "首页推荐作品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "pageIndex", value = "页长", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页码", dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "{\n" +
                    "    \"total\":2,\n" +
                    "    \"pageIndex\":1,\n" +
                    "    \"list\":[\n" +
                    "        {\n" +
                    "            \"time|创建时间\":null,\n" +
                    "            \"id\":1,\n" +
                    "            \"content｜正文\":\"1\",\n" +
                    "            \"evaluationNum｜评价数量\":0,\n" +
                    "            \"praiseNum｜点赞数\":0,\n" +
                    "            \"forwardingNum｜转发数\":0,\n" +
                    "            \"meterialUrl\":\"http://localhost:8887/pears/file/readFile/null\",\n" +
                    "            \"name｜用户名\":\"王大帅迁\",\n" +
                    "            \"headpic｜用户头像\":\"http://localhost:8887/pears/file/readFile/temp-111.jpg\",\n" +
                    "            \"musicUrl\":\"https://www.nopears.com/music/null\",\n" +
                    "            \"isFriend｜是否关注\":0,\n" +
                    "            \"title｜标题\":null,\n" +
                    "            \"picture｜图片\":null,\n" +
                    "            \"isPraise｜是否点赞\":0,\n" +
                    "            \"introduce｜介绍\":\"1\"\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}")
    })
    @GetMapping("/getContentList")
    public Object getContentList(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long userid = ModelUtil.getLong(params, "userId");
        int pageIndex = ModelUtil.getInt(params, "pageIndex", 1);
        int pageSize = ModelUtil.getInt(params, "pageSize", 20);
        return toJsonOk(productService.getContentList(userid, pageSize, pageIndex));
    }


    @ApiOperation(value = "首页推荐作品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "pageIndex", value = "页长", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页码", dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "{\n" +
                    "            \"time|创建时间\":null,\n" +
                    "            \"id\":1,\n" +
                    "            \"content｜正文\":\"1\",\n" +
                    "            \"evaluationNum｜评价数量\":0,\n" +
                    "            \"praiseNum｜点赞数\":0,\n" +
                    "            \"forwardingNum｜转发数\":0,\n" +
                    "            \"meterialUrl\":\"http://localhost:8887/pears/file/readFile/null\",\n" +
                    "            \"name｜用户名\":\"王大帅迁\",\n" +
                    "            \"headpic｜用户头像\":\"http://localhost:8887/pears/file/readFile/temp-111.jpg\",\n" +
                    "            \"musicUrl\":\"https://www.nopears.com/music/null\",\n" +
                    "            \"isFriend｜是否关注\":0,\n" +
                    "            \"title｜标题\":null,\n" +
                    "            \"picture｜图片\":null,\n" +
                    "            \"isPraise｜是否点赞\":0,\n" +
                    "            \"introduce｜介绍\":\"1\"\n" +
                    "}")
    })
    @GetMapping("/getContentDetail")
    public Object getContentDetail(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long userid = ModelUtil.getLong(params, "userId");
        long contentId = ModelUtil.getLong(params, "contentId");
        if (contentId == 0) {
            return toError("参数错误");
        }
        return toJsonOk(productService.getContentDetail(contentId, userid));
    }


    @ApiOperation(value = "添加用户查看足迹")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "productId", value = "作品id", dataType = "String"),
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
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "{\n" +
                    "    \"id\":1,\n" +
                    "    \"name|分类名称\":\"动物\",\n" +
                    "    \"image｜图片\":null,\n" +
                    "    \"pid\":null,\n" +
                    "    \"isFocus｜是否关注\":0,\n" +
                    "    \"child｜子分类\":[\n" +
                    "        {\n" +
                    "            \"id\":2,\n" +
                    "            \"name\":\"两栖动物\",\n" +
                    "            \"image\":null,\n" +
                    "            \"pid\":1,\n" +
                    "            \"isFocus\":0,\n" +
                    "            \"child\":null\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}")
    })
    @GetMapping("/getClassifyList")
    public Object getClassifyList(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long userid = ModelUtil.getLong(params, "userId");
        return toJsonOk(productService.getClassifyList(userid));
    }

    @ApiOperation(value = "我关注的分类")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "[\n" +
                    "            {\n" +
                    "                \"id\": 1,\n" +
                    "                \"name\": \"动物\",\n" +
                    "                \"image\": null\n" +
                    "            }\n" +
                    "        ]")
    })
    @GetMapping("/getFocusList")
    public Object getFocusList(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long userid = ModelUtil.getLong(params, "userId");
        validLong(params, "userId");
        return toJsonOk(productService.getFocusList(userid));
    }

}
