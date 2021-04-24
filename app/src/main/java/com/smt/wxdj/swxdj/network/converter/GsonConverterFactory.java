package com.smt.wxdj.swxdj.network.converter;


import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 提供ResponseBody的转换和RequestBody的转换
 *
 * @author guobihai
 * @date 2019/03/14
 */
public final class GsonConverterFactory extends Converter.Factory {


    public static GsonConverterFactory create() {
        return new GsonConverterFactory(new Gson());
    }

    private final Gson gson;

    private GsonConverterFactory(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
//        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
//        return new GsonResponseBodyConverter<>(gson, adapter);
        try {
            if(type == String.class){
                return  new StringResponseConerter();
            }

            return  retrofit2.converter.gson.GsonConverterFactory.create(gson).responseBodyConverter(type,annotations,retrofit);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
//        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return    retrofit2.converter.gson.GsonConverterFactory.create(gson).requestBodyConverter(type,parameterAnnotations,methodAnnotations,retrofit);
    }

    private static class StringResponseConerter implements Converter<ResponseBody,String>{

        @Override
        public String convert(ResponseBody value) throws IOException {
            return value.toString();
        }
    }
}
