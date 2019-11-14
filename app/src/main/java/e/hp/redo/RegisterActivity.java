package e.hp.redo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    TextInputLayout mDisplayName,mEmail,mPassword;
    Button mCreateButton;
    ProgressDialog mRegProgress;

    private FirebaseAuth mAuth;

    android.support.v7.widget.Toolbar register_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //input fields

        mDisplayName=findViewById(R.id.reg_display_name);
        mEmail=(TextInputLayout)findViewById(R.id.reg_email);
        mPassword=findViewById(R.id.reg_password);
        mCreateButton=findViewById(R.id.reg_create_button);
        mRegProgress=new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        //toolbar
        register_toolbar=findViewById(R.id.register_toolbar);
        setSupportActionBar(register_toolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String display_name=mDisplayName.getEditText().getText().toString().trim();
                String email=mEmail.getEditText().getText().toString().trim();
                String password=mPassword.getEditText().getText().toString().trim();
                if (!TextUtils.isEmpty(display_name)||!TextUtils.isEmpty(email)||!TextUtils.isEmpty(password)){
                    mRegProgress.setTitle("Registering User");
                    mRegProgress.setMessage("Please wait while we create your account");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();
                    register_user(display_name,email,password);
                }


            }
        });
    }
    void register_user(String display_name,String email,String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            mRegProgress.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent mainIntent=new Intent(RegisterActivity.this,BookingForm.class);
                            startActivity(mainIntent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            mRegProgress.hide();
                            FirebaseAuthException e=(FirebaseAuthException)task.getException();
                            Log.e("error kya h:",e.getMessage());
                            Toast.makeText(getApplicationContext(),"Cannot Sign in. Please check the form and try again.",Toast.LENGTH_LONG).show();
                        }

                        //also check email already exists or not error
                        //alos check internet connection problem

                        // ...
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
