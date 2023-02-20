package com.example.locationtrackingeinbindung

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.locationtrackingeinbindung.ui.theme.Blue_
import com.example.locationtrackingeinbindung.ui.theme.LocationTrackingEinbindungTheme
import com.google.android.gms.location.*

class MainActivity : ComponentActivity() {

    private var locationCallback: LocationCallback? = null
    var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationRequired = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocationTrackingEinbindungTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    val context = LocalContext.current
                    var currentLocation by remember {
                        mutableStateOf(LocationDetails(0.toDouble(), 0.toDouble()))
                    }
                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                    locationCallback = object : LocationCallback() {
                        override fun onLocationResult(p0: LocationResult) {
                            for (lo in p0.locations) {
                                // Update UI with location data
                                currentLocation = LocationDetails(lo.latitude, lo.longitude)
                            }
                        }
                    }

                    val launcherMultiplePermissions = rememberLauncherForActivityResult(
                        ActivityResultContracts.RequestMultiplePermissions()
                    ) { permissionsMap ->
                        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
                        if (areGranted) {
                            locationRequired = true
                            startLocationUpdates()
                            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Blue_),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        val permissions = arrayOf(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                        Button(
                            modifier = Modifier
                                .height(50.dp)
                                .width(200.dp),
                            colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent),
                            shape = CircleShape,
                            onClick = {
                            if (permissions.all {
                                    ContextCompat.checkSelfPermission(
                                        context,
                                        it
                                    ) == PackageManager.PERMISSION_GRANTED
                                }) {
                                // Get the location
                                startLocationUpdates()
                            } else {
                                launcherMultiplePermissions.launch(permissions)
                            }
                        }) {
                            Text(text = "Get current location")
                        }

                        Spacer(modifier = Modifier.size(10.dp))

                        Text(text = "Latitude : " + currentLocation.latitude)
                        Text(text = "Longitude : " + currentLocation.longitude)

                    }
                }
            }
        }
    }
    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        locationCallback?.let {
            val locationRequest = LocationRequest.create().apply {
                interval = 10000
                fastestInterval = 5000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
            fusedLocationClient?.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )
        }
    }

    override fun onResume() {
        super.onResume()
        if (locationRequired) {
            startLocationUpdates()
        }
    }

    override fun onPause() {
        super.onPause()
        locationCallback?.let { fusedLocationClient?.removeLocationUpdates(it) }
    }
}

