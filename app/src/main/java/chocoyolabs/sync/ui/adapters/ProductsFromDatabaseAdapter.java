package chocoyolabs.sync.ui.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import chocoyolabs.sync.R;
import chocoyolabs.sync.models.ProductModel;
import io.realm.Realm;
import io.realm.RealmResults;

public class ProductsFromDatabaseAdapter extends RecyclerView.Adapter<ProductsFromDatabaseAdapter.ViewHolder> {
    private RealmResults<ProductModel> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView sku;
        public TextView name;
        public TextView price;
        public LinearLayout item;
        public Context context;
        public ViewHolder(View view, Context _context) {
            super(view);
            sku = view.findViewById(R.id.sku);
            name = view.findViewById(R.id.name);
            price = view.findViewById(R.id.price);
            item = view.findViewById(R.id.item);
            context = _context;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProductsFromDatabaseAdapter(RealmResults<ProductModel> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProductsFromDatabaseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ProductsFromDatabaseAdapter.ViewHolder(view, parent.getContext());
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ProductsFromDatabaseAdapter.ViewHolder holder, final int position) {
        final ProductModel productModel = mDataset.get(position);

        Log.i("sku", productModel.getSku());
        Log.i("name", productModel.getName());
        Log.i("price", String.valueOf(productModel.getPrice()));

        holder.sku.setText(productModel.getSku());
        holder.name.setText(productModel.getName());
        holder.price.setText(String.valueOf(productModel.getPrice()));


        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new MaterialDialog.Builder(holder.context)
                        .content("Desea borrar este registro.")
                        .positiveText("Borrar")
                        .negativeText("No")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                remoteItemFromDatabase(mDataset.get(position));
                            }
                        })
                        .show();
                return true;
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void remoteItemFromDatabase(ProductModel productModel) {
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        productModel.deleteFromRealm();
        realm.commitTransaction();
    }
}
