package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsapp.Retrofit.IMyService;
import com.example.newsapp.Retrofit.RetrofitClient;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private  boolean mIsShowPass = false;
    private EditText email , password;
    private TextView register;
    private Button signin;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.signin_to_register);
        signin = findViewById(R.id.signin);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Register.class));
            }
        });
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(email.getText().toString(),password.getText().toString());
            }
        });
    }

    private void loginUser(String email, String password) {

            if (email.length() == 0) {
                Toast.makeText(this, "Please enter the email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.length() == 0) {
                Toast.makeText(this, "Please enter the password", Toast.LENGTH_SHORT).show();
                return;
            }
            compositeDisposable.add(iMyService.loginUser(email, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String response) throws Exception {
                            Toast.makeText(MainActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                        }
                    }));

    }
}
