package com.danilatyukov.linkedmoney.ui.dashboard.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.appComponent
import com.danilatyukov.linkedmoney.data.local.geopoints.GeopointEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class WorldMapViewModel : ViewModel() {
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val geopointDao = App.it().appComponent.geopointDao

    private val _points = MutableLiveData<MutableList<GeopointEntity>>().apply {
        compositeDisposable.add(geopointDao.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                value = it
            })
    }

    val points: LiveData<MutableList<GeopointEntity>> = _points
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}