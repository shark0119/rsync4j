package com.shark.unary.checksum;

import java.util.zip.CRC32;

public class CRC32Util {
    /**
     * 计算 CRC32 校验码
     *
     * @param data 字节数组数据
     * @return CRC32 校验码
     */
    public static long encode(byte[] data) {
        CRC32 crc32 = new CRC32();
        crc32.update(data);
        return crc32.getValue();
    }
}
