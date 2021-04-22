package com.smt.wxdj.swxdj.network.interceptor;



import android.text.TextUtils;


import com.smt.wxdj.swxdj.network.account.AccountManager;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * token拦截器，用于请求时添加token请求头，以及保存token响应头
 *
 * @Author guobihai
 * @Created 4/20/19
 * @Editor zrh
 * @Edited 5/11/19
 * @Type
 * @Layout
 * @Api
 */
public class TokenInterceptor implements Interceptor {


    //tokneROM
    private static final String TOKEN = "token";
    private static final String FROM = "from";

    private String tenant = "39f967cb-0049-d2cd-7099-3bacf49973f9";

    @SuppressWarnings("NullableProblems")
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();

        // 如果本地存在token，则添加token请求头
//        if (AccountManager.getTokenInfo() != null) {
////            requestBuilder.header(TOKEN, AccountManager.getToken());
//            requestBuilder.header("Authorization","bearer ".concat(AccountManager.getTokenInfo()));
//            requestBuilder.header("__tenant", tenant);
//        }


        if(!TextUtils.isEmpty(AccountManager.getTokenInfo())){
            //"Authorization", "bearer ".concat(token)
            requestBuilder.header("Authorization","bearer ".concat(AccountManager.getTokenInfo()));
            requestBuilder.header("Accept-Language", "zh-Hans");
            requestBuilder.header("content-type", "application/json; charset=utf-8");
            requestBuilder.header("culture", "zh-Hans");
            requestBuilder.header("__tenant", tenant);
        }


        // 如果响应头中存在token则更新本地token
        Response response = chain.proceed(requestBuilder.build());
        Headers headers = response.headers();
        String token = headers.get(TOKEN);
        if (token != null) {
            AccountManager.saveToken(token);
        }
        return response;
    }
}
