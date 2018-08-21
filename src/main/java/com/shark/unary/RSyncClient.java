package com.shark.unary;

import com.shark.unary.checksum.MD5Util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RSync 差异复制，源端
 *
 * @author Shark.Yin
 * @since 1.0
 */
public class RSyncClient {
    private FileProvider provider;
    private DiffDataHandler handler;
    private int chunkSize;
    private RSync.CHECK_STYLE checkStyle;

    private boolean ready = false;

    public RSyncClient(FileProvider provider, DiffDataHandler handler) {
        this(provider, handler, 750, RSync.CHECK_STYLE.TIME_SIZE);
    }

    public RSyncClient(FileProvider provider, DiffDataHandler handler, int chunkSize, RSync.CHECK_STYLE checkStyle) {
        this.provider = provider;
        this.handler = handler;
        this.chunkSize = chunkSize;
        this.checkStyle = checkStyle;
        handler.setFileProvider(provider);
        handler.setChunkSize(chunkSize);
    }

    /**
     * 获取源端文件校验数据
     *
     * @return key: 文件唯一标识； </br>
     * field: 取决于验证方式。如果是 MD5 则为长度为1的数组。如是TIME_SIZE，则[0]为时间，[1]为大小；
     */
    public Map<String, String[]> getFileValidation() throws IOException {
        File file;
        Map<String, String[]> map = new HashMap<>();
        String [] validationData ;
        while ((file = provider.next()) != null) {
            if (RSync.CHECK_STYLE.TIME_SIZE.equals(checkStyle)) {
                validationData = new String[2];
                validationData[0] = Long.toHexString(file.length());
                validationData[1] = Long.toHexString(file.lastModified());
            } else {
                validationData = new String[1];
                validationData[0] = MD5Util.getFileMD5(file);
            }
            map.put(provider.getIdentification(), validationData);
        }
        ready = true;
        return map;
    }

    /**
     * 设置目标端返回的文件数据块校验码,设置后自动开始对比文件
     *
     * @param chunkCheckSum 数据块校验码; key: 文件唯一标识符；</br>
     *                      field: [0]块编号,[1] rolling checksum,[2] md5 checksum
     */
    public void setCheckSumData(Map<String, List<String[]>> chunkCheckSum) {
        if (ready) {

        } else {
            throw new IllegalStateException("can't sync file without file validation .");
        }
    }

}
