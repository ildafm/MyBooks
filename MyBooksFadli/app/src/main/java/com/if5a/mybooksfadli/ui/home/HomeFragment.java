package com.if5a.mybooksfadli.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.if5a.mybooksfadli.activities.DetailActivity;
import com.if5a.mybooksfadli.adapters.BukuViewAdapter;
import com.if5a.mybooksfadli.databases.BukuHelper;
import com.if5a.mybooksfadli.databinding.FragmentHomeBinding;
import com.if5a.mybooksfadli.models.Buku;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private BukuViewAdapter bukuViewAdapter;
    private BukuHelper bukuHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        bukuHelper = new BukuHelper(getActivity());
        bukuViewAdapter = new BukuViewAdapter(this::onItemBukuClick);
        binding.rvBuku.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                hideKeyboard(getActivity());
            }
        });

        return root;
    }

    private void onItemBukuClick(Buku buku, int i){
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("EXTRA_BUKU", buku);
        startActivity(intent);
    }

    private void getAllData(){
        bukuHelper.open();
        ArrayList<Buku> bukus = bukuHelper.getAllDataBooks();
        bukuHelper.close();
        bukuViewAdapter.setData(bukus);
    }

    private void hideKeyboard(Context context){
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}