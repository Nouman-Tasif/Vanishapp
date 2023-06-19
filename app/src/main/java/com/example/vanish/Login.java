package com.example.vanish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    ImageView leftCircle,rightCircle;
    ConstraintLayout layout;
    Animation leftanimation,rightanimation,layoutfade;
    Button logIn;
    TextView signUp;
    EditText Username,Password;
    CheckBox remember;
    public static final String MY_PREFS = "SharedPreferences";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        leftCircle = findViewById(R.id.leftCircle);
        rightCircle = findViewById(R.id.rightCircle);
        layout = findViewById(R.id.innerlayout);
        logIn = findViewById(R.id.logInButton);
        signUp = findViewById(R.id.signUpButton);
        Username = findViewById(R.id.etUsername);
        Password = findViewById(R.id.etPassword);
        remember = findViewById(R.id.remembercheck);

        leftanimation = AnimationUtils.loadAnimation(this,R.anim.leftcircle);
        rightanimation = AnimationUtils.loadAnimation(this,R.anim.rightcircle);
        layoutfade = AnimationUtils.loadAnimation(this,R.anim.layoutalpha);
        rightCircle.setAnimation(rightanimation);
        leftCircle.setAnimation(leftanimation);
        layout.setAnimation(layoutfade);

        /*SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox = preferences.getString("remember","");
        if (checkbox.equals(true)){
            startActivity(new Intent(Login.this,MainScreen.class));
        }else if (checkbox.equals(false)){
            Toast.makeText(Login.this, "Please Sign In", Toast.LENGTH_SHORT).show();
        }*/
        SharedPreferences mySharedPreferences = getSharedPreferences(MY_PREFS, 0);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putLong("uid", 0);
        editor.commit();
        RememberMe();
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, 0);
        String thisUsername = prefs.getString("username", "");
        String thisPassword = prefs.getString("password", "");
        boolean thisRemember = prefs.getBoolean("remember", false);
        if(thisRemember) {
            Username.setText(thisUsername);
            Password.setText(thisPassword);
            remember.setChecked(thisRemember);
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = Username.getText().toString().trim();
                String password  =  Password.getText().toString().trim();
                auth.signInWithEmailAndPassword(username,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(Login.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this,MainScreen.class));
                                    finish();
                                }else
                                {
                                    Toast.makeText(Login.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked())
                {
                    SharedPreferences sharedPreferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                }else if(!buttonView.isChecked()){
                    SharedPreferences sharedPreferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Signup.class));
                finish();
            }
        });
    }

    private void RememberMe() {
        boolean thisRemember = remember.isChecked();
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("remember", thisRemember);
        editor.commit();
    }
}