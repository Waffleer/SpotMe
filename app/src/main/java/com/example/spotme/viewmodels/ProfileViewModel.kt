package com.example.spotme.viewmodels

import androidx.lifecycle.ViewModel
import com.example.spotme.data.Profile
import com.example.spotme.database.Repository

class ProfileViewModel(private val repository: Repository) : ViewModel() {

    companion object {
        fun insertProfile(profile: Profile) {
            //repository.insertProfile(profile)
        }
    }
}
