package com.ribbonconsumer.controller.leyile;

import com.core.base.controller.BaseController;
import com.core.base.util.ModelUtil;
import com.core.base.util.StrUtil;
import com.ribbonconsumer.service.leyile.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/product")
public class ProductController extends BaseController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @ApiOperation(value = "查看评论列表")
    @PostMapping("/getEvaluationList")
    public Object contentId(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long contentId = ModelUtil.getLong(params, "contentId");
        long userid = ModelUtil.getLong(params, "userid");
        if (contentId == 0) {
            toError("contentId为空");
        }
        toJsonOk(productService.getEvaluationList(contentId,userid));
        return toJsonOk("");
    }

    @ApiOperation(value = "发布作品")
    @PostMapping("/focusUser")
    public Object focusUser(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long userid = ModelUtil.getLong(params, "userId");
        long meterialId = ModelUtil.getLong(params, "meterialId");
        String content = ModelUtil.getStr(params, "content");
        if (userid == 0 || meterialId == 0 || StrUtil.isEmpty(content)) {
            return toError("参数错误");
        }
        productService.insertContent(userid, content, meterialId);
        return toJsonOk("success");
    }

    @ApiOperation(value = "首页推荐作品列表")
    @GetMapping("/getContentList")
    public Object getContentList(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        long userid = ModelUtil.getLong(params, "userid");
        int pageIndex = ModelUtil.getInt(params, "pageIndex", 1);
        int pageSize = ModelUtil.getInt(params, "pageSize", 20);
        result.put("data", productService.getContentList(userid, pageSize, pageIndex));
        return toJsonOk(result);
    }


}
