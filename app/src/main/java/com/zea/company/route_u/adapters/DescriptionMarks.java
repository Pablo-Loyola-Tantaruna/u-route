package com.zea.company.route_u.adapters;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.zea.company.route_u.BuildConfig;
import com.zea.company.route_u.R;
import com.zea.company.route_u.model.ResponseGeneral;
import com.zea.company.route_u.network.ApiClient;
import com.zea.company.route_u.network.ApiLocationMaps;
import com.zea.company.route_u.network.IGoogleApi;
import com.zea.company.route_u.network.RetrofitDirections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class DescriptionMarks extends BottomSheetDialogFragment {


    BottomSheetDialog dialog;
    BottomSheetBehavior<View> viewBottomSheetBehavior;
    View view;

    JSONObject jsonResult, jsonLocation, jsonGeometry, jsonDest, jsonObjectDest,jsonObj;
    JSONArray jsonArray;
    private double lat;
    private double longi;

    private String uriFirst = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&maxheight=400&photo_reference=";
    private String uriSecond = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&maxheight=400&photo_reference=";
    TextView TitleBottom, txtdescription, txtwhere, txttime, txtisOpen, txtrating, txtdistance;
    ImageView imgReference;
    FloatingActionButton button;
    boolean bundleRequest;
    private LatLng ubic;
    private String latlong, latilongdest;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        return dialog;
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        view = inflater.inflate(R.layout.bottom_sheet_map, container, false);
        //return super.onCreateView(inflater, container, savedInstance);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewBottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        viewBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        RelativeLayout layout = dialog.findViewById(R.id.bottomSheetLayout);
        assert layout != null;
        layout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
        imgReference = dialog.findViewById(R.id.imgReference);
        TitleBottom = dialog.findViewById(R.id.TitleBottom);
        txtdescription = dialog.findViewById(R.id.txtdescription);
        txtwhere = dialog.findViewById(R.id.txtwhere);
        txttime = dialog.findViewById(R.id.txttime);
        txtisOpen = dialog.findViewById(R.id.txtisOpen);
        txtrating = dialog.findViewById(R.id.txtrating);
        txtdistance = dialog.findViewById(R.id.txtdistance);
        button = (FloatingActionButton) dialog.findViewById(R.id.floatbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            try {
                jsonResult = new JSONObject(bundle.getString("RSULT"));

                jsonDest = new JSONObject(jsonResult.getString("geometry"));
                jsonObjectDest = new JSONObject(jsonDest.getString("location"));
            } catch (Exception e) {
            }
            LocationManager locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(getContext().LOCATION_SERVICE);
            LocationListener locationListener = location -> {
                lat = location.getLatitude();
                longi = location.getLongitude();


                latlong = lat + "," + longi;
                Timber.d("LOG: %s",latlong);
                try {
                    latilongdest = jsonObjectDest.getString("lat") + "," + jsonObjectDest.getString("lng");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                getDirections(latlong,latilongdest).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            try {
                                Gson gson = new Gson();
                                String res = gson.toJson(response.body());
                                Timber.d("JSON: " + jsonResult);
                                Timber.d("JSON: " + res);
                                TitleBottom.setText(jsonResult.getString("name"));
                                if (!jsonResult.has("description")) {
                                    txtdescription.setVisibility(View.GONE);
                                } else {
                                    txtdescription.setText(jsonResult.getString("description"));
                                }
                                txtrating.setText("Tiene una puntuación de: "+ jsonResult.getString("rating"));
                                txtwhere.setText(jsonResult.getString("vicinity"));
                                if (jsonResult.has("opening_hours")) {
                                    jsonLocation = new JSONObject(jsonResult.getString("opening_hours"));
                                    if (jsonLocation.getString("open_now").equalsIgnoreCase("true")) {
                                        txtisOpen.setText("Se encuentra abierto");
                                    } else {
                                        txtisOpen.setText("Se encuentra cerrado");
                                    }
                                } else {
                                    txtisOpen.setText("No se encuentra disponible");
                                }
                                JSONObject jsonObject = new JSONObject(response.body());
                                JSONArray jsonArray = jsonObject.getJSONArray("routes");
                                JSONObject route = jsonArray.getJSONObject(0);
                                JSONArray legs = route.getJSONArray("legs");
                                JSONObject leg= legs.getJSONObject(0);
                                JSONObject distance = leg.getJSONObject("distance");
                                JSONObject duration = leg.getJSONObject("duration");


                                if(jsonResult.has("photos")) {
                                    jsonArray = jsonResult.getJSONArray("photos");
                                    jsonObj = jsonArray.getJSONObject(0);
                                    uriFirst = uriFirst + jsonObj.getString("photo_reference") + "&key=" + BuildConfig.MAPS_API_KEY;
                                    Glide.with(getContext()).load(uriFirst).into(imgReference);
                                }else{
                                    Glide.with(getContext()).load(R.drawable.not_found).into(imgReference);
                                }
                                txtdistance.setText("La distancia es de: "+distance.getString("text"));
                                txttime.setText("Tiempo estimado: "+duration.getString("text"));
                                bundleRequest = true;
                            } catch (JSONException e) {
                                Timber.e("ERROR: " + e);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getActivity().getApplicationContext(), "Ocurrio un error", Toast.LENGTH_SHORT).show();
                    }
                });

//                Call<ResponseGeneral> call = ApiClient
//                        .getPlaces()
//                        .create(ApiLocationMaps.class)
//                        .getDataAbout(
//                                latlong,
//                                latilongdest,
//                                BuildConfig.MAPS_API_KEY
//                        );
//                call.enqueue(new Callback<ResponseGeneral>() {
//                    @Override
//                    public void onResponse(Call<ResponseGeneral> call, Response<ResponseGeneral> response) {
//
//
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseGeneral> call, Throwable t) {
//
//
//
//                    }
//                });


            };
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1, locationListener);
        } else {
            bundleRequest = false;
            Timber.d("ES NULO");
        }
    }
    public Call<String> getDirections(String latO,String latDes){
        String baseUrl = "https://maps.googleapis.com";
        String query="/maps/api/directions/json?mode-walking&"+
                "origin="+latO+"&"+
                "destination="+latDes
                +"&"+"key="+BuildConfig.MAPS_API_KEY;
        Log.d("PABLOOO->    ",query);
        return RetrofitDirections.getDirec(baseUrl).create(IGoogleApi.class).getDirections(baseUrl+query);
    }
}
