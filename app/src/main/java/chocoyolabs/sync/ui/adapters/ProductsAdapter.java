package chocoyolabs.sync.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import chocoyolabs.sync.R;
import chocoyolabs.sync.models.ProductModel;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    private List<ProductModel> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView sku;
        public TextView name;
        public TextView price;
        public ViewHolder(View view) {
            super(view);
            sku = view.findViewById(R.id.sku);
            name = view.findViewById(R.id.name);
            price = view.findViewById(R.id.price);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProductsAdapter(List<ProductModel> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProductModel productModel = mDataset.get(position);

        Log.i("sku", productModel.getSku());
        Log.i("name", productModel.getName());
        Log.i("price", String.valueOf(productModel.getPrice()));

        holder.sku.setText(productModel.getSku());
        holder.name.setText(productModel.getName());
        holder.price.setText(String.valueOf(productModel.getPrice()));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

