package com.qubuss.todotransfer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailED;
    private EditText passED;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailED = (EditText) findViewById(R.id.emailLoginED);
        passED = (EditText) findViewById(R.id.passLoginED);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loguje...");
        progressDialog.setCancelable(false);

    }

    public void goToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        onStop();
    }

    public void handleLogin(View view) {
        progressDialog.show();
        String email = emailED.getText().toString().trim();
        String password = passED.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(this.getClass().toString(), "signInWithEmail:onComplete:" + task.isSuccessful());

                if (!task.isSuccessful()) {
                    Log.w(this.getClass().toString(), "signInWithEmail", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    onStop();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        progressDialog.hide();
    }
}
