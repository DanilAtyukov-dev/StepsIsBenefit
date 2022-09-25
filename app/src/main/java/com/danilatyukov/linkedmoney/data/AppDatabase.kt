package com.danilatyukov.linkedmoney.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.danilatyukov.linkedmoney.data.local.geopoints.GeopointDao
import com.danilatyukov.linkedmoney.data.local.geopoints.GeopointEntity

@Database(entities = [GeopointEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun geopointDao(): GeopointDao
}