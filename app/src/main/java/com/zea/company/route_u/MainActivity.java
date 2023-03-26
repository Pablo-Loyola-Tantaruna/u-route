package com.zea.company.route_u;

import static com.zea.company.route_u.fragments_bottom_nav.ProfileFragment.PICK_IMAGE;

import static java.lang.System.out;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationBarView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.zea.company.route_u.databinding.ActivityMainBinding;
import com.zea.company.route_u.fragments_bottom_nav.ExploreFragment;
import com.zea.company.route_u.fragments_bottom_nav.FavouriteFragment;
import com.zea.company.route_u.fragments_bottom_nav.HomeFragment;
import com.zea.company.route_u.fragments_bottom_nav.ProfileFragment;
import com.zea.company.route_u.screen_logueo.LoginActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

//    ActivityMainBinding mainBinding;
    boolean isPermissionGranted = false;
    private BottomSheetBehavior mBottomSheetBehavior1;
    ChipNavigationBar navigationBar;

    HomeFragment homeFragment = new HomeFragment();
    ExploreFragment exploreFragment = new ExploreFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    FavouriteFragment favouriteFragment = new FavouriteFragment();
    LinearLayout tapactionlayout;
    View bottomSheet;
    TextView txtnombre_local, txtDireccion, txtHorario;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
//        View view = mainBinding.getRoot();
//        setContentView(view);
        checkpermissions();
        if (isPermissionGranted) {
            checkGooglePayServices();
        }
        SharedPreferences pref = getSharedPreferences("prefs",MODE_PRIVATE);
        if(!pref.getString("ISLOG","").equalsIgnoreCase("true")){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        View headerLayout1 = findViewById(R.id.bottomJsoft);
        navigationBar = findViewById(R.id.bottom_menu);
        bottomSheet = findViewById(R.id.bottomJsoft);
        tapactionlayout = (LinearLayout) findViewById(R.id.tap_action_layout);
        mBottomSheetBehavior1 = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior1.setPeekHeight(100);
        mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mBottomSheetBehavior1.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    tapactionlayout.setVisibility(View.VISIBLE);
                }
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    tapactionlayout.setVisibility(View.GONE);
                }
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    tapactionlayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
        tapactionlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (mBottomSheetBehavior1.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        }});
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
        navigationBar.setItemSelected(2131296514,true);
        navigationBar.setOnItemSelectedListener(id -> {
            switch (id) {
                case R.id.home:
                    Log.d("PABLITO", "THERE " + id);

                    getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                    break;
                case R.id.explore:
                    Log.d("PABLITO", "EXPLO " + id);
//                    headerLayout1.setVisibility(View.VISIBLE);
//                    bottomSheet.setVisibility(View.VISIBLE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, exploreFragment).commit();
                    break;
//                case R.id.favorites:
//                    Log.d("PABLITO", "FAV " + id);
//                    getSupportFragmentManager().beginTransaction().replace(R.id.container, favouriteFragment).commit();
////                    final Dialog dialog = new Dialog(MainActivity.this);
////                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////                    dialog.setContentView(R.layout.bottomsheetlayout);
////                    LinearLayout editLayout = dialog.findViewById(R.id.layoutEdit);
////                    LinearLayout shareLayout = dialog.findViewById(R.id.layoutShare);
////                    LinearLayout uploadLayout = dialog.findViewById(R.id.layoutUpload);
////                    LinearLayout printLayout = dialog.findViewById(R.id.layoutPrint);
////
////                            dialog.show();
////                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
////                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
////                            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
////                            dialog.getWindow().setGravity(Gravity.BOTTOM);
//
//                    break;
                case R.id.profile:
                    Log.d("PABLITO", "PROF " + id);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                    break;
                default:
                    Log.d("PABLITO", "DEF " + id);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                    break;
            }

        });
        ActivityResultLauncher<String> resultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        SharedPreferences preferences = getSharedPreferences("prefs",MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("routeIMG",result.getPath());
                        editor.putString("routeIMG2",result.toString());
                        editor.commit();
                    }
                });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            //TODO: action
        }
        try {
            final Bundle extras = data.getExtras();
            if (extras != null) {
                //Get image
                Bitmap bmp = extras.getParcelable("data");
                InputStream inputStream = getApplicationContext().getContentResolver().openInputStream(data.getData());
                bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
    private boolean checkGooglePayServices() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int result = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (result == ConnectionResult.SUCCESS) {
            return true;
        } else if (googleApiAvailability.isUserResolvableError(result)) {
            Dialog dialog = googleApiAvailability.getErrorDialog(this, result, 201, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {

                }
            });
            dialog.show();
        }
        return false;
    }

    private void checkpermissions() {
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                isPermissionGranted = true;
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                finishAffinity();
                finish();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

}