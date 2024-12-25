package cn.com.tcsl.fast.kds.server.util;


import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;
import lombok.extern.slf4j.Slf4j;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;

import java.io.File;
import java.net.URI;

/**
 * <b>功能：</b>音频文件处理工具<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20200219&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;于宝龙&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Slf4j
public class AudioUtil {
    /**
     * <b>功能描述：</b>修改文件比特率<br>
     * <b>修订记录：</b><br>
     * <li>20200219&nbsp;&nbsp;|&nbsp;&nbsp;于宝龙&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param uri
     * @param realFileName
     * @return
     */
    public static String modifyAudioBitRate(URI uri, String realFileName) {

        try {
            String bitRate = getHead(uri.resolve(realFileName));
            log.info("获取到音频文件比特率为:" + bitRate);
            File source = new File(uri.resolve(realFileName));//这是源文件，目录里必须要有的
            String newFileName = realFileName.substring(0, realFileName.indexOf(".mp3")) + "_new.mp3";
            File target = new File(uri.resolve(newFileName));//这是目标文件
            if (target.exists()) {
                target.delete();
            }
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("libmp3lame");
            audio.setBitRate(new Integer(128000));
            audio.setChannels(new Integer(1));
            audio.setSamplingRate(new Integer(44100));
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setFormat("mp3");
            attrs.setAudioAttributes(audio);
            Encoder encoder = new Encoder();
            encoder.encode(source, target, attrs);
            return newFileName;
        } catch (Exception e) {
            log.error("修改音频文件比特率异常", e);
        }
        return realFileName;
    }

    /**
     * <b>功能描述：</b>修改文件比特率<br>
     * <b>修订记录：</b><br>
     * <li>20200219&nbsp;&nbsp;|&nbsp;&nbsp;于宝龙&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @return
     */
    private static String getHead(URI fileName) {
        try {
            MP3File mp3File = new MP3File(fileName.getPath());//封装好的类
            MP3AudioHeader header = mp3File.getMP3AudioHeader();
            if (header == null) {
                return "";
            }
            return header.getBitRate();
        } catch (Exception e) {
            log.error("获取音频文件头信息异常", e);
        }
        return "";
    }


}
