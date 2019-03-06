package com.example.rest.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rest.R;
import com.example.rest.entity.CallREST;
import com.example.rest.service.APISErvice;
import com.example.rest.service.APIUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TomTom extends AppCompatActivity {
    //  private TomtomMap tomtomMap;
    private TextView m;
    private Button btn;
    private LocationManager locationManager;
    /* GPS Constant Permission */
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;
    private APISErvice myService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        setContentView(R.layout.activity_tom_tom);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btn = findViewById(R.id.button2);
        m = findViewById(R.id.label);
        //permisos?
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSION_ACCESS_COARSE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_ACCESS_FINE_LOCATION);
        }
        //   tomtomMap.setMyLocationEnabled(true);
        if (isLocationEnabled()) {
            myService = APIUtils.getAPIService();

            btn.setOnLongClickListener(new View.OnLongClickListener() {
                @SuppressLint("MissingPermission")
                @Override
                public boolean onLongClick(View v) {
                    btn.setEnabled(false);

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 10, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            m.setText(location.getLatitude() + " " + location.getLongitude());
                            System.out.println(location.getLatitude() + "," + location.getLongitude());
                            Call<CallREST> t = myService.sendCall(new CallREST(location.getLatitude() + "," + location.getLongitude(), "pedido"));
                            t.enqueue(new Callback<CallREST>() {
                                @Override
                                public void onResponse(Call<CallREST> call, Response<CallREST> response) {
                                    btn.setEnabled(true);

                                    Toast.makeText(getApplicationContext(), "Procesado, espere un momento", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onFailure(Call<CallREST> call, Throwable t) {
                                    t.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    });

                    return true;
                }
            });
        } else {
            showAlert();
        }
    }


    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Habilite ubicación")
                .setMessage("La configuracion de Ubicación esta apagada.\nPor Favor habilitela y reinicie la app "
                )
                .setPositiveButton("Ir a  permisos", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }
}

