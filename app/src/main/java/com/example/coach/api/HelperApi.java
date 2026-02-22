package com.example.coach.api;

import android.util.Log;

import com.example.coach.model.Profil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelperApi {
    private static final IRequestApi api = CoachApi.getRetrofit().create(IRequestApi.class);

    public static IRequestApi getApi() {
        return api;
    }

    public static <T> void call(Call<ResponseApi<T>> call, ICallbackApi<T> callback){
        call.enqueue(new Callback<ResponseApi<T>>() {
            @Override
            public void onResponse(Call<ResponseApi<T>> call, Response<ResponseApi<T>> response) {
                Log.d("API", "code : " + response.body().getCode() +
                        " message : " + response.body().getMessage() +
                        " result : " + response.body().getResult()
                );
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getResult());
                } else {
                    callback.onError();
                    Log.e("API", "Erreur API: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseApi<T>> call, Throwable throwable) {
                callback.onError();
                Log.e("API", "Erreur API", throwable);
            }
        });
    }
}
