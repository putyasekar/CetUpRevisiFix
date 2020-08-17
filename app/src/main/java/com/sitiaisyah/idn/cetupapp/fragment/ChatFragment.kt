package com.sitiaisyah.idn.cetupapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.iid.FirebaseInstanceId
import com.sitiaisyah.idn.cetupapp.R
import com.sitiaisyah.idn.cetupapp.adapter.UserAdapter
import com.sitiaisyah.idn.cetupapp.model.ChatList
import com.sitiaisyah.idn.cetupapp.model.Token
import com.sitiaisyah.idn.cetupapp.model.Users

class ChatFragment : Fragment() {
    private var userAdapter: UserAdapter? = null
    private var mUser: List<Users>? = null
    private var userChatList: List<ChatList>? = null
    private var firebaseUser: FirebaseUser? = null
    lateinit var recyclerChatList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        recyclerChatList = view.findViewById(R.id.rv_chat_list)
        recyclerChatList.setHasFixedSize(true)
        recyclerChatList.layoutManager = LinearLayoutManager(context)

        firebaseUser = FirebaseAuth.getInstance().currentUser

        userChatList = ArrayList()

        val ref = FirebaseDatabase.getInstance().reference.child("ChatList")
            .child(firebaseUser!!.uid)

        ref!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshots: DataSnapshot) {
                (userChatList as ArrayList).clear()

                for (dataSnapshot in snapshots.children) {
                    val chatList = dataSnapshot.getValue(ChatList::class.java)
                    (userChatList as ArrayList).add(chatList!!)
                }
                retrieveChatList()
            }
        })

        updateToken(FirebaseInstanceId.getInstance().token)
        return view
    }

    private fun updateToken(token: String?) {
        val ref = FirebaseDatabase.getInstance().reference.child("Tokens")
        val firstToken = Token(token!!)
        ref.child(firebaseUser!!.uid).setValue(firstToken)
    }

    private fun retrieveChatList() {
        mUser = ArrayList()

        val ref = FirebaseDatabase.getInstance().reference.child("Users")
        ref!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshots: DataSnapshot) {
                (mUser as ArrayList).clear()

                for (dataSnapshot in snapshots.children) {
                    val user = dataSnapshot.getValue(Users::class.java)
                    for (chatList in userChatList!!) {
                        if (user!!.getUID().equals(chatList.getId())) {
                            (mUser as ArrayList).add(user!!)
                        }
                    }
                }
                userAdapter = UserAdapter(context!!, (mUser as ArrayList<Users>), true)
                recyclerChatList.adapter = userAdapter
            }
        })
    }
}