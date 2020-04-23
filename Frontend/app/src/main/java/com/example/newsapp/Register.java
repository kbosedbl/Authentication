package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsapp.Retrofit.IMyService;

import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class Register extends AppCompatActivity {
    private EditText email , password , name;
    private Button register;
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
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        name = findViewById(R.id.name);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(email.getText().toString(),password.getText().toString(),name.getText().toString());
            }
        });
    }

    private void registerUser(String email, String password, String name) {
            if (email.length() == 0) {
                Toast.makeText(this, "Please enter the email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.length() == 0) {
                Toast.makeText(this, "Please enter the password", Toast.LENGTH_SHORT).show();
                return;
            }
            if (name.length() == 0) {
                Toast.makeText(this, "Please enter the password", Toast.LENGTH_SHORT).show();
                return;
            }
            compositeDisposable.add(iMyService.registerUser(email, name, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String response) throws Exception {
                            Toast.makeText(Register.this, "" + response, Toast.LENGTH_SHORT).show();
                        }
                    }));

    }
}
