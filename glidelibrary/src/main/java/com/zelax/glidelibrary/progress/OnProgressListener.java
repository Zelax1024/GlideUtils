package com.zelax.glidelibrary.progress;

/**
 * created by Zelax on 2021/2/4.
 */
public interface OnProgressListener {
    void onProgress(boolean isComplete, int percentage, long bytesRead, long totalBytes);
}
