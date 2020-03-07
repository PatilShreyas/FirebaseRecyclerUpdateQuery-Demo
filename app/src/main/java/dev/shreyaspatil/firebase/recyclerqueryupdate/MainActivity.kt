package dev.shreyaspatil.firebase.recyclerqueryupdate

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import dev.shreyaspatil.firebase.recyclerqueryupdate.database.DatabaseActivity
import dev.shreyaspatil.firebase.recyclerqueryupdate.firestore.FirestoreActivity
import dev.shreyaspatil.firebase.recyclerqueryupdate.firestore.FirestorePagingActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_realtime_db_demo.setOnClickListener(this)
        button_firestore_db_demo.setOnClickListener(this)
        button_firestore_db_paging_demo.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val activityClass = when (v) {
            button_realtime_db_demo -> DatabaseActivity::class.java
            button_firestore_db_demo -> FirestoreActivity::class.java
            button_firestore_db_paging_demo -> FirestorePagingActivity::class.java
            else -> null
        }

        activityClass?.let {
            startActivity(Intent(this, it))
        }
    }
}
