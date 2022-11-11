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
import com.danilatyukov.linkedmoney.data.local.geopoints.GeopointDao
import com.danilatyukov.linkedmoney.data.local.preferences.AppPreferences
import com.danilatyukov.linkedmoney.data.local.preferences.RememberMePreference
import com.danilatyukov.linkedmoney.data.local.preferences.ScoresPreferences
import com.danilatyukov.linkedmoney.data.remote.interactors.StatisticInteractorImpl
import com.danilatyukov.linkedmoney.model.ForegroundService
import com.danilatyukov.linkedmoney.model.geolocation.GeolocationManager
import com.danilatyukov.linkedmoney.model.pedometer.PedometerManager
import com.danilatyukov.linkedmoney.networkListeners.StatisticListener


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
@Component(modules = [SharedPreferencesModule::class ,RestModule::class, ContextModule::class, DatabaseModule::class, GeolocationManagerModule::class, PedometerModule::class, IntentsModule::class])
interface AppComponent {


    fun injectGeolocationManager(geolocationManager: GeolocationManager)
    val fusedLocationClient: FusedLocationProviderClient
    val locationRequest: LocationRequest
    val locationManager: LocationManager

    fun injectForegroundService(foregroundService: ForegroundService)
    val geolocationManager: GeolocationManager
    val pedometerManager: PedometerManager
    val provideForegroundServicePendingIntent: PendingIntent


    val notificationIntent: Intent
    val geopointDao: GeopointDao
    val appDatabase: AppDatabase


    val statisticInteractor: StatisticInteractorImpl

    val statisticListener: StatisticListener

    val appPreferences: AppPreferences
    val rememberMePreference: RememberMePreference
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

@Module
object SharedPreferencesModule {
    @Singleton
    @Provides
    fun provideAppPreferences(ctx: Context): AppPreferences {
        return AppPreferences.create(ctx)
    }

    @Singleton
    @Provides
    fun provideRememberMePreferences(ctx: Context): RememberMePreference {
        return RememberMePreference.create(ctx)
    }

}

@Module
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
object RestModule {
    @Singleton
    @Provides
    fun provideStatisticInteractor(appPreferences: AppPreferences, statisticListener: StatisticListener): StatisticInteractorImpl{
        return StatisticInteractorImpl(appPreferences, statisticListener)
    }
    @Singleton
    @Provides
    fun provideStatisticListener(): StatisticListener{
        return StatisticListener()
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