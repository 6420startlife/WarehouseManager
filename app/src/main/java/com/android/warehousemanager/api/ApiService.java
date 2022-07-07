package com.android.warehousemanager.api;

import com.android.warehousemanager.models.ApiResponse;
import com.android.warehousemanager.models.DetailGoodsReceipt;
import com.android.warehousemanager.models.Storage;
import com.android.warehousemanager.models.GoodsReceipt;
import com.android.warehousemanager.models.Supplies;
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
    Call<List<Supplies>> getAllVatTu();
    @PUT("VatTu")
    Call<Supplies> updateVatTu(@Body Supplies supplies);
    @POST("VatTu")
    Call<Supplies> createVatTu(@Body Supplies supplies);
    @DELETE("VatTu/{id}")
    Call<Supplies> removeVatTu(@Path("id") String maVatTu);
    @GET("VatTu/spinner")
    Call<List<String>> getSuppliesSpinner();

    @GET("Kho")
    Call<List<Storage>> getAllKho();
    @PUT("Kho")
    Call<Storage> updateKho(@Body Storage storage);
    @POST("Kho")
    Call<Storage> createKho(@Body Storage storage);
    @DELETE("Kho/{id}")
    Call<Storage> removeKho(@Path("id") String maKho);
    @GET("Kho/spinner")
    Call<List<String>> getStorageSpinner();

    @GET("PhieuNhap")
    Call<List<GoodsReceipt>> getAllPhieuNhap();
    @PUT("PhieuNhap")
    Call<GoodsReceipt> updatePhieuNhap(@Query("soPhieu") int soPhieu, @Body GoodsReceipt phieuNhap);
    @POST("PhieuNhap")
    Call<ApiResponse> createPhieuNhap(@Body GoodsReceipt phieuNhap);
    @DELETE("PhieuNhap/{id}")
    Call<GoodsReceipt> removePhieuNhap(@Path("id") int soPhieu);

    @GET("ChiTietPhieuNhap/{soPhieu}")
    Call<List<DetailGoodsReceipt>> getAllChiTietPhieuNhap(@Path("soPhieu") int soPhieu);
    @POST("ChiTietPhieuNhap")
    Call<DetailGoodsReceipt> createChiTietPhieuNhap(@Body DetailGoodsReceipt chiTietPhieuNhap);
    @PUT("ChiTietPhieuNhap")
    Call<DetailGoodsReceipt> updateChiTietPhieuNhap(@Body DetailGoodsReceipt chiTietPhieuNhap);
    @DELETE("ChiTietPhieuNhap/{soPhieu}/{maVatTu}")
    Call<DetailGoodsReceipt> removeChiTietPhieuNhap(@Path("soPhieu") int soPhieu, @Path("maVatTu") String maVatTu);

    @Multipart
    @POST("Cloudinary")
    Call<ApiResponse> uploadImage(@Part MultipartBody.Part file);

    @POST("Email/SendMail")
    Call<ApiResponse> sendEmail(@Query("subject") String subject, @Query("content") String content);
}
