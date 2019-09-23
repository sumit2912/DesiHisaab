package com.diamond.it.desihisaab.ui

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.diamond.it.desihisaab.R
import com.diamond.it.desihisaab.screen.ScreenHelper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_locat_us.*

class LocateUsActivity : BaseActivity(), OnMapReadyCallback {

    private lateinit var locationManager: LocationManager
    private lateinit var supportMapFragment: SupportMapFragment
    private lateinit var googleMap: GoogleMap

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
    }

    override fun addMessageReceiver(): ScreenHelper.MessageReceiver {
        return this@LocateUsActivity
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.ivFindMe -> {
                var location: Location? = null
                try {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                }catch (e:SecurityException){}
                if(location != null){
                    val latlng = LatLng(location.latitude,location.longitude)
                    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latlng,18f)
                    googleMap.moveCamera(cameraUpdate)
                }
            }
        }
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
}
