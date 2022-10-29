package com.if5a.mybooksfadli.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.if5a.mybooksfadli.R;
import com.if5a.mybooksfadli.databinding.ActivityDetailBinding;
import com.if5a.mybooksfadli.models.Buku;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Buku buku = getIntent().getParcelableExtra("EXTRA_BUKU");

        Glide.with(DetailActivity.this)
                .load(buku.getImage_url_l())
                .into(binding.ivImage);

        binding.tvTitle.setText(buku.getTitle());
        binding.tvIsbn.setText("ISBN : " + buku.getIsbn());
        binding.tvAuthor.setText("Author : " + buku.getAuthor());
        binding.tvYearOfPublication.setText("Year of Publication : " + buku.getYearOfPublication());
        binding.tvPublisher.setText("Publisher : " + buku.getPublisher());
    }
}