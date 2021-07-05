package com.qzl.base_common.api.retrofit_converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class IGsonFactory extends Converter.Factory {
    public static IGsonFactory create() {
        return create(new GsonBuilder().setLenient().create());
    }

    public static IGsonFactory create(Gson gson) {
        if (gson == null)
            throw new NullPointerException("gson == null");
        return new IGsonFactory(gson);
    }

    private final Gson gson;

    private IGsonFactory(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type,
                                                            Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new IResponseBodyConverter<>(gson, adapter); // 响应
    }
}
