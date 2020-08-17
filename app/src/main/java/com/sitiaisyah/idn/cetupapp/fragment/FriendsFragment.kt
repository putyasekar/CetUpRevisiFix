package com.sitiaisyah.idn.cetupapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sitiaisyah.idn.cetupapp.R
import com.sitiaisyah.idn.cetupapp.viewmodel.FriendsViewModel

class FriendsFragment : Fragment() {

    private lateinit var friendsVM: FriendsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        friendsVM =
            ViewModelProviders.of(this).get(FriendsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_friends, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
        friendsVM.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
        })
        return root
    }
}