package com.if5a.mybooksfadli.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.if5a.mybooksfadli.R;
import com.if5a.mybooksfadli.databases.BukuHelper;
import com.if5a.mybooksfadli.databinding.ActivitySplashBinding;
import com.if5a.mybooksfadli.models.Buku;
import com.if5a.mybooksfadli.utilities.AppPreference;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new LoadData().execute();
    }

    //<sebelum, berjalan, selesai>
    private class LoadData extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadData.class.getSimpleName();

        BukuHelper bukuHelper;
        AppPreference appPreference;

        double progress = 0;
        double maxprogress = 100;

        //sebelum proses
        @Override
        protected void onPreExecute() {
            bukuHelper = new BukuHelper(SplashActivity.this);
            appPreference = new AppPreference(SplashActivity.this);
        }

        protected Void doInBackground(Void... voids){
            Boolean firstRun = appPreference.getFirstRun();

            //jika sudah pernah dijalankan app itu
            if(firstRun){
                ArrayList<Buku> bukus = preLoadBooks();
                bukuHelper.open();

                progress = 0;

                publishProgress((int) progress);
                Double progressMaxInsert = 100.0;
                Double progressDiff = (progressMaxInsert - progress) / (bukus.size());

                bukuHelper.beginTransaction();
                try {
                    for(Buku buku : bukus){
                        bukuHelper.insertDataBooks(buku);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                    bukuHelper.setTransactionSuccess();
                } catch (Exception e) {
                    Log.e(TAG, "doInBackground: Exception " + e.getMessage());
                }
                bukuHelper.endTransaction();

                bukuHelper.close();
                appPreference.setFirstRun(false);
                publishProgress((int) maxprogress);
            } else {
                try {
                    synchronized (this){
                        this.wait(1000);
                        for (int i = 60; i > 0; i--){
                            publishProgress(((int) maxprogress)/i);
                            this.wait(50);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        //ketika kita ingin publis progress, akan lanjut ke method ini
        //update progress terjadi disini
        protected void onProgressUpdate(Integer... values){ // ... berarti array
            binding.progressBar.setProgress(values[0]);
            binding.tvLoading.setText("App Loading " + values[0] + "%");
        }

        //ketika sudah selesai semua, dan kita harus intent
        @Override
        protected void onPostExecute(Void unused) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    //menarik data dari data raw dan diletakan kedalam array
    public ArrayList<Buku> preLoadBooks(){
        ArrayList<Buku> bukus = new ArrayList<>();
        String line = null;
        BufferedReader reader;

        try {
            Resources res = getResources();
            InputStream raw_book = res.openRawResource(R.raw.books);

            reader = new BufferedReader(new InputStreamReader(raw_book));

            do {
                line = reader.readLine();
                String[] splitstr = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                Buku buku;
                buku = new Buku(
                        splitstr[0], splitstr[1], splitstr[2],
                        splitstr[3], splitstr[4], splitstr[5],
                        splitstr[6], splitstr[7]
                );
                bukus.add(buku);

            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bukus;
    }
}