package com.sitiaisyah.idn.cetupapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sitiaisyah.idn.cetupapp.R
import com.sitiaisyah.idn.cetupapp.viewmodel.GroupViewModel

class GroupFragment : Fragment() {

    private lateinit var groupVM: GroupViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        groupVM =
            ViewModelProviders.of(this).get(GroupViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_group, container, false)
        groupVM.text.observe(viewLifecycleOwner, Observer {
        })
        return root
    }
}