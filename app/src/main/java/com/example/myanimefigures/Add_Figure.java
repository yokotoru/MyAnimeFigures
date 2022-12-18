package com.example.myanimefigures;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Add_Figure extends AppCompatActivity implements View.OnClickListener {


    EditText anime;
    EditText name;
    EditText status;

    String nPicture;
    ImageView picture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_figure);

        anime = findViewById(R.id.edtDrink);
        name = findViewById(R.id.edtPrice);
        status = findViewById(R.id.Status_fig);
        picture = findViewById(R.id.ImgBut);

        Button buttonChange = findViewById(R.id.Add);
        buttonChange.setOnClickListener(this);
        picture.setOnClickListener(this);

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.Status_fig);

        String[] stat = getResources().getStringArray(R.array.status);
        List<String> statList = Arrays.asList(stat);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, statList);
        autoCompleteTextView.setAdapter(adapter);
    }

    public void onNumberClick(View view){

        status.setText("");
        Button button = (Button)view;
        status.append(button.getText());

    }

    public final ActivityResultLauncher<Intent> pickImg = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null) {
                        Uri uri = result.getData().getData();
                        try {
                            InputStream is = getContentResolver().openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeStream(is);
                            picture.setImageBitmap(bitmap);
                            nPicture = encodeImage(bitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    public void onClick2(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }


    private void postCreate(String Anime, String Name, String Status, String Image) {


        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://ngknn.ru:5001/NGKNN/кузнецоваон/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
            DataModal modal = new DataModal(Anime, Name, Status, Image);
            Call<DataModal> call = retrofitAPI.createPost(modal);
            call.enqueue(new Callback<DataModal>() {
                @Override
                public void onResponse(Call<DataModal> call, Response<DataModal> response) {

                    if (!response.isSuccessful()) {
                        Toast.makeText(Add_Figure.this, "Во время добавления что-то пошло не по плану. Мы уже разбираемся", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(Add_Figure.this, "Товар успешно добавлен", Toast.LENGTH_SHORT).show();
                    anime.setText("");
                    name.setText("");
                    status.setText("");



                }

                @Override
                public void onFailure(Call<DataModal> call, Throwable t) {
                    Toast.makeText(Add_Figure.this, "Еще секундочку....: " + t.getMessage(), Toast.LENGTH_LONG).show();


                }
            });
        }
        catch (Exception e) {
            Toast.makeText(Add_Figure.this, "Что-то пошло не так в процессе добавления: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    public static String encodeImage(Bitmap bitmap) {
        int prevW = 500;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();

        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return java.util.Base64.getEncoder().encodeToString(bytes);
        }
        return "";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Add: {

                if (anime.getText().length() == 0 || name.getText().length() == 0 || status.getText().length() == 0 ) {
                    Toast.makeText(this, "Возможно одно или несколько полей были незаполнены", Toast.LENGTH_LONG).show();
                    return;
                }
                String Anime = anime.getText().toString();
                String Name = name.getText().toString();
                String Status = status.getText().toString();

                postCreate(Anime, Name, Status, nPicture);
                new Handler().postDelayed(() -> startActivity(
                        new Intent(Add_Figure.this, MainActivity.class)), 1000);


            }
            break;

            case R.id.ImgBut:

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                pickImg.launch(intent);
                break;

        }
    }


}