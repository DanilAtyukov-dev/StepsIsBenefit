package com.danilatyukov.linkedmoney.data.local.geopoints

import androidx.room.*
import io.reactivex.Flowable


@Dao
interface GeopointDao {
    @Query("SELECT * FROM Geopoints")
    fun getAll(): Flowable<MutableList<GeopointEntity>>

    @Insert
    fun insert(geopointEntity: GeopointEntity)

    @Delete
    fun delete(geopointEntity: GeopointEntity)

    @Query("DELETE FROM Geopoints")
    fun deleteAll()
}

@Entity(tableName = "Geopoints")
data class GeopointEntity(

    val date: String,

    val time: String,

    val speed: Int,

    val accuracy: Float,

    val longitude: Double,

    val latitude: Double,
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null
)
