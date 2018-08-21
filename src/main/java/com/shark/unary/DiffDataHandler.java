package com.shark.unary;

/**
 * 当源端每匹配到一个数据块时，将会把匹配的数据块和当前数据块之前的差异数据
 * 传入 handler() 中。当文件对比完成时，会调用 finish() 方法。
 *
 * @author Shark.Yin
 */
public interface DiffDataHandler {

    /**
     * 设置数据块大小
     *
     * @param chunkSize 数据块大小
     */
    void setChunkSize(int chunkSize);

    /**
     * 用于处理相同的数据块
     *
     * @param identification 文件唯一标识符
     * @param offset         偏移量
     * @param diffData       差异数据，可以为空数组，标识匹配到了连续的数据块
     * @param chunkIndex     数据块序号,当值为-1 时,表示同步完成
     */
    void handler(String identification, long offset, byte[] diffData, int chunkIndex);

    /**
     * 某一个文件已经对比完成
     *
     * @param identification 文件唯一标识符
     */
    void finish(String identification);

    /**
     * 获取文件提供者
     *
     * @param provider 文件遍历提供类
     */
    void setFileProvider(FileProvider provider);
}
