package com.danilatyukov.linkedmoney

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.LocationManager
import android.os.Build
import androidx.room.Room
import com.danilatyukov.linkedmoney.data.AppDatabase
import com.danilatyukov.linkedmoney.data.LoginActivity
import com.danilatyukov.linkedmoney.data.local.geopoints.GeopointDao
import com.danilatyukov.linkedmoney.data.local.preferences.RetrievedPreference
import com.danilatyukov.linkedmoney.data.local.preferences.SavedPreference
import com.danilatyukov.linkedmoney.data.remote.FDatabaseReader
import com.danilatyukov.linkedmoney.model.ForegroundService
import com.danilatyukov.linkedmoney.model.geolocation.GeolocationManager
import com.danilatyukov.linkedmoney.model.pedometer.PedometerManager


import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Singleton
@Component(modules = [GoogleLoginModule::class, ContextModule::class, DatabaseModule::class, GeolocationManagerModule::class, PedometerModule::class, IntentsModule::class])
interface AppComponent {
    fun injectLoginActivity(activity: LoginActivity)
    val gso: GoogleSignInOptions

    fun injectSavedPreference(savedPreference: SavedPreference)
    val editor: SharedPreferences.Editor

    fun injectRetrievePreference(retrievePreference: RetrievedPreference)
    val sp: SharedPreferences

    fun injectGeolocationManager(geolocationManager: GeolocationManager)
    val fusedLocationClient: FusedLocationProviderClient
    val locationRequest: LocationRequest
    val locationManager: LocationManager

    fun injectForegroundService(foregroundService: ForegroundService)
    val geolocationManager: GeolocationManager
    val pedometerManager: PedometerManager
    val provideForegroundServicePendingIntent: PendingIntent

    val fDatabaseReader: FDatabaseReader

    val notificationIntent: Intent
    val fDatabaseReference: DatabaseReference
    val geopointDao: GeopointDao
    val appDatabase: AppDatabase

    val firebaseInstance: FirebaseDatabase
}

@Module
object GeolocationManagerModule {
    @Singleton
    @Provides
    fun provideGeolocationManager(): GeolocationManager {
        return GeolocationManager()
    }

    @Singleton
    @Provides
    fun provideLocationManager(context: Context): LocationManager{
        return context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    @Singleton
    @Provides
    fun provideFusedLocationClient(context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Singleton
    @Provides
    fun provideLocationRequest(): LocationRequest{
        return LocationRequest.create().apply {
            interval = 30000
            fastestInterval = 10000
            priority = LocationRequest.PRIORITY_LOW_POWER
        }
    }
}

@Module(includes = [FirebaseModule::class, SharedPreferencesModule::class])
object DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "database").build()
    }

    @Singleton
    @Provides
    fun provideGeopointDAO(appDatabase: AppDatabase): GeopointDao {
        return appDatabase.geopointDao()
    }
}


@Module
object FirebaseModule {
    @Singleton
    @Provides
    fun provideFDatabaseReference(firebaseInstance: FirebaseDatabase): DatabaseReference {
        return firebaseInstance.reference
    }

    @Singleton
    @Provides
    fun provideFDatabaseInstance(): FirebaseDatabase{
        return FirebaseDatabase.getInstance("https://linkedmoney-c8216-default-rtdb.asia-southeast1.firebasedatabase.app")
    }

    @Singleton
    @Provides
    fun provideFDatabaseReader(): FDatabaseReader{
        return FDatabaseReader()
    }
}

@Module
object GoogleLoginModule {
    @Singleton
    @Provides
    fun provideGoogleSignInOptions(context: Context): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }
}

@Module
object SharedPreferencesModule {
    @Singleton
    @Provides
    fun provideSharedPreferences(ctx: Context): SharedPreferences {
        return ctx.getSharedPreferences("settings", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideSharedPreferencesEditor(sp: SharedPreferences): SharedPreferences.Editor {
        return sp.edit()
    }
}

@Module
object ContextModule {
    @Singleton
    @Provides
    fun provideContext(): Context {
        return App.it().appContext
    }
}

@Module
object PedometerModule {
    @Singleton
    @Provides
    fun providePedometerManager(): PedometerManager{
        return PedometerManager()
    }
}

@Module
object IntentsModule {
    @Singleton
    @Provides
    fun provideNotificationIntent(context: Context): Intent{
        return Intent(context, MainActivity::class.java)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    @Singleton
    @Provides
    fun provideForegroundServicePendingIntent(context: Context, notificationIntent: Intent): PendingIntent{
        val foregroundServicePendingIntent: PendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getActivity(
                context,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
        return foregroundServicePendingIntent
    }
}

fun main() {
    println("update")

    /*val ka = 30
    val kr = 5
    val sopr = 0.168
    val dkpp = 24

    val odpp = sopr*dkpp
    val ddka = odpp*(ka/100)
    println("ОДПП $odpp ДДКА $ddka")

    val odppvka = odpp - ddka
    val ddkr = odppvka*(kr/100)
    val idpp = odppvka-ddkr
    println("ОДППВКА $odppvka ДДКР $ddkr ИДПП $idpp")


    val krv = 10
    val doksp = 20000
    val rssr = idpp*doksp
    val ssv = krv*rssr
    println("SSV $ssv")*/

}