package com.if5a.mybooksfadli.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
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
//                    e.printStackTrace();
                    Log.e(TAG, "doInBachground: Exception " + e.getMessage());
                }
                bukuHelper.endTransaction();

                bukuHelper.close();
                appPreference.setFirstRun(false);
                publishProgress((int) maxprogress);
            } else {
                try {
                    synchronized (this){
                        this.wait(1000);
                        publishProgress(50);

                        this.wait(1000);
                        publishProgress((int) maxprogress);
                    }
                } catch (InterruptedException e) {

                }
            }
            return null;
        }

        //ketika kita ingin publis progress, akan lanjut ke method ini
        //update progress terjadi disini
        protected void onProgressUpdate(Integer... values){ // ... berarti array
            binding.progressBar.setProgress(values[0]);
            binding.tvLoading.setText("Loading " + values[0] + "% ...");
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
//    public ArrayList<Buku> preLoadBooks(){
//        ArrayList<Buku> bukus = new ArrayList<>();
//        String line = null;
//        BufferedReader reader;
//
//        //jika terdapat null akan force close jika tidak menggunakan try catch
//        try {
//            Resources res = getResources();
//            InputStream raw_book = res.openRawResource(R.raw.books);
//
//            reader = new BufferedReader(new InputStreamReader(raw_book));
//            int count = 0;
//            do{
//                line = reader.readLine();
//                String[] splitstr = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)\"", -1);
//
//                Buku buku;
//                buku = new Buku(splitstr[0], splitstr[1], splitstr[2], splitstr[3], splitstr[4], splitstr[5], splitstr[6], splitstr[7]);
//                bukus.add(buku);
//            }while (line != null);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bukus;
//    }

    public ArrayList<Buku> preLoadBooks(){
        ArrayList<Buku> bukus = new ArrayList<>();
        ArrayList<Integer> i = new ArrayList<>();
        ArrayList<String> lines = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            getResources().openRawResource(R.raw.books)
                    )
            );

            bufferedReader.readLine(); // skip line 1

            String line = null;

            int count = 1;

            do {
                line = bufferedReader.readLine();
                String[] splitted = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                try{
                    Buku buku = new Buku();
                    buku.setIsbn(splitted[0]);
                    buku.setTitle(splitted[1]);
                    buku.setYearOfPublication(splitted[2]);
                    buku.setYearOfPublication(splitted[3]);
                    buku.setPublisher(splitted[4]);
                    buku.setImage_url_s(splitted[5]);
                    buku.setImage_url_m(splitted[6]);
                    buku.setImage_url_l(splitted[7]);

                    bukus.add(buku);
                }catch (NumberFormatException e){
                    i.add(count);
                    lines.add(line);
                }

                count++;
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("errors", "preloadRawBooks: " + i + "\n" + lines);

        return bukus;
    }
}