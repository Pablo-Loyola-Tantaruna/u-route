package com.zea.company.route_u.screen_logueo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;
import com.zea.company.route_u.MainActivity;
import com.zea.company.route_u.R;
import com.zea.company.route_u.databinding.ActivityLoginBinding;
import com.zea.company.route_u.databinding.ActivityMainBinding;
import com.zea.company.route_u.databinding.ActivityRegisterBinding;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {


    ActivityLoginBinding loginBinding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login)
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = loginBinding.getRoot();
        setContentView(view);
        mAuth = FirebaseAuth.getInstance();
        loginBinding.EDENTER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginBinding.EDemail.getText().toString().isEmpty() || loginBinding.EDPASS.getEditText().getText().toString().isEmpty()) {
                    PopupDialog.getInstance(LoginActivity.this)
                            .setStyle(Styles.FAILED)
                            .setHeading("Inicio de sesión incorrecto")
                            .setDescription("El correo y/o la contraseña son incorrectos")
                            .setCancelable(false)
                            .showDialog(new OnDialogButtonClickListener() {
                                @Override
                                public void onDismissClicked(Dialog dialog) {
                                    super.onDismissClicked(dialog);
                                }
                            });
                } else {
                    mAuth.signInWithEmailAndPassword(loginBinding.EDemail.getText().toString(), loginBinding.EDPASS.getEditText().getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    SharedPreferences preferences = getSharedPreferences("prefs",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("ISLOG","true");
                                    editor.commit();
                                    PopupDialog.getInstance(LoginActivity.this)
                                            .setStyle(Styles.SUCCESS)
                                            .setHeading("LOGUEO EXITOSO")
                                            .setDescription("Se inicio sesion correctamente")
                                            .setCancelable(false)
                                            .showDialog(new OnDialogButtonClickListener() {
                                                @Override
                                                public void onDismissClicked(Dialog dialog) {
                                                    super.onDismissClicked(dialog);
                                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                                }
                                            });
                                }
                            });
                }
            }
        });
        loginBinding.irRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}