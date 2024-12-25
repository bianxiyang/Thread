package cn.com.tcsl.fast.kds.server.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.Map.Entry;

/**
 * <b>功能：</b>http公共工具类<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/3/13 20:08&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Slf4j
public class HttpUtils {

    public static final String KEY_ALGORITHM = "RSA";
    public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";

    public HttpUtils() {
    }

    public static String RSAEncode(String pubkeyb64, String plainText) {
        try {
            byte[] bs = plainText.getBytes();
            PublicKey key = Bs64KeyPub(fmtKeyB64(pubkeyb64));
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(1, key);
            bs = encode(cipher, bs);
            bs = Base64.encodeBase64(bs);
            return new String(bs);
        } catch (NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException
                | NoSuchAlgorithmException var5) {
            log.error("exception:", var5);

            return null;
        }
    }

    private static PublicKey Bs64KeyPub(String key64) {
        byte[] bs = Base64.decodeBase64(key64.getBytes());
        return restorePublicKey(bs);
    }

    private static String fmtKeyB64(String key) {
        return key;
    }

    private static PublicKey restorePublicKey(byte[] keyBytes) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);

        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");

            return factory.generatePublic(x509EncodedKeySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException var4) {
            log.error("exception:", var4);

            return null;
        }
    }

    private static byte[] encode(Cipher cipher, byte[] bs) throws BadPaddingException, IllegalBlockSizeException {
        byte[] enBytes = null;
        int count = 32;

        for (int i = 0; i < bs.length; i += count) {
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(bs, i, i + count));
            enBytes = ArrayUtils.addAll(enBytes, doFinal);
        }

        return enBytes;
    }

    public static String post(HttpClient client, String url, Map<String, Object> params, Map<String, String> headers,
                              String charset) throws IOException {
        HttpPost post = new HttpPost(url);
        if (params != null) {
            Set<String> keySet = params.keySet();
            List<NameValuePair> pairs = new ArrayList<>();
            for (String key : keySet) {
                pairs.add(new BasicNameValuePair(key, params.get(key).toString()));
            }
            post.setEntity(new UrlEncodedFormEntity(pairs, charset));
        }
        setHeaders(post, headers);
        HttpResponse response = client.execute(post);
        int code = response.getStatusLine().getStatusCode();
        if (code != 200) {
            return null;
        }
        return EntityUtils.toString(response.getEntity(), charset);
    }

    public static String post(HttpClient client, String url, String params, Map<String, String> headers, String charset)
            throws IOException {
        HttpPost post = new HttpPost(url);
        if (params != null) {
            post.setEntity(new StringEntity(params, charset));
        }
        setHeaders(post, headers);
        HttpResponse response = client.execute(post);
        int code = response.getStatusLine().getStatusCode();
        if (code != 200) {
            return null;
        }
        return EntityUtils.toString(response.getEntity(), charset);
    }

    private static void setHeaders(HttpRequestBase request, Map<String, String> headers) {
        if (headers == null || headers.size() <= 0) {
            return;
        }
        Entry<String, String> entry;
        Iterator<Entry<String, String>> iterator = headers.entrySet().iterator();
        while (iterator.hasNext()) {
            entry = iterator.next();
            request.addHeader(entry.getKey(), entry.getValue());
        }
    }

}
