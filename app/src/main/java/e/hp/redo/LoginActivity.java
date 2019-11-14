package e.hp.redo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout mLoginEmail;
    TextInputLayout mLoginPassword;
    Button mLogin;
    ProgressDialog mLoginProgress;

    android.support.v7.widget.Toolbar login_toolbar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //input fields
        mLoginEmail=findViewById(R.id.login_email);
        mLoginPassword=findViewById(R.id.login_password);
        mLogin=findViewById(R.id.login_button);
        mLoginProgress=new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        //toolbar
        login_toolbar=findViewById(R.id.login_toolbar);
        setSupportActionBar(login_toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=mLoginEmail.getEditText().getText().toString().trim();
                String password=mLoginPassword.getEditText().getText().toString().trim();
                if (!TextUtils.isEmpty(email)||!TextUtils.isEmpty(password)){
                    mLoginProgress.setTitle("Logging in");
                    mLoginProgress.setMessage("Please wait while we check your credentials.");
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();
                    loginUser(email,password);
                }
            }
        });
    }

    private void loginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    mLoginProgress.dismiss();

                    Intent intent=new Intent(LoginActivity.this,BookingForm.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    mLoginProgress.hide();
                    FirebaseAuthException e=(FirebaseAuthException)task.getException();
                    Log.e("error kya h:",e.getMessage());
                    Toast.makeText(getApplicationContext(),"Cannot Sign in. Please check the form and try again.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
