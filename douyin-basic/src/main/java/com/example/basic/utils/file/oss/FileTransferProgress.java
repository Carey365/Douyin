package com.example.basic.utils.file.oss;

public interface FileTransferProgress {

    /**
     * 文件传输进度
     * @param curBytes 当前字节数
     * @param totalBytes 总字节数
     * @param ratio 百分比
     */
    public void onProgress(long curBytes, long totalBytes, int ratio);

}
