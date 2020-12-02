package com.ribbonconsumer.config.swagger;

import com.github.xiaoymin.knife4j.spring.annotations.EnableSwaggerBootstrapUi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.AbstractPathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration //标记配置类
@EnableSwagger2 //开启在线接口文档
@EnableSwaggerBootstrapUi
public class Swagger2Config {

    private static final String SWAGGER_SCAN_ADMIN_PACKAGE = "com.ribbonconsumer.controller.leyile.admin";
    private static final String SWAGGER_SCAN_MA_USER_PACKAGE = "com.ribbonconsumer.controller.leyile.mini";
    private static final String VERSION = "";

    @Value("${swagger.base.apiPath}")
    private String swaggerPath;
    @Value("${base.isonline}")
    public String isOnline;

    @Bean
    public Docket createAdminApi() {
        if ("1".equals(isOnline)) {
            return apiDocketOnline("后台接口");
        } else {
            return apiDocketTest("后台接口", SWAGGER_SCAN_ADMIN_PACKAGE, "后台所有业务相关接口");
        }
    }

    @Bean
    public Docket createMaUserApi() {
        if ("1".equals(isOnline)) {
            return apiDocketOnline("小程序接口");
        } else {
            return apiDocketTest("小程序接口", SWAGGER_SCAN_MA_USER_PACKAGE, "前端小程序用户端所有业务相关接口");
        }
    }

//    @Bean
//    public Docket createAppDoctorApi() {
//        if (!"1".equals(isOnline) && !"2".equals(isOnline)) {
//            return apiDocketOnline("APP医生端接口");
//        } else {
//            return apiDocketTest("APP医生端接口", SWAGGER_SCAN_APP_DOCTOR_PACKAGE, "前端APP医生端所有业务相关接口");
//        }
//    }
//
//    @Bean
//    public Docket createWebApi() {
//        if (!"1".equals(isOnline) && !"2".equals(isOnline)) {
//            return apiDocketOnline("WEBAPP接口");
//        } else {
//            return apiDocketTest("WEBAPP接口", SWAGGER_SCAN_WEB_PACKAGE, "前端H5所有业务相关接口");
//        }
//    }
//
//
//    @Bean
//    public Docket createPcApi() {
//        if (!"1".equals(isOnline) && !"2".equals(isOnline)) {
//            return apiDocketOnline("WEBPC接口");
//        } else {
//            return apiDocketTest("WEBPC接口", SWAGGER_SCAN_PC_PACKAGE, "前端pc所有业务相关接口");
//        }
//    }
//
//    @Bean
//    public Docket createH5Api() {
//        if (!"1".equals(isOnline) && !"2".equals(isOnline)) {
//            return apiDocketOnline("H5接口");
//        } else {
//            return apiDocketTest("H5接口", SWAGGER_SCAN_H5_PACKAGE, "H5所有业务相关接口");
//        }
//    }
//
//
//    @Bean
//    public Docket createCommApi() {
//        if (!"1".equals(isOnline) && !"2".equals(isOnline)) {
//            return apiDocketOnline("通用接口");
//        } else {
//            return apiDocketTest("通用接口", SWAGGER_SCAN_COMM_PACKAGE, "全局通用接口(图片上传等)");
//        }
//    }

    private Docket apiDocketTest(String name, String basePackage, String description) {
        Tag[] tags = allTags(basePackage);
        Tag tag = new Tag("暂无接口", "");
        if (tags.length > 0) {
            tag = tags[0];
        }
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(name)
                .apiInfo(new ApiInfoBuilder()
                        .title("uiknow接口文档")//设置文档的标题
                        .description(description)//设置文档的描述->1.Overview
                        .version(VERSION)//设置文档的版本信息-> 1.1 Version information
                        .contact(new Contact("", "", ""))//设置文档的联系方式->1.2 Contact information
                        .termsOfServiceUrl("")//设置文档的License信息->1.3 License information
                        .build())
                .pathProvider(new AbstractPathProvider() {
                    @Override
                    protected String applicationPath() {
                        return swaggerPath;
                    }

                    @Override
                    protected String getDocumentationPath() {
                        return swaggerPath;
                    }
                })
                .tags(tag, tags)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))//api接口包扫描路径
                .paths(PathSelectors.any())//可以根据url路径设置哪些请求加入文档，忽略哪些请求
                .build();
    }

    private Tag[] allTags(String basePackage) {
        if (SWAGGER_SCAN_ADMIN_PACKAGE.equals(basePackage)) {
            return new Tag[]{
                    //后台管理Tag标记
                    new Tag(DocVer.Z9.ADMIN.SYS.KEY, DocVer.Z9.ADMIN.SYS.DES),
//                    new Tag(DocVer.Z9.ADMIN.CODE.KEY, DocVer.Z9.ADMIN.CODE.DES),
//                    new Tag(DocVer.Z9.ADMIN.DOCTOR.KEY, DocVer.Z9.ADMIN.DOCTOR.DES),
//                    new Tag(DocVer.Z9.ADMIN.USER.KEY, DocVer.Z9.ADMIN.USER.DES),
//                    new Tag(DocVer.Z9.ADMIN.ORDER.KEY, DocVer.Z9.ADMIN.ORDER.DES),
//                    new Tag(DocVer.Z9.ADMIN.APPOINT.KEY, DocVer.Z9.ADMIN.APPOINT.DES),
//                    new Tag(DocVer.Z9.ADMIN.DATA.KEY, DocVer.Z9.ADMIN.DATA.DES),
//                    new Tag(DocVer.Z9.ADMIN.FINANCE.KEY, DocVer.Z9.ADMIN.FINANCE.DES),
//                    new Tag(DocVer.Z9.ADMIN.TEAM.KEY, DocVer.Z9.ADMIN.TEAM.DES),
//                    new Tag(DocVer.Z9.ADMIN.WECHAT.KEY, DocVer.Z9.ADMIN.WECHAT.DES),
//                    new Tag(DocVer.Z9.ADMIN.REPAIR.KEY, DocVer.Z9.ADMIN.REPAIR.DES),
//                    new Tag(DocVer.Z9.ADMIN.SMS.KEY, DocVer.Z9.ADMIN.SMS.DES),
//                    new Tag(DocVer.Z9.ADMIN.ARTICLE.KEY, DocVer.Z9.ADMIN.ARTICLE.DES),
//                    new Tag(DocVer.Z9.ADMIN.CITICBANK.KEY, DocVer.Z9.ADMIN.CITICBANK.DES),
//                    new Tag(DocVer.Z9.ADMIN.COUPON.KEY, DocVer.Z9.ADMIN.COUPON.DES),
            };
        }
//        else if (SWAGGER_SCAN_APP_USER_PACKAGE.equals(basePackage)) {
//            return new Tag[]{
//                    //前端APP用户端Tag标记
//                    new Tag(DocVer.Z9.APPUSER.USER.KEY, DocVer.Z9.APPUSER.USER.DES),
//                    new Tag(DocVer.Z9.APPUSER.ORDER.KEY, DocVer.Z9.APPUSER.ORDER.DES),
//                    new Tag(DocVer.Z9.APPUSER.PRESCRIPTION.KEY, DocVer.Z9.APPUSER.PRESCRIPTION.DES),
//                    new Tag(DocVer.Z9.APPUSER.APPOINT.KEY, DocVer.Z9.APPUSER.APPOINT.DES),
//                    new Tag(DocVer.Z9.APPUSER.APPOINT_TJ.KEY, DocVer.Z9.APPUSER.APPOINT_TJ.DES),
//                    new Tag(DocVer.Z9.APPUSER.DOCTOR.KEY, DocVer.Z9.APPUSER.DOCTOR.DES),
//                    new Tag(DocVer.Z9.APPUSER.APPHOME.KEY, DocVer.Z9.APPUSER.APPHOME.DES),
//                    new Tag(DocVer.Z9.APPUSER.ARTICLE.KEY, DocVer.Z9.APPUSER.ARTICLE.DES),
//                    new Tag(DocVer.Z9.APPUSER.SPECIAL.KEY, DocVer.Z9.APPUSER.SPECIAL.DES),
//                    new Tag(DocVer.Z9.APPUSER.EVALUATE.KEY, DocVer.Z9.APPUSER.EVALUATE.DES),
//                    new Tag(DocVer.Z9.APPUSER.AI.KEY, DocVer.Z9.APPUSER.AI.DES),
//                    new Tag(DocVer.Z9.APPUSER.CARD.KEY, DocVer.Z9.APPUSER.CARD.DES),
//                    new Tag(DocVer.Z9.APPUSER.COUPON.KEY, DocVer.Z9.APPUSER.COUPON.DES),
//                    new Tag(DocVer.Z9.APPUSER.MEDICALSKILLS.KEY, DocVer.Z9.APPUSER.MEDICALSKILLS.DES),
//            };
         else if (SWAGGER_SCAN_MA_USER_PACKAGE.equals(basePackage)) {
            return new Tag[]{
                    //前端APP用户端Tag标记
                    new Tag(DocVer.Z9.MAUSER.USER.KEY, DocVer.Z9.MAUSER.USER.DES),
//                    new Tag(DocVer.Z9.MAUSER.ORDER.KEY, DocVer.Z9.MAUSER.ORDER.DES),
//                    new Tag(DocVer.Z9.MAUSER.PRESCRIPTION.KEY, DocVer.Z9.MAUSER.PRESCRIPTION.DES),
//                    new Tag(DocVer.Z9.MAUSER.APPOINT.KEY, DocVer.Z9.MAUSER.APPOINT.DES),
//                    new Tag(DocVer.Z9.MAUSER.APPOINT_TJ.KEY, DocVer.Z9.MAUSER.APPOINT_TJ.DES),
//                    new Tag(DocVer.Z9.MAUSER.DOCTOR.KEY, DocVer.Z9.MAUSER.DOCTOR.DES),
//                    new Tag(DocVer.Z9.MAUSER.APPHOME.KEY, DocVer.Z9.MAUSER.APPHOME.DES),
//                    new Tag(DocVer.Z9.MAUSER.ARTICLE.KEY, DocVer.Z9.MAUSER.ARTICLE.DES),
//                    new Tag(DocVer.Z9.MAUSER.SPECIAL.KEY, DocVer.Z9.MAUSER.SPECIAL.DES),
//                    new Tag(DocVer.Z9.MAUSER.EVALUATE.KEY, DocVer.Z9.MAUSER.EVALUATE.DES),
//                    new Tag(DocVer.Z9.MAUSER.AI.KEY, DocVer.Z9.MAUSER.AI.DES),
//                    new Tag(DocVer.Z9.MAUSER.COUPON.KEY, DocVer.Z9.MAUSER.COUPON.DES),
//                    new Tag(DocVer.Z9.MAUSER.TEAM.KEY, DocVer.Z9.MAUSER.TEAM.DES),
//                    new Tag(DocVer.Z9.MAUSER.TRIP.KEY, DocVer.Z9.MAUSER.TRIP.DES),
            };
        }
//        } else if (SWAGGER_SCAN_APP_DOCTOR_PACKAGE.equals(basePackage)) {
//            return new Tag[]{
//                    //前端APP医生端Tag标记
//                    new Tag(DocVer.Z9.APPDOCTOR.DOCTOR.KEY, DocVer.Z9.APPDOCTOR.DOCTOR.DES),
//                    new Tag(DocVer.Z9.APPDOCTOR.ORDER.KEY, DocVer.Z9.APPDOCTOR.ORDER.DES),
//                    new Tag(DocVer.Z9.APPDOCTOR.PRESCRIPTION.KEY, DocVer.Z9.APPDOCTOR.PRESCRIPTION.DES),
//                    new Tag(DocVer.Z9.APPDOCTOR.TEAM.KEY, DocVer.Z9.APPDOCTOR.TEAM.DES),
//                    new Tag(DocVer.Z9.APPDOCTOR.ARTICLE.KEY, DocVer.Z9.APPDOCTOR.ARTICLE.DES),
//                    new Tag(DocVer.Z9.APPDOCTOR.MEDICAL.KEY, DocVer.Z9.APPDOCTOR.MEDICAL.DES),
//                    new Tag(DocVer.Z9.APPDOCTOR.MEDICALSKILLS.KEY, DocVer.Z9.APPDOCTOR.MEDICALSKILLS.DES),
//            };
//        } else if (SWAGGER_SCAN_WEB_PACKAGE.equals(basePackage)) {
//            return new Tag[]{
//                    //前端WEBTag标记
//            };
//        } else if (SWAGGER_SCAN_PC_PACKAGE.equals(basePackage)) {
//            return new Tag[]{
//                    new Tag(DocVer.Z9.PC.HOMEPAGE.KEY, DocVer.Z9.PC.HOMEPAGE.DES)
//                    //前端PCTag标记
//            };
//        } else if (SWAGGER_SCAN_H5_PACKAGE.equals(basePackage)) {
//            return new Tag[]{
//                    new Tag(DocVer.Z9.H5USER.USER.KEY, DocVer.Z9.H5USER.USER.DES),
//                    new Tag(DocVer.Z9.H5USER.XINGUAN.KEY, DocVer.Z9.H5USER.XINGUAN.DES),
//                    new Tag(DocVer.Z9.H5USER.SPECIAL.KEY, DocVer.Z9.H5USER.SPECIAL.DES),
//                    new Tag(DocVer.Z9.H5USER.EVALUATE.KEY, DocVer.Z9.H5USER.EVALUATE.DES),
//                    new Tag(DocVer.Z9.H5USER.TJ.KEY, DocVer.Z9.H5USER.TJ.DES),
//                    //前端PCTag标记
//            };
//        } else if (SWAGGER_SCAN_COMM_PACKAGE.equals(basePackage)) {
//            return new Tag[]{
//                    //通用接口Tag标记
//                    new Tag(DocVer.Z9.COMMOM.KEY, DocVer.Z9.COMMOM.DES)
//            };
//        } else if (SWAGGER_SCAN_APP_OPEN_USER_PACKAGE.equals(basePackage)) {
//            return new Tag[]{
//                    //前端OPEN用户端Tag标记
//                    new Tag(DocVer.Z9.OPENUSER.DOCTOR.KEY, DocVer.Z9.OPENUSER.DOCTOR.DES),
//                    new Tag(DocVer.Z9.OPENUSER.AUTH.KEY, DocVer.Z9.OPENUSER.AUTH.DES),
//                    new Tag(DocVer.Z9.APPUSER.ORDER.KEY, DocVer.Z9.APPUSER.ORDER.DES),
//            };
//        }
        else {
            return new Tag[]{};
        }
    }

    private Docket apiDocketOnline(String name) {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(name)
                .apiInfo(new ApiInfoBuilder()
                        .title("")
                        .description("")
                        .license("")
                        .licenseUrl("")
                        .termsOfServiceUrl("")
                        .version("")
                        .contact(new Contact("", "", ""))
                        .build())
                .select()
                .paths(PathSelectors.none())//如果是线上环境，添加路径过滤，设置为全部都不符合
                .build();
    }


}
