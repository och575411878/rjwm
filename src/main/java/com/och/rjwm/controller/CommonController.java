package com.och.rjwm.controller;


import com.och.rjwm.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${rjwm.path}") // 为什么使用value无法完成注入.
    private String basePath;


    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        log.info(file.getOriginalFilename());
        String[] split = file.getOriginalFilename().split("\\.");
        String fileName = UUID.randomUUID().toString() +"."+ split[split.length-1];
        File storeFile = new File(basePath);
        if(!storeFile.exists())storeFile.mkdirs();
        try {
            file.transferTo(new File(basePath+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  R.success(fileName);
    }

    /**
     * 下载文件
     * @param name 文件名
     * @param response
     * @return
     */
    // 抄写一遍
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try {
            FileInputStream fileInputStream = new FileInputStream(basePath + name);
            ServletOutputStream outputStream = response.getOutputStream();

            int length = 0;
            byte[] buffer = new byte[1024];
            while ((length = fileInputStream.read(buffer))!=-1){
                outputStream.write(buffer,0,length);
            }
            fileInputStream.close();
            outputStream.close();
        } catch (FileNotFoundException fe){
          log.warn("rjwm的老菜品有bug正常!");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
