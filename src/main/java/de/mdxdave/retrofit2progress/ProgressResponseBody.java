package de.mdxdave.retrofit2progress;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Response body implementation with progress listener
 */
final class ProgressResponseBody extends ResponseBody {

   private final ResponseBody responseBody;

   private final ProgressListener progressListener;

   private BufferedSource bufferedSource;

   public ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
      this.responseBody = responseBody;
      this.progressListener = progressListener;
   }

   @Override
   public MediaType contentType() {
      return responseBody.contentType();
   }

   @Override
   public long contentLength() {
      return responseBody.contentLength();
   }

   @Override
   public BufferedSource source() {
      if (bufferedSource == null) {
         bufferedSource = Okio.buffer(source(responseBody.source()));
      }
      return bufferedSource;
   }

   private Source source(Source source) {
      return new ForwardingSource(source) {
         long totalBytesRead = 0L;

         @Override
         public long read(Buffer sink, long byteCount) throws IOException {
            long bytesRead = super.read(sink, byteCount);
            // read() returns the number of bytes read, or -1 if this source is exhausted.
            totalBytesRead += bytesRead != -1 ? bytesRead : 0;
            progressListener.update(totalBytesRead, responseBody.contentLength());
            return bytesRead;
         }
      };
   }
}
