package com.ctd.wechat.utils;

import com.ctd.wechat.vo.WeChatRetVO;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Formatter;
import java.util.UUID;

/**
 * WeChatSignUtils
 *
 * @author chentudong
 * @date 2020/4/22 4:14 下午
 * @since 1.0
 */
public class WeChatSignUtils
{
    public static WeChatRetVO sign(String jsapiTicket, String url)
    {
        String nonceStr = createNonceStr();
        String timestamp = createTimestamp();
        String stringSort;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        stringSort = "jsapi_ticket=" + jsapiTicket +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(stringSort.getBytes(StandardCharsets.UTF_8));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return new WeChatRetVO(url, jsapiTicket, nonceStr, timestamp, signature);
    }

    private static String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String createNonceStr()
    {
        return UUID.randomUUID().toString();
    }

    private static String createTimestamp()
    {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    public static String byteToStr(byte[] byteArray)
    {
        StringBuilder strDigest = new StringBuilder();
        for (byte b : byteArray)
        {
            strDigest.append(byteToHexStr(b));
        }
        return strDigest.toString();
    }

    public static String byteToHexStr(byte mByte)
    {
        char[] digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = digit[mByte & 0X0F];
        return new String(tempArr);
    }

    public static boolean check(String signature, String content)
    {
        try
        {
            //sha1Hex 加密
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(content.getBytes());
            return (byteToStr(digest).toLowerCase()).equals(signature);
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static StringBuilder checkToken(String token, String timestamp, String nonce)
    {
        //排序
        String[] arr = {token, timestamp, nonce};
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (String s : arr)
        {
            content.append(s);
        }
        return content;
    }
}
