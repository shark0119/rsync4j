package com.shark.unary;

import com.shark.unary.checksum.MD5Util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Adler32;

/**
 * RSync 差异复制，目标端
 *
 * @author Shark.Yin
 * @since 1.0
 */
public class RSyncServer {

    // 设置文件同步的目标文件夹
    private String path;
    private FileProvider provider;
    private int chunkSize;
    private RSync.CHECK_STYLE checkStyle;
    private PathMapper pathMapper;
    private Adler32 adler32;

    private boolean ready = false;

    public RSyncServer(String path, FileProvider provider) {
        this(path, provider, 750, RSync.CHECK_STYLE.TIME_SIZE);
    }

    public RSyncServer(String path, FileProvider provider, int chunkSize, RSync.CHECK_STYLE checkStyle) {
        this.path = path;
        this.provider = provider;
        this.chunkSize = chunkSize;
        this.checkStyle = checkStyle;
        adler32 = new Adler32();
    }

    /**
     * 根据源端文件校验数据，获取目标端文件分块校验数据
     *
     * @param fileValidation 源端文件校验数据 key: 文件唯一标识；field: 取决于验证方式。
     *                       如果是 MD5 则为长度为1的数组。如是TIME_SIZE，则[0]为时间，[1]为大小；
     * @return key: 文件唯一标识符；field: [0]块编号,[1] rolling checksum,[2] md5 checksum
     * 如果 field 为 null，则表示目标端无此文件。
     */
    public Map<String, List<String[]>> getFileValidation(Map<String, String[]> fileValidation) throws IOException {
        if (pathMapper == null)
            pathMapper = new PathMapper();
        Map<String, List<String[]>> map = new HashMap<>();
        File file;
        boolean modified;
        for (Map.Entry<String, String[]> entry : fileValidation.entrySet()) {
            file = provider.get(entry.getKey());
            file = new File(pathMapper.sourcePathMapper(path, file.getAbsolutePath()));
            if (!file.exists()) {
                map.put(entry.getKey(), null);
                continue;
            }
            if (RSync.CHECK_STYLE.TIME_SIZE.equals(checkStyle)) {
                modified = Long.parseLong(entry.getValue()[0]) == file.lastModified()
                        && Long.parseLong(entry.getValue()[1]) == file.length();
            } else {
                modified = MD5Util.getFileMD5(file).equals(entry.getValue()[0]);
            }
            if (modified) {
                map.put(entry.getKey(), calCheckSums(file));
            }
        }
        ready = true;
        return map;
    }

    protected List<String[]> calCheckSums (File file) throws IOException{
        try (
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))
        ) {
            byte[] data = new byte[chunkSize];
            int offset = 0, readSize, chunkIndex = 0;
            String[] checkSums;
            do {
                readSize = bis.read(data, offset, chunkSize);
                checkSums = new String[3];
                checkSums[0] = Integer.toString(chunkIndex);
                // TODO checkSums[1] ;
                adler32.update(data);
                checkSums[2] = MD5Util.getMD5String(data);
                offset += chunkSize;
                chunkIndex ++;
            } while (readSize != -1);
        } catch (IOException e) {
            throw e;
        }
        return null;
    }
    /**
     * 根据以匹配的数据块和差异数据来同步文件
     *
     * @param identification 文件唯一标识符
     * @param offset         偏移量
     * @param diffData       差异数据，可以为空数组，标识匹配到了连续的数据块
     * @param chunkIndex     数据块序号,当值为-1 时,表示同步完成
     */
    public void syncFile(String identification, long offset, byte[] diffData, int chunkIndex) {
        if (ready) {

        } else {
            throw new IllegalStateException("can't sync file without file validation .");
        }
    }

    public RSyncServer setPathMapper(PathMapper pathMapper) {
        this.pathMapper = pathMapper;
        return this;
    }
}
