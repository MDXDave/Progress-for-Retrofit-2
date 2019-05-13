# Up/Download progress listener for Retrofit 2
Provides simple progress listener implementation for Retrofit 2

[![](https://jitpack.io/v/MDXDave/retrofit2progress.svg?style=flat-square)](https://jitpack.io/#MDXDave/retrofit2progress)

## Gradle:

Add it in your root build.gradle at the end of repositories:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Afterwards add the dependency in your apps build.gradle:
```
compile 'de.mdxdave:retrofit2progress:1.0.0'
```

Create pool:
```java
ProgressListenerPool pool = new ProgressListenerPool();
```

Add interceptor and converter:
```java
Retrofit retrofit = new Retrofit.Builder()
    .client(new OkHttpClient.Builder().addInterceptor(new ProgressInterceptor(pool)).build())
    .addConverterFactory(new ProgressConverterFactory(pool))
    ...
    .build();
```

Sample Webservice interface:
```java
public interface WS {

   @GET("/sth")
   Observable<Object> getSomething(@DownloadProgress @Header(DownloadProgress.HEADER) ProgressListener listener);

   @POST("/sth")
   Observable<Object> setSomething(@Body String veryLongString, @UploadProgress @Header(UploadProgress.HEADER) ProgressListener listener);
}
```
