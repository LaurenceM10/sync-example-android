package chocoyolabs.sync.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import chocoyolabs.sync.R;
import chocoyolabs.sync.api.Api;
import chocoyolabs.sync.models.ProductModel;
import chocoyolabs.sync.ui.adapters.ProductsAdapter;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        fetchProducts();
        getFromDataBase();
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)

    }

    private void fetchProducts() {
        Call<List<ProductModel>> call = Api.instance().productsAll();
        call.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                mAdapter = new ProductsAdapter(response.body());
                mRecyclerView.setAdapter(mAdapter);

                sync(response.body());
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {

            }
        });
    }

    private void sync(List<ProductModel> productModels) {
        for(ProductModel productModel : productModels) {
            store(productModel);
        }
    }

    private void store(ProductModel productModelFromApi) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        ProductModel productModel = realm.createObject(ProductModel.class); // Create a new object

        productModel.setSku(productModelFromApi.getSku());
        productModel.setName(productModelFromApi.getName());
        productModel.setPrice(productModelFromApi.getPrice());

        realm.commitTransaction();
    }

    private void getFromDataBase() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ProductModel> query = realm.where(ProductModel.class);

        RealmResults<ProductModel> results = query.findAll();

        mAdapter = new ProductsAdapter(results);
        mRecyclerView.setAdapter(mAdapter);
    }
}
