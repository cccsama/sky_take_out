package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 通用接口
 */
@RestController
@RequestMapping("admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {

    // Autowired注解的作用:自动将匹配的Bean装配到@Autowired标注的变量、方法或构造函数上。(依赖注入)
    @Autowired
    private AliOssUtil aliOssUtil;


    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传接口被调用:{}",file);
        try {
            //  获取原始文件名
            String originalFilename = file.getOriginalFilename();
            // 截取原始文件名的扩展名
            String extName = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 生成新的文件名: UUID + 扩展名
            String newFileName = UUID.randomUUID().toString() + extName;

            // 文件上传请求路径
            String filePath  = aliOssUtil.upload(file.getBytes(), newFileName);
            return Result.success(filePath);
        }catch(IOException e){
            e.printStackTrace();
            log.error("文件上传失败:{}",e);

        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
