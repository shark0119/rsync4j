package com.shark.unary;

import java.util.Objects;

/**
 * 差异复制 RSync 算法
 *
 * @author Shark.Yin
 * @since 1.0
 */

public class RSync {

    private static final int DEFAULT_CHUNK_SIZE = 750;

    private int chunkSize;
    private CHECK_STYLE checkStyle;
    private FileProvider provider;
    private PathMapper pathMapper;

    public RSync(FileProvider provider) {
        this(DEFAULT_CHUNK_SIZE, CHECK_STYLE.TIME_SIZE, provider);
    }

    public RSync(int chunkSize, CHECK_STYLE checkStyle, FileProvider provider) {
        Objects.requireNonNull(provider);
        Objects.requireNonNull(checkStyle);
        this.chunkSize = chunkSize > 0 ? chunkSize : DEFAULT_CHUNK_SIZE;
        this.checkStyle = checkStyle;
        this.provider = provider;
    }

    public RSyncClient buildClient(DiffDataHandler handler) {
        Objects.requireNonNull(handler);
        return new RSyncClient(provider, handler, chunkSize, checkStyle);
    }

    public RSyncServer buildServer(String destPath) {
        if (destPath.trim().isEmpty())
            throw new NullPointerException("dest path can't be null.");
        return new RSyncServer(destPath, provider, chunkSize, checkStyle).setPathMapper(pathMapper);
    }

    public RSync setPathMapper(PathMapper pathMapper) {
        this.pathMapper = pathMapper;
        return this;
    }

    public enum CHECK_STYLE {
        TIME_SIZE, MD5,
    }
}
