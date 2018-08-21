package com.shark.unary;

import java.io.File;
import java.io.FileFilter;

public interface FileProvider {
    /**
     * 获取当前文件的唯一标识符
     *
     * @return 当前文件的唯一标识符
     */
    String getIdentification();

    /**
     * 获取下一个文件实体
     * @return 下一个文件实体，无成员时，返回 Null 值
     */
    File next();

    /**
     * 根据文件唯一标识符来获取文件实体
     *
     * @param identification 文件唯一标识符
     * @return 文件实体
     */
    File get(String identification);

    /**
     * 重置当前文件遍历器
     */
    void reset();

    /**
     * 设置文件过滤器，不符合条件的文件将不出现在遍历结果中
     *
     * @param fileFilter 文件过滤器
     */
    void setFileFilter (FileFilter fileFilter);
}
