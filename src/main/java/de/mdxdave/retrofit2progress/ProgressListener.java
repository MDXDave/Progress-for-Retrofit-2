package de.mdxdave.retrofit2progress;

public interface ProgressListener {
   void update(long bytesRead, long contentLength);
}
