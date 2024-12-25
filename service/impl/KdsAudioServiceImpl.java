package cn.com.tcsl.fast.kds.server.service.impl;

import cn.com.tcsl.fast.kds.server.service.KdsAudioService;
import cn.com.tcsl.fast.oss.aliyun.OSSStoreClient;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.*;


@Service
@Slf4j
public class KdsAudioServiceImpl implements KdsAudioService {

    @Resource
    private OSSStoreClient ossStoreClient;
    @Value("${audio.file.path}")
    private String filePath;
    @Value("${audio.merge.file.path}")
    private String mergeFilePath;
    private static final String MP3 = ".mp3";

    public String mergeAudio(String[] files, String savefileName) {
        log.info("开始将语音合并");
        SequenceInputStream sequenceInputStream = null;
        try {
            List<InputStream> inputStreams = new ArrayList<>();
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i];
                InputStream fileInputStream = null;
                try {
                    fileInputStream = ossStoreClient.getObjectAndDirectory(fileName, filePath);
                } catch (OSSException e) {
                    log.error("查找oss文件发生异常", e);
                } catch (ClientException e) {
                    e.printStackTrace();
                }
                if (Objects.nonNull(fileInputStream)) {
                    inputStreams.add(fileInputStream);
                }
            }
            final Iterator<InputStream> iterator = inputStreams.iterator();
            //  新建序列流
            sequenceInputStream = new SequenceInputStream(new Enumeration<InputStream>() {
                @Override
                public boolean hasMoreElements() {
                    return iterator.hasNext();
                }

                @Override
                public InputStream nextElement() {
                    return iterator.next();
                }
            });
            return ossStoreClient.putObjectAndDirectory(savefileName + MP3, sequenceInputStream, mergeFilePath);
        } catch (IOException e) {
            log.error("合并语音时候发生异常", e);
        }
        return null;
    }

}