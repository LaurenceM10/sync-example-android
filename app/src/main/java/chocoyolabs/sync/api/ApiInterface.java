package chocoyolabs.sync.api;

import java.util.List;

import chocoyolabs.sync.models.ProductModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("products")
    Call<List<ProductModel>> productsAll();

}
