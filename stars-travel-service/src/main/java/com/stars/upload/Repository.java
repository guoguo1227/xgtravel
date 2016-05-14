package com.stars.upload;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
/**
 * Description :
 * Author : guo
 * Date : 2016/1/17 22:05
 */
@Service
public class Repository implements ServletContextAware{

    public String getRealPath(String name){
        String realPath = ctx.getRealPath(name);
        if(realPath==null){
            realPath = ctx.getRealPath("/")+realPath;
        }
        return realPath;
    }

    public String storeByExt(String path,String ext,MultipartFile file) throws IOException {
        String filename = UploadUtils.generateFilename(path, ext);
        File dest = new File(getRealPath(filename));
        dest = UploadUtils.getUniqueFile(dest);
        store(file,dest);
        return filename;
    }

    public String storeByName(String filename,MultipartFile file) throws IOException {
        File dest = new File(getRealPath(filename));
        store(file,dest);
        return filename;
    }

    public String storeByExt(String path,String ext,File file)throws IOException{
        String filename = UploadUtils.generateFilename(path, ext);
        File dest = new File(getRealPath(filename));
        dest = UploadUtils.getUniqueFile(dest);
        store(file,dest);
        return filename;
    }

    public String storeByName(String filename,File file)throws IOException{
        File dest = new File(getRealPath(filename));
        store(file,dest);
        return filename;
    }
    public File retrieve(String name) {
        return new File(ctx.getRealPath(name));
    }

    public void store(MultipartFile file,File dest)throws IOException{
        try{
            UploadUtils.checkDirAndCreate(dest.getParentFile());
            file.transferTo(dest);
        }catch(IOException e){
            logger.error("Transfer file error when upload file",e);
            throw e;
        }
    }

    public void store(File file,File dest)throws IOException{
        try{
            UploadUtils.checkDirAndCreate(dest);
            //FileUtils.copyFile(file, dest);
        }catch(Exception e){
            logger.error("Transfer file error when upload file",e);
            throw e;
        }
    }

    private Logger logger = LoggerFactory.getLogger(Repository.class);

    private ServletContext ctx;

    @Override
    public void setServletContext(ServletContext arg0) {
        this.ctx = arg0;

    }
}
