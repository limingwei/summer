package cn.limw.summer.web.upload.qiniu;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import cn.limw.summer.util.Asserts;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.StringUtil;
import cn.limw.summer.web.upload.AbstractFileStore;

import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.net.CallRet;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rs.RSClient;

/**
 * 上传文件到qiniu.com云存储
 * @author li
 * @version 1 (2014年9月10日 上午11:51:11)
 * @since Java7
 */
public class QiniuFileStore extends AbstractFileStore {
    private static final Logger log = Logs.slf4j();

    private String accessKey;

    private String secretKey;

    private String bucketName;

    private String bucketDomain;

    public Boolean upload(String fileName, InputStream inputStream) {
        try {
            PutPolicy putPolicy = new PutPolicy(getBucketName());
            String uptoken = putPolicy.token(newMac());
            PutExtra extra = new PutExtra();
            PutRet ret = IoApi.Put(uptoken, fileName, inputStream, extra);
            log.info("upload() fileName={}, statusCode={}, response={}", fileName, ret.getStatusCode(), ret.getResponse());
            return ret.ok();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void download(String fileName, HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect("http://" + getBucketDomain() + "/" + fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean delete(String fileName) {
        RSClient client = new RSClient(newMac());
        CallRet callRet = client.delete(getBucketName(), fileName);
        log.info("delete() fileName={}, statusCode={}, response={}", fileName, callRet.getStatusCode(), callRet.getResponse());
        return callRet.ok();
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public void setBucketDomain(String bucketDomain) {
        log.info("QiniuFileStore.bucketDomain={}", bucketDomain);
        Asserts.noEmpty(bucketDomain, "QiniuFileStore.bucketDomain不可以为空");
        this.bucketDomain = bucketDomain.endsWith(".com") ? bucketDomain : (bucketDomain + ".qiniucdn.com");
    }

    public String getBucketDomain() {
        return StringUtil.isEmpty(bucketDomain) ? (getBucketName() + ".qiniudn.com") : bucketDomain;
    }

    public Mac newMac() {
        return new Mac(getAccessKey(), getSecretKey());
    }

    public String toString() {
        return super.toString() + ", bucketName=" + getBucketName() + ", bucketDomain=" + getBucketDomain();
    }
}