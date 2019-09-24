package com.spark.wsclient.utils;


import com.spark.wsclient.base.WebSocketConstant;

import java.nio.ByteBuffer;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/23
 * 描    述：数据工具类
 * 修订历史：
 * ================================================
 */
public class DataUtils {
    private static ByteBuffer buffer = ByteBuffer.allocate(8);

    public static byte[] initBytes(short cmd, byte[] body) {
        int length = body == null ? 26 : (26 + body.length);
        byte[] sendBytes = new byte[length];
        byte[] lenBytes = int2ByteArray(length);
        byte[] sequenceIdBytes = long2Bytes(WebSocketConstant.SEQUESCEID);
        byte[] codeBytes = short2ByteArray(cmd);
        byte[] versionBytes = int2ByteArray(WebSocketConstant.VERSION);
        byte[] terminalBytes = WebSocketConstant.TERMINIAL.getBytes();
        byte[] requestidBytes = int2ByteArray(WebSocketConstant.REQUESTID);

        System.arraycopy(lenBytes, 0, sendBytes, 0, lenBytes.length);
        System.arraycopy(sequenceIdBytes, 0, sendBytes, lenBytes.length, sequenceIdBytes.length);
        System.arraycopy(codeBytes, 0, sendBytes, lenBytes.length + sequenceIdBytes.length, codeBytes.length);
        System.arraycopy(versionBytes, 0, sendBytes, lenBytes.length + sequenceIdBytes.length + codeBytes.length, versionBytes.length);
        System.arraycopy(terminalBytes, 0, sendBytes, lenBytes.length + sequenceIdBytes.length + codeBytes.length + versionBytes.length, terminalBytes.length);
        System.arraycopy(requestidBytes, 0, sendBytes, lenBytes.length + sequenceIdBytes.length + codeBytes.length + versionBytes.length + terminalBytes.length, requestidBytes.length);
        if (body != null) {
            System.arraycopy(body, 0, sendBytes, lenBytes.length + sequenceIdBytes.length + codeBytes.length + versionBytes.length + terminalBytes.length + requestidBytes.length, body.length);
        }
        return sendBytes;
    }

    //byte 数组与 long 的相互转换
    public static byte[] long2Bytes(long x) {
        buffer.putLong(0, x);
        return buffer.array();
    }

    public static byte[] int2ByteArray(int a) {
        return new byte[]{
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }

    public static byte[] short2ByteArray(short a) {
        return new byte[]{
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }


}
