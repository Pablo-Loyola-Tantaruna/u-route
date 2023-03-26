package com.zea.company.route_u.screen_logueo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;
import com.zea.company.route_u.R;
import com.zea.company.route_u.databinding.ActivityRegisterBinding;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    //    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://u-route-379712-default-rtdb.firebaseio.com/");

    ActivityRegisterBinding binding;
    String username, password, email, passconfirm;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mAuth = FirebaseAuth.getInstance();
        binding.btnregister.setOnClickListener(v -> {
            //get data from editText with binding
            username = binding.edusername.getText().toString();
            password = binding.edpassword.getEditText().getText().toString();
            passconfirm = binding.edconfirmpassword.getEditText().getText().toString();
            email = binding.edemail.getText().toString();

            if (username.isEmpty() || password.isEmpty() || passconfirm.isEmpty() || email.isEmpty()) {
                showDialogFailed(getApplicationContext(), "Datos Incompletos", "Los datos son incorrectos o están incompletos!");
            } else if (!password.equalsIgnoreCase(passconfirm)) {
                showDialogFailed(getApplicationContext(), "Contraseñas incorrectas", "Las contraseñas no son iguales!");
            } else {
                //showDialogProgress();
                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(username)) {
                            showDialogFailed(getApplicationContext(), "Usuario Existente", "El usuario ya existe coloque otro!");
                        } else {
                            SharedPreferences preferences = getSharedPreferences("prefs",MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("username",username);
                            editor.putString("password",password);
                            editor.putString("email",email);
                            editor.commit();
                            databaseReference.child("users").child(username).child("username").setValue(username);
                            databaseReference.child("users").child(username).child("email").setValue(email);
                            databaseReference.child("users").child(username).child("dateRegister").setValue(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
                            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        showDialogSuccess(getApplicationContext(), "Registro Exitoso!", "Usted se registró exitosamente!");
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        binding.irLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
    private void showDialogFailed(Context context, String msjHead, String msjDesc) {
        PopupDialog.getInstance(RegisterActivity.this)
                .setStyle(Styles.FAILED)
                .setHeading(msjHead)
                .setDescription(msjDesc)
                .setCancelable(false)
                .showDialog(new OnDialogButtonClickListener() {
                    @Override
                    public void onDismissClicked(Dialog dialog) {
                        super.onDismissClicked(dialog);
                    }
                });
    }
    private void showDialogSuccess(Context context, String msjHead, String msjDesc) {
        PopupDialog.getInstance(RegisterActivity.this)
                .setStyle(Styles.SUCCESS)
                .setHeading(msjHead)
                .setDescription(msjDesc)
                .setCancelable(false)
                .showDialog(new OnDialogButtonClickListener() {
                    @Override
                    public void onDismissClicked(Dialog dialog) {
                        super.onDismissClicked(dialog);
                        startActivity(new Intent(context,LoginActivity.class));
                    }
                });
    }
}