package com.android.warehousemanager.api;

import com.android.warehousemanager.models.ApiResponse;
import com.android.warehousemanager.models.ChiTietPhieuNhap;
import com.android.warehousemanager.models.Kho;
import com.android.warehousemanager.models.PhieuNhap;
import com.android.warehousemanager.models.Supply;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    Gson GSON = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .build();
    ApiService API_SERVICE = new Retrofit.Builder()
            .baseUrl("http://quanlykho.somee.com/api/")
            .addConverterFactory(GsonConverterFactory.create(GSON))
            .client(okHttpClient)
            .build()
            .create(ApiService.class);

    @GET("VatTu")
    Call<List<Supply>> getAllVatTu();
    @PUT("VatTu")
    Call<Supply> updateVatTu(@Body Supply supply);
    @POST("VatTu")
    Call<Supply> createVatTu(@Body Supply supply);
    @DELETE("VatTu/{id}")
    Call<Supply> removeVatTu(@Path("id") String maVatTu);
    @GET("VatTu/spinner")
    Call<List<String>> getVatTuSpinner();

    @GET("Kho")
    Call<List<Kho>> getAllKho();
    @PUT("Kho")
    Call<Kho> updateKho(@Body Kho kho);
    @POST("Kho")
    Call<Kho> createKho(@Body Kho kho);
    @DELETE("Kho/{id}")
    Call<Kho> removeKho(@Path("id") String maKho);
    @GET("Kho/spinner")
    Call<List<String>> getKhoSpinner();

    @GET("PhieuNhap")
    Call<List<PhieuNhap>> getAllPhieuNhap();
    @PUT("PhieuNhap")
    Call<PhieuNhap> updatePhieuNhap(@Query("soPhieu") int soPhieu, @Body PhieuNhap phieuNhap);
    @POST("PhieuNhap")
    Call<ApiResponse> createPhieuNhap(@Body PhieuNhap phieuNhap);
    @DELETE("PhieuNhap/{id}")
    Call<PhieuNhap> removePhieuNhap(@Path("id") int soPhieu);

    @GET("ChiTietPhieuNhap/{soPhieu}")
    Call<List<ChiTietPhieuNhap>> getAllChiTietPhieuNhap(@Path("soPhieu") int soPhieu);
    @POST("ChiTietPhieuNhap")
    Call<ChiTietPhieuNhap> createChiTietPhieuNhap(@Body ChiTietPhieuNhap chiTietPhieuNhap);
    @PUT("ChiTietPhieuNhap")
    Call<ChiTietPhieuNhap> updateChiTietPhieuNhap(@Body ChiTietPhieuNhap chiTietPhieuNhap);
    @DELETE("ChiTietPhieuNhap/{soPhieu}/{maVatTu}")
    Call<ChiTietPhieuNhap> removeChiTietPhieuNhap(@Path("soPhieu") int soPhieu, @Path("maVatTu") String maVatTu);

    @Multipart
    @POST("Cloudinary")
    Call<ApiResponse> uploadImage(@Part MultipartBody.Part file);

    @POST("Email/SendMail")
    Call<ApiResponse> sendEmail(@Query("subject") String subject, @Query("content") String content);
}
