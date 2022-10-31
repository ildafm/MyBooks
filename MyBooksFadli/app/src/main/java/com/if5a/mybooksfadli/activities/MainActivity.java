package com.if5a.mybooksfadli.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.if5a.mybooksfadli.adapters.BukuViewAdapter;
import com.if5a.mybooksfadli.databases.BukuHelper;
import com.if5a.mybooksfadli.databinding.ActivityMainBinding;
import com.if5a.mybooksfadli.models.Buku;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private BukuViewAdapter bukuViewAdapter;
    private BukuHelper bukuHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bukuHelper = new BukuHelper(MainActivity.this);
        bukuViewAdapter = new BukuViewAdapter(this::onItemBukuClick);
        binding.rvBuku.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        binding.rvBuku.setAdapter(bukuViewAdapter);

        getAllData();

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strSearch = binding.etSearch.getText().toString();

                if (TextUtils.isEmpty(strSearch)){
                    getAllData();
                } else{
                    bukuHelper.open();
                    ArrayList<Buku> bukus = bukuHelper.getAllDataBooksByTitle(strSearch);
                    bukuHelper.close();
                    bukuViewAdapter.setData(bukus);
                }
                hideKeyboard(MainActivity.this);
            }
        });
    }

    private void onItemBukuClick(Buku buku, int i){
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("EXTRA_BUKU", buku);
        startActivity(intent);
    }

    private void getAllData(){
        bukuHelper.open();
        ArrayList<Buku> bukus = bukuHelper.getAllDataBooks();
        bukuHelper.close();//ini digunakan untuk menutup database di app Inspection
        bukuViewAdapter.setData(bukus);
    }

    private void hideKeyboard(Context context){
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

}