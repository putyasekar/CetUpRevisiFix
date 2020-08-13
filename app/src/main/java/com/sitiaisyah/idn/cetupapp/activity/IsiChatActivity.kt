package com.sitiaisyah.idn.cetupapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.sitiaisyah.idn.cetupapp.R
import com.sitiaisyah.idn.cetupapp.model.ModelUser

class IsiChatActivity : AppCompatActivity() {
    var firebaseUser: FirebaseUser? = null
    var reference: DatabaseReference? = null
    var userIdVisit: String = ""
    var notify = false
    var mChatList: List<ModelUser>? = null
    lateinit var recyclerViewChat: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_isi_chat)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}