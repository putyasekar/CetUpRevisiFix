package com.sitiaisyah.idn.cetupapp.fragment

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.sitiaisyah.idn.cetupapp.R
import com.sitiaisyah.idn.cetupapp.model.Users
import kotlinx.android.synthetic.main.fragment_setting.view.*

class SettingFragment : Fragment() {
    var userReference: DatabaseReference? = null
    var firebaseUser: FirebaseUser? = null
    private val RequestCode = 438
    private var imageUri: Uri? = null
    private var storageRef: StorageReference? = null
    private var coverCheck: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_setting,
            container,
            false
        )

        firebaseUser = FirebaseAuth.getInstance().currentUser
        userReference =
            FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
        storageRef = FirebaseStorage.getInstance().reference.child("UserImages")

        userReference!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user: Users? = snapshot.getValue(Users::class.java)

                    if (context != null) {
                        view.tv_username_.text = user!!.getUserName()
                    }
                }
            }
        })
        view.cv_profile_.setOnClickListener {
            pickImage()
        }
        return view
    }

    private fun pickImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, RequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RequestCode && resultCode == Activity.RESULT_OK && data!!.data != null)
            imageUri = data.data
        Toast.makeText(
            context,
            getString(R.string.upload),
            Toast.LENGTH_LONG
        ).show()

        uploadingImageToDatabase()
    }

    private fun uploadingImageToDatabase() {
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage(getString(R.string.wait))
        progressDialog.show()

        val fileRef = storageRef!!.child(System.currentTimeMillis().toString() + ".jpg")
        val uploadTask: StorageTask<*>
        uploadTask = fileRef.putFile(imageUri!!)

        uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful) {
                task.exception.let {
                    throw it!!
                }
            }

            return@Continuation fileRef.downloadUrl

        }).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUrl = task.result
                val url = downloadUrl.toString()

                if (coverCheck == "cover") {
                    val mapCoverImage = HashMap<String, Any>()
                    mapCoverImage["cover"] = url
                    userReference!!.updateChildren(mapCoverImage)
                    coverCheck = ""
                } else {
                    val mapProfiImage = HashMap<String, Any>()
                    mapProfiImage["profile"] = url
                    userReference!!.updateChildren(mapProfiImage)
                    coverCheck = ""
                }
                progressDialog.dismiss()
            }
        }
    }
}