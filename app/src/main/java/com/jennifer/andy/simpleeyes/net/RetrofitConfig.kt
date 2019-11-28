package com.jennifer.andy.simpleeyes.net

import com.google.gson.GsonBuilder
import com.jennifer.andy.simpleeyes.AndyApplication
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * Author:  andy.xwt
 * Date:    2017/10/10 22:41
 * Description:
 */
class RetrofitConfig private constructor() {


    private lateinit var mRetrofit: Retrofit
    private lateinit var mHttpLoggingInterceptor: HttpLoggingInterceptor
    private lateinit var mRequestInterceptor: Interceptor

    private lateinit var mOkHttpClient: OkHttpClient
    private lateinit var mCache: Cache

    companion object {

        private const val READ_TIME_OUT = 5000L// 读取超过时间 单位:毫秒
        private const val WRITE_TIME_OUT = 5000L//写超过时间 单位:毫秒
        private const val FILE_CACHE_SIZE = 1024 * 1024 * 100L//缓存大小100Mb
        private val Instance: RetrofitConfig by lazy { RetrofitConfig() }

        /**
         * 获取默认请求接口
         */
        @JvmStatic
        fun getDefaultService(): ApiService {
            return Instance.apiService
        }

    }


    private lateinit var apiService: ApiService

    init {
        initRequestInterceptor()
        initLoggingInterceptor()
        initCachePathAndSize()
        initOkHttpClient()
        initRetrofit()
    }

    /**
     * 初始化请求拦截，添加缓存头,与全局请求参数
     */
    private fun initRequestInterceptor() {
        mRequestInterceptor = Interceptor { chain ->
            //注意全局请求参数都是死的
            val urlBuilder = chain.request().url
                    .newBuilder()
                    .setEncodedQueryParameter("udid", "d0f6190461864a3a978bdbcb3fe9b48709f1f390")
                    .setEncodedQueryParameter("vc", "225")
                    .setEncodedQueryParameter("vn", "3.12.0")
                    .setEncodedQueryParameter("deviceModel", "Redmi%204")
                    .setEncodedQueryParameter("first_channel", "eyepetizer_xiaomi_market")
                    .setEncodedQueryParameter("last_channel", "eyepetizer_xiaomi_market")
                    .setEncodedQueryParameter("system_version_code", "23")

            val request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Cookie", "ky_auth=;sdk=23")
                    .addHeader("model", "Android")
                    .url(urlBuilder.build())
                    .build()

            chain.proceed(request)
        }
    }

    /**
     * 初始化日志拦截
     */
    private fun initLoggingInterceptor() {
        mHttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    /**
     * 配置缓存大小与缓存地址
     */
    private fun initCachePathAndSize() {
        val cacheFile = File(AndyApplication.INSTANCE.cacheDir, "cache")
        mCache = Cache(cacheFile, FILE_CACHE_SIZE)
    }


    /**
     * 配置okHttp
     */
    private fun initOkHttpClient() {
        mOkHttpClient = OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.MILLISECONDS)
                .cache(mCache)
                .addInterceptor(mRequestInterceptor)
                .addInterceptor(mHttpLoggingInterceptor)
                .build()
    }

    /**
     * 配置retrofit
     */
    private fun initRetrofit() {
        val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create()
        mRetrofit = Retrofit.Builder()
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Api.BASE_URL)
                .build()
        apiService = mRetrofit.create(ApiService::class.java)

    }


}
