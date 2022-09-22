package com.danilatyukov.linkedmoney

import android.content.Context
import android.content.SharedPreferences
import com.danilatyukov.linkedmoney.data.LoginActivity
import com.danilatyukov.linkedmoney.data.local.SavedPreference

import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
@Singleton
@Component(modules = [GoogleLoginModule::class, SharedPreferencesModule::class, ContextModule::class, FirebaseModule::class])
interface AppComponent {
    fun inject(activity: LoginActivity)
    fun inject(savedPreference: SavedPreference)
    val gso: GoogleSignInOptions
    val sp: SharedPreferences
    val editor: SharedPreferences.Editor
    val fDatabaseReference: DatabaseReference
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
    fun provideSharedPreferences(ctx: Context): SharedPreferences{
       return ctx.getSharedPreferences("settings", Context.MODE_PRIVATE)
        //
    }
    @Singleton
    @Provides
    fun provideSharedPreferencesEditor(sp: SharedPreferences): SharedPreferences.Editor{
        return sp.edit()
    }
}

@Module
object ContextModule {
    @Singleton
    @Provides
    fun provideContext(): Context{
        return App.it().appContext
    }
}

fun main(){
    println("update")
}