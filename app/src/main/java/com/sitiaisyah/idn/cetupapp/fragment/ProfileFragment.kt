package com.sitiaisyah.idn.cetupapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sitiaisyah.idn.cetupapp.R
import com.sitiaisyah.idn.cetupapp.viewmodel.ProfileViewModel

class ProfileFragment : Fragment() {

    private lateinit var profileVM: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileVM =
            ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        profileVM.text.observe(viewLifecycleOwner, Observer {
        })
        return root
    }
}