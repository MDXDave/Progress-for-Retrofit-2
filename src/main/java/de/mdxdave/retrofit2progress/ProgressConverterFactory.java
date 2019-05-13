package de.mdxdave.retrofit2progress;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import de.mdxdave.retrofit2progress.annotation.DownloadProgress;
import de.mdxdave.retrofit2progress.annotation.UploadProgress;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Converter factory that generates uuid for all used progress listeners.
 */
public final class ProgressConverterFactory extends Converter.Factory {

   private final ProgressListenerPool pool;

   public ProgressConverterFactory(ProgressListenerPool pool) {
      this.pool = pool;
   }

   @Override
   public Converter<ProgressListener, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
      for (Annotation annotation : annotations) {
         if (annotation instanceof DownloadProgress || annotation instanceof UploadProgress) {
            return pool::add;
         }
      }
      return null;
   }
}
