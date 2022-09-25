package com.danilatyukov.linkedmoney

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.danilatyukov.linkedmoney.data.AppDatabase
import com.danilatyukov.linkedmoney.data.LoginActivity
import com.danilatyukov.linkedmoney.data.local.geopoints.GeopointDao
import com.danilatyukov.linkedmoney.data.local.preferences.RetrievedPreference
import com.danilatyukov.linkedmoney.data.local.preferences.SavedPreference
import com.danilatyukov.linkedmoney.model.GeolocationManager


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
@Component(modules = [GoogleLoginModule::class, SharedPreferencesModule::class, ContextModule::class, FirebaseModule::class, DatabaseModule::class, GeolocationManagerModule::class])
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




    val fDatabaseReference: DatabaseReference
    val geopointDao: GeopointDao
    val appDatabase: AppDatabase
}


@Module
object GeolocationManagerModule {
    @Singleton
    @Provides
    fun provideFusedLocationClient(context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Singleton
    @Provides
    fun provideLocationRequest(): LocationRequest{
        return LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            smallestDisplacement = 10f
        }
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
object FirebaseModule {
    @Singleton
    @Provides
    fun provideFDatabaseReference(): DatabaseReference {
        return FirebaseDatabase.getInstance("https://linkedmoney-c8216-default-rtdb.asia-southeast1.firebasedatabase.app").reference
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
        //
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

fun main() {
    println("update")
}