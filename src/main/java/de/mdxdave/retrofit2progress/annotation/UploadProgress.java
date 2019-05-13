package de.mdxdave.retrofit2progress.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface UploadProgress {
   String HEADER = "X-UProgress";
}
