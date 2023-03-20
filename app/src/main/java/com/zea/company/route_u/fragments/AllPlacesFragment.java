package com.zea.company.route_u.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.zea.company.route_u.BuildConfig;
import com.zea.company.route_u.R;
import com.zea.company.route_u.adapters.DescriptionMarks;
import com.zea.company.route_u.adapters.GridAdapter;
import com.zea.company.route_u.adapters.Places_Adapter;
import com.zea.company.route_u.fragments_bottom_nav.ExploreFragment;
import com.zea.company.route_u.fragments_bottom_nav.HomeFragment;
import com.zea.company.route_u.model.ResponseGeneral;
import com.zea.company.route_u.model.results;
import com.zea.company.route_u.network.ApiClient;
import com.zea.company.route_u.network.ApiLocationMaps;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllPlacesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllPlacesFragment extends Fragment implements Places_Adapter.RSClickListener, Places_Adapter.RSClickLongListener{

    //    GridView gridView;
    private List<results> resultsList;
    private ResponseGeneral responseGeneral;
    private String latlong;
    private double lat;
    private double longi;

    FusedLocationProviderClient locationProviderClient;
    private RecyclerView recyclerView;
    private Places_Adapter places_adapter;

    public AllPlacesFragment() {
        // Required empty public constructor
    }

    public static AllPlacesFragment newInstance(String param1, String param2) {
        AllPlacesFragment fragment = new AllPlacesFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_places, container, false);
        return inflater.inflate(R.layout.fragment_all_places, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        gridView = getView().findViewById(R.id.gridViewPlaces);
//        String[] flowersName = {"Rose","lotus","Lily","Joseline",
//                "Tulip","Orchid","Lavander","RoseMarry","Sunfloer","Carnation"};
//        int[] flowers = {
//                R.drawable.a,
//                R.drawable.b,
//                R.drawable.c,
//                R.drawable.d,
//                R.drawable.e,
//                R.drawable.f,
//                R.drawable.g,
//                R.drawable.h,
//                R.drawable.i,
//                R.drawable.j
//        };
//        GridAdapter gridAdapter = new GridAdapter(getContext(),flowersName,flowers);
//        gridView.setAdapter(gridAdapter);
//
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(), "CLICK "+position, Toast.LENGTH_SHORT).show();
//            }
//        });
        recyclerView = getView().findViewById(R.id.recycler_places);
        //recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        showPhotos();
        super.onViewCreated(view, savedInstanceState);
    }

    public void showPhotos() {
//        Call<List<results>> call = ApiClient
//                .getPlaces()
//                .create(ApiLocationMaps.class)
//                .getNearbyLocations(
//                "cruise",
//                "-12.167297045369493,-76.92391056034707",
//                "10000",
//                "market",
//                BuildConfig.MAPS_API_KEY
//                );
//        call.enqueue(new Callback<List<results>>() {
//            @Override
//            public void onResponse(Call<List<results>> call, Response<List<results>> response) {
//                Timber.d("LOL: %s", response.raw());
//                if (response.isSuccessful()) {
//                    resultsList = response.body();
//                    places_adapter = new Places_Adapter(resultsList, getContext());
//                    recyclerView.setAdapter(places_adapter);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<results>> call, Throwable t) {
//                Timber.e("FALLA: "+t.getMessage());
//                Timber.d("NADA FALLA POR AQUI YOU KNOW");
//            }
//        });
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(getContext().LOCATION_SERVICE);
        LocationListener locationListener = location -> {
            lat = location.getLatitude();
            longi = location.getLongitude();
            Timber.d("LATITUD: "+lat);
            Timber.d("LONGITUD: "+longi);
            latlong = lat + ","+longi;
            Timber.d("VER: "+latlong);
            Call<ResponseGeneral> call = ApiClient
                    .getPlaces()
                    .create(ApiLocationMaps.class)
                    .getNearbyLocation(
                            latlong,
                            "10000",
                            "tourist_attraction",
                            BuildConfig.MAPS_API_KEY
                    );
            call.enqueue(new Callback<ResponseGeneral>() {
                @Override
                public void onResponse(Call<ResponseGeneral> call, Response<ResponseGeneral> response) {
                    if (response.isSuccessful()) {
//                      Con ESTO PUEDO VER EL JSON QUE VIENE O LO QUE VIENE
                        Gson gson = new Gson();
                        String res = gson.toJson(response.body());
                        Timber.d( "onResponse: " + res);

                        ResponseGeneral responseGeneral1 = response.body();
                        List<results> results1 = responseGeneral1.getResultsArrayList();
                        places_adapter = new Places_Adapter(results1, getContext(), AllPlacesFragment.this::selectedPlace, AllPlacesFragment.this::selectedDescriptionPlace);
                        recyclerView.setAdapter(places_adapter);
                    }
                }

                @Override
                public void onFailure(Call<ResponseGeneral> call, Throwable t) {
                    Timber.d("NADA DE NADA: "+t.getMessage());
                    Timber.d("NOTHING TO NOTHING: "+t.getLocalizedMessage());
                }
            });
        };


        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,50000,500,locationListener);
    }

    @Override
    public void selectedPlace(results rs) {
        Timber.d("EL NU: "+rs.getName());
        Bundle bundle = new Bundle();
        bundle.putString("RSULT",new Gson().toJson(rs));
        Fragment fragment = new ExploreFragment();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void selectedDescriptionPlace() {
        DescriptionMarks descriptionMarks = new DescriptionMarks();
        descriptionMarks.show(getActivity().getSupportFragmentManager(), descriptionMarks.getTag());
    }
}
