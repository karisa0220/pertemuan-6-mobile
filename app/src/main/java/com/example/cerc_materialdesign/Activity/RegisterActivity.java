package com.example.cerc_materialdesign.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cerc_materialdesign.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private MaterialTextView tv_login;
    Button btn_register;
    EditText input_username_reg, input_email_reg, input_password_reg, input_confirm_password, input_telephone, input_fullname;
    String username, email, password, confirm_password, userId;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    private FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();
        btn_register = findViewById(R.id.btn_register);
        input_username_reg = findViewById(R.id.input_username);
        input_email_reg = findViewById(R.id.input_email);
        input_password_reg = findViewById(R.id.input_password);
        input_confirm_password = findViewById(R.id.input_confirm_password);

        tv_login = findViewById(R.id.tv_login);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        registerUser();
        toLogin();
    }

    private void toLogin() {
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
    }

    private void registerUser() {
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = input_username_reg.getText().toString();
                email = input_email_reg.getText().toString();
                password = input_password_reg.getText().toString();
                confirm_password = input_confirm_password.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Username Cannot be Empty !!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Email Cannot be Empty !!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!email.matches(emailPattern)) {
                    Toast.makeText(getApplicationContext(), "Use valid Email !!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Password Cannot be Empty !!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(confirm_password)) {
                    Toast.makeText(getApplicationContext(), "Confirm Password Cannot be Empty !!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirm_password)) {
                    Toast.makeText(getApplicationContext(), "Password doesn't match !!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Daftar sukses, masuk ke Main Activity
                                    userId = mAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = db.collection("Users").document(userId);
                                    Map<String, Object> userData = new HashMap<>();
                                    userData.put("email", email);
                                    userData.put("username", username);
                                    userData.put("address", null);
                                    userData.put("city", null);
                                    userData.put("country", null);

                                    documentReference.set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            sendVerificationEmail();
                                        }
                                    });
                                } else {
                                    // Jika daftar gagal, memberikan pesan
                                    Toast.makeText(RegisterActivity.this, "Proses Pendaftaran gagal : " + task.getException(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }


    private void sendVerificationEmail()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/cgmarketplace-a8727.appspot.com/o/UserImg%2Fimg_default_profile.png?alt=media&token=d893e8b1-f4ef-4718-834d-0b3ac76107ca");
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(username).setPhotoUri(uri).build();

        user.updateProfile(profileUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "User Profile Updated");
                    }
                });
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent
                            AlertDialog dialog = new AlertDialog.Builder(RegisterActivity.this).create();
                            dialog.setTitle("Registration Successful");
                            dialog.setMessage("Registration successful! Please verify your email to activate your account.");
                            dialog.setButton(Dialog.BUTTON_POSITIVE,"Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseAuth.getInstance().signOut();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                            dialog.show();
                            // after email is sent just logout the user and finish this activity

                        }
                        else
                        {
                            // email not sent, so display message and restart the activity or do whatever you wish to do

                            //restart this activity
                            Toast.makeText(RegisterActivity.this, "Please Verify Your Email! : " +  task.getException(),
                                    Toast.LENGTH_LONG).show();
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());

                        }
                    }
                });
    }
}