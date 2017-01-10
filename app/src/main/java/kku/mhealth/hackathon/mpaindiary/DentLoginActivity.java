package kku.mhealth.hackathon.mpaindiary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DentLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailEditText,passEditText;
    private Button loginButton;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dent_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //After login activity
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        progressDialog = new ProgressDialog(this);
        loginButton = (Button)findViewById(R.id.button2);
        emailEditText = (EditText)findViewById(R.id.editText);
        passEditText = (EditText)findViewById(R.id.editText2);

        loginButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if(v == loginButton){
            loginDentUser();
        }
    }

    private void loginDentUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passEditText.getText().toString().trim();

        if(email.isEmpty()){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.isEmpty()){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length() < 6){
            Toast.makeText(this, "Passwords must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Login User..." + email + " : " + password);
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(DentLoginActivity.this, "Login Complete", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
                else{
                    Toast.makeText(DentLoginActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
