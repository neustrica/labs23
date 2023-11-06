package com.example.ipr2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<Product> adapter;
    private ArrayList<Product> products = new ArrayList<>();
    private DatabaseHelper databaseHelper;
    static int activePosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        listView = findViewById(R.id.listView);

        databaseHelper = new DatabaseHelper(this);
        databaseHelper.openDatabase();

        adapter = new ProductsAdapter(this);
        listView.setAdapter(adapter);
        products.addAll(databaseHelper.readDatabase());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                activePosition = position + 1;
                toChange();
            }
        });

        //databaseHelper.clearDb();
    }

    public void buttonAddClick(View view) {
        toChange();
    }

    public void toChange() {
        Intent intent = new Intent(this, ChangeActivity.class);
        startActivity(intent);
    }

    private class ProductsAdapter extends ArrayAdapter<Product> {

        public ProductsAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_1, products);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Product product = getItem(position);
            LayoutInflater inflater = getLayoutInflater();
            @SuppressLint({"ViewHolder", "InflateParams"}) View view = inflater.inflate(R.layout.row, null);
            TextView textViewName = view.findViewById(R.id.textViewName);
            TextView textViewType = view.findViewById(R.id.textViewType);
            TextView textViewCost = view.findViewById(R.id.textViewCost);
            textViewName.setText(product.getName());
            textViewType.setText(product.getType());
            textViewCost.setText(Integer.toString(product.getCost()));
            return view;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.closeDatabase();
    }
}
