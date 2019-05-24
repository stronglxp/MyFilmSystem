package com.zmq.filmsystem.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.zmq.filmsystem.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @Description TODO
 * @Version 1.0
 */
@Service
public class QiniuService {

    private static final Logger logger = LoggerFactory.getLogger(QiniuService.class);

    // 设置好账号的ACCESS_KEY和SECRET_KEY
    String ACCESS_KEY = "l69HOV1eLVU8iCFfUXOeY0nm3gE7cdSutVNza2s-";
    String SECRET_KEY = "ercP6OJOMYP1YgieiR-SBcbK09MYcQf0kE3D3ujW";
    // 要上传的空间
    String bucketname = "filmsystem";

    // 密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    // 构造一个带指定Zone对象的配置类,不同的七云牛存储区域调用不同的zone
    Configuration cfg = new Configuration(Zone.zone0());
    // ...其他参数参考类注释
    UploadManager uploadManager = new UploadManager(cfg);

    // 测试域名，只有30天有效期
    private static String QINIU_IMAGE_DOMAIN = "http://prwk901o2.bkt.clouddn.com/";

    // 简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken() {
        return auth.uploadToken(bucketname);
    }

    public String saveImage(MultipartFile file) throws IOException {
        try {
            int dotPos = file.getOriginalFilename().lastIndexOf(".");
            if (dotPos < 0) {
                return null;
            }
            String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
            // 判断是否是合法的文件后缀
            if (!FileUtil.isFileAllowed(fileExt)) {
                return null;
            }

            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
            // 调用put方法上传
            Response res = uploadManager.put(file.getBytes(), fileName, getUpToken());
            // 打印返回的信息
            if (res.isOK() && res.isJson()) {
                // 返回这张存储照片的地址http://ox6xu9hb7.bkt.clouddn.com/f07ff5a8945b4d6fb1bfc4cc8910e0fb.png，json串格式为：{"msg":"http://ox6xu9hb7.bkt.clouddn.com/f07ff5a8945b4d6fb1bfc4cc8910e0fb.png","code":0}
                return QINIU_IMAGE_DOMAIN + JSONObject.parseObject(res.bodyString()).get("key");
            } else {
                logger.error("七牛异常:" + res.bodyString());
                return null;
            }
        } catch (QiniuException e) {
            // 请求失败时打印的异常的信息
            logger.error("七牛异常:" + e.getMessage());
            return null;
        }
    }
}
