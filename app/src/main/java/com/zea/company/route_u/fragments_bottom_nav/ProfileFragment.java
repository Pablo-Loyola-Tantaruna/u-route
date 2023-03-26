package com.zea.company.route_u.fragments_bottom_nav;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.zea.company.route_u.R;
import com.zea.company.route_u.screen_logueo.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    EditText profileEmail, profileUsername, profilePassword,confirmPassword;
    TextView titleName, titleUsername;
    Button editProfile;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    ImageView img;

    public static final int PICK_IMAGE = 1;
    private ImageButton menuProfile;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        profileEmail = getView().findViewById(R.id.profile_email);
        profileUsername = getView().findViewById(R.id.profile_username);
        profilePassword = getView().findViewById(R.id.profile_password);
        confirmPassword = getView().findViewById(R.id.confirmPassword);
        img = getView().findViewById(R.id.profileImg);

        titleName = getView().findViewById(R.id.titleName);
        titleUsername = getView().findViewById(R.id.titleUsername);
        //editSeccion = findViewById(R.id.linearLayout);

        editProfile = getView().findViewById(R.id.edit_button);
        menuProfile = getView().findViewById(R.id.menuProfile);

        reference = FirebaseDatabase.getInstance().getReference("users");
        enableOrDisableProfile(false);
        showUserData();

        SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences("prefs",MODE_PRIVATE);
        String route = preferences.getString("routeIMG","");;
        if(!route.isEmpty()){
            img.setImageURI(Uri.parse(route));
        }
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);
            }
        });

        menuProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getActivity().getApplicationContext(),menuProfile);
                popupMenu.getMenuInflater().inflate(R.menu.menu,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.edit_profile:
                                enableOrDisableProfile(true);
                                confirmPassword.setVisibility(View.VISIBLE);
                                editProfile.setVisibility(View.VISIBLE);
                                return true;

                            case R.id.logout_profile:
                                enableOrDisableProfile(false);
                                SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences("prefs",MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("ISLOG","false");
                                editor.commit();
                                mAuth.signOut();
                                startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                                getActivity().finish();
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPasswordChanged() && isEmailChanged()){
                    UpdateCampos();
                    Toast.makeText(getActivity(), "User Update", Toast.LENGTH_SHORT).show();
                    enableOrDisableProfile(false);

                } else {
                    Toast.makeText(getActivity(), "No Changes Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void enableOrDisableProfile(boolean onof){
        profileUsername.setEnabled(false);
        profileEmail.setEnabled(onof);
        profilePassword.setEnabled(onof);
        confirmPassword.setEnabled(onof);

        editProfile.setEnabled(onof);

        confirmPassword.setVisibility(View.INVISIBLE);
        editProfile.setVisibility(View.INVISIBLE);

    }

    public void showUserData(){
        SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences("prefs",MODE_PRIVATE);
        String emailUser = preferences.getString("email","");;
        String usernameUser = preferences.getString("username","");
        String passwordUser = preferences.getString("password","");
        String confirmpwdUser = preferences.getString("password","");


        titleUsername.setText(usernameUser);

        profileEmail.setText(emailUser);
        profileUsername.setText(usernameUser);
        profilePassword.setText(passwordUser);
        confirmPassword.setText(confirmpwdUser);
    }

    public boolean isEmailChanged(){
        String usernameEdit = profileUsername.getText().toString();
        String emailEdit = profileEmail.getText().toString();

        if(!emailEdit.isEmpty()){
            reference.child(usernameEdit).child("email").setValue(emailEdit);
            return true;
        } else {
            return false;
        }
    }

    public boolean isPasswordChanged(){
        String usernameEdit = profileUsername.getText().toString();
        String passwordEdit = profilePassword.getText().toString();
        String ConfirmPassword = confirmPassword.getText().toString();

        if(passwordEdit.equals(ConfirmPassword)){
            reference.child(usernameEdit).child("password").setValue(passwordEdit);
            return true;

        } else {
            profilePassword.setError("Password is Required");
            confirmPassword.setError("Password is Required");
            return false;
        }
    }

    public void UpdateCampos(){
        String UserDB = profileUsername.getText().toString();

        reference = FirebaseDatabase.getInstance().getReference("users");
        Query check = reference.orderByChild("username").equalTo(UserDB);

        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences("prefs",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("username",snapshot.child(UserDB).child("username").getValue(String.class));
                editor.putString("password",snapshot.child(UserDB).child("password").getValue(String.class));
                editor.putString("email",snapshot.child(UserDB).child("email").getValue(String.class));
                editor.commit();


                titleUsername.setText(snapshot.child(UserDB).child("username").getValue(String.class));
                profileUsername.setText(snapshot.child(UserDB).child("username").getValue(String.class));
                profilePassword.setText(snapshot.child(UserDB).child("password").getValue(String.class));
                confirmPassword.setText(snapshot.child(UserDB).child("password").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}