package com.jun.controller;

import com.jun.constant.PathConstant;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by Harmony.JuN.CN on 2017/4/6.
 */
@Controller
@RequestMapping("/upload")
public class Upload {

    @ResponseBody
    @RequestMapping(value = "/uploadMessage",produces = "text/html;charset=UTF-8",method = RequestMethod.POST)
    public String uploadMessage(@RequestParam("pictures")MultipartFile[] pictures,@RequestParam("content")String content,@RequestParam("radio") MultipartFile radio){

        System.out.println("-------------");
        JSONObject result = new JSONObject();

        String imagePath = PathConstant.PICTURE_SAVE_PATH;
        String radioPath = PathConstant.RADIO_SAVE_PATH;

        File imageDir = new File(imagePath);
        File radioDir = new File(radioPath);

        if (!imageDir.exists() && !imageDir.isDirectory()){
            imageDir.mkdir();
        }

        if (!radioDir.exists() && !radioDir.isDirectory()){
            radioDir.mkdir();
        }

        for (MultipartFile picture : pictures) {
            if (picture.isEmpty() || picture == null){
                continue;
            }
            String originName = picture.getOriginalFilename();
            int index = originName.lastIndexOf("/");
            originName = originName.substring(index+1);
            String path = imagePath + originName;
            File item = new File(path);
            try {
                picture.transferTo(item);
            } catch (IOException e) {
                System.out.println("图片上传失败");
                e.printStackTrace();
            }
        }

        result.put("picture","success");
        result.put("content",content);

        if (radio.isEmpty() || radio == null){
            result.put("radio","");
        }else {
            String originName = radio.getOriginalFilename();
            int index = originName.lastIndexOf("/");
            originName = originName.substring(index+1);
            String path = radioPath + originName;
            File file = new File(path);
            try {
                radio.transferTo(file);
            } catch (IOException e) {
                System.out.println("音频上传失败");
                e.printStackTrace();
            }
        }

        result.put("radio","success");



        return result.toString();
    }

}
