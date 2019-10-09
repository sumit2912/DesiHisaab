package com.diamond.it.desihisaab.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.diamond.it.desihisaab.R
import com.diamond.it.desihisaab.common.AlertDialogManager
import com.diamond.it.desihisaab.model.data_model.Data
import com.diamond.it.desihisaab.screen.ScreenHelper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.GeoApiContext
import kotlinx.android.synthetic.main.activity_locat_us.*

class LocateUsActivity : BaseActivity(), OnMapReadyCallback, AlertDialogManager.AlertDialogListener {

    private val TAG = ""
    private lateinit var locationManager: LocationManager
    private lateinit var supportMapFragment: SupportMapFragment
    private lateinit var googleMap: GoogleMap
    private val REQ_LOCATION = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_locat_us
    }

    override fun getActivityContext(): Context {
        return this@LocateUsActivity
    }

    override fun initUi() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.locate_us)
        supportMapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        supportMapFragment.getMapAsync(this)
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        ivFindMe.setOnClickListener(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.uiSettings.isMapToolbarEnabled = false
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkCoarseLocationPermission() && checkFineLocationPermission()) {
                locateMe()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION), REQ_LOCATION)
            }
        } else {
            locateMe()
        }
    }

    override fun addMessageReceiver(): ScreenHelper.MessageReceiver {
        return this@LocateUsActivity
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.ivFindMe -> {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkCoarseLocationPermission() && checkFineLocationPermission()) {
                        locateMe()
                    } else {
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), REQ_LOCATION)
                    }
                } else {
                    locateMe()
                }
            }
        }
    }

    private fun locateMe() {
        var networkLocation: Location? = null
        var gpsLocation: Location? = null

        try {
            networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        } catch (e: SecurityException) {
        }

        try {
            gpsLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        } catch (e: SecurityException) {
        }

        var latlng: LatLng? = null

        if (gpsLocation != null) {
            latlng = LatLng(gpsLocation.latitude, gpsLocation.longitude)
        }

        if (networkLocation != null) {
            latlng = LatLng(networkLocation.latitude, networkLocation.longitude)
        }

        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latlng, 18f)
        googleMap.moveCamera(cameraUpdate)

        latlng?.let { drawCurrentLocation(it) }
    }

    private fun drawCurrentLocation(latlng: LatLng) {
        googleMap.clear()
        val markerOptionsSource = MarkerOptions()
        markerOptionsSource.position(latlng)
        markerOptionsSource.title("Your Location")
        markerOptionsSource.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        googleMap.addMarker(markerOptionsSource)

        val markerOptionsDestiantion = MarkerOptions()
        val latlngDes = LatLng(21.640639,69.609194)
        markerOptionsDestiantion.position(latlngDes)
        markerOptionsDestiantion.title("Your Destination Location")
        markerOptionsDestiantion.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        googleMap.addMarker(markerOptionsDestiantion)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finish()
    }

    private fun checkFineLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkCoarseLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQ_LOCATION && grantResults.size > 0) {
            locateMe()
        } else {
            Toast.makeText(context, "Grant location permission to locate us.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onMessageReceived(from: String, msg: String, data: Data?) {
        super.onMessageReceived(from, msg, data)
    }

    override fun getAlertDialogListener(): AlertDialogManager.AlertDialogListener {
        return this@LocateUsActivity
    }

    override fun onPositiveClicked(type: String) {

    }

    override fun onNegativeClicked(type: String) {

    }
}
