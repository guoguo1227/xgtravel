package com.stars.travel.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.stars.travel.enums.ImageType;
import com.stars.travel.model.ext.RequestResult;
import com.stars.upload.Repository;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description : 文件上传
 * Author : guo
 * Date : 2016/1/17 18:05
 */
@Controller
@RequestMapping("image")
public class UploadController extends BaseController{

    @Resource(name = "repository")
    private Repository repository;
    /**
     * Logger log
     */
    private static final Logger log = LoggerFactory.getLogger(UploadController.class);

    /**
     * uploadImages
     *
     * @param file     待上传的图片对象
     * @param request  用户请求对象
     * @描述: 图片上传
     */

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public
    @ResponseBody

    Object uploadImages(Integer imageType, @RequestParam(value = "file", required = false) MultipartFile file,
                      HttpServletRequest request) {
        boolean flag = false;
        String fileUrl = null;
        String originName = file.getOriginalFilename();
        String ext = FilenameUtils.getExtension(originName).toLowerCase(Locale.ENGLISH);

        //暂时使用原文件名，作为上传后文件的名称
        String filename = originName;
        if(null == imageType) imageType=1;

        try {
           // String ctx = request.getContextPath();
            fileUrl = repository.storeByExt(ImageType.getImagePath(imageType), ext, file);
            flag = true;
        } catch (IllegalStateException e) {
            log.error("Upload images error: " + e.getMessage());
        } catch (IOException e) {
            log.error("Upload images error: " + e.getMessage());
        } finally {
            if (flag) {
                File f = new File(fileUrl);
                if (!f.exists())
                    f.mkdirs();
                if (!file.isEmpty()) {
                    try {
                        FileOutputStream fos = new FileOutputStream(fileUrl + filename);
                        InputStream in = file.getInputStream();
                        int b = 0;
                        while ((b = in.read()) != -1) {
                            fos.write(b);
                        }
                        fos.close();
                        in.close();
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
                log.info("上传成功。");
            }
        }
        RequestResult result = new RequestResult();
        result.setSuccess(true);
        JSONObject object = new JSONObject();
        object.put("uploadPath",fileUrl);
        result.setData(object);
        return  gson.toJson(result);
    }
}
