package e.hp.redo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import javax.security.auth.login.LoginException;

public class StartOption extends AppCompatActivity {
    Button reg_btn,login_btn;

    android.support.v7.widget.Toolbar options_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_option);

        //input Fields
        reg_btn=findViewById(R.id.reg_btn);
        login_btn=findViewById(R.id.login_btn);

        //toolbar
        options_toolbar=findViewById(R.id.options_toolbar);
        setSupportActionBar(options_toolbar);
        getSupportActionBar().setTitle("Easy Car App");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent=new Intent(StartOption.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent=new Intent(StartOption.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });
    }


}
