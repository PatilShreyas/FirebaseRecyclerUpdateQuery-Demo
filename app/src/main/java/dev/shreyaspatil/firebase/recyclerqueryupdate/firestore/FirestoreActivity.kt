package dev.shreyaspatil.firebase.recyclerqueryupdate.firestore

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import dev.shreyaspatil.firebase.recyclerqueryupdate.Constants
import dev.shreyaspatil.firebase.recyclerqueryupdate.R
import dev.shreyaspatil.firebase.recyclerqueryupdate.model.Post
import dev.shreyaspatil.firebase.recyclerqueryupdate.viewholder.PostViewHolder
import kotlinx.android.synthetic.main.activity_recycler.*

class FirestoreActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mAdapter: FirestoreRecyclerAdapter<Post, PostViewHolder>
    private var mCollectionReference =
        FirebaseFirestore.getInstance().collection(Constants.FIRESTORE_POSTS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        mAdapter = getAdapter()

        // Init RecyclerView
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@FirestoreActivity)
            adapter = mAdapter
        }

        button_first.setOnClickListener(this)
        button_last.setOnClickListener(this)
    }

    private fun getAdapter(): FirestoreRecyclerAdapter<Post, PostViewHolder> {

        val options = FirestoreRecyclerOptions.Builder<Post>()
            .setLifecycleOwner(this)
            .setQuery(getFirstQuery(), Post::class.java)
            .build()

        return object : FirestoreRecyclerAdapter<Post, PostViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
                return PostViewHolder(
                    layoutInflater.inflate(
                        R.layout.item_post,
                        parent,
                        false
                    )
                )
            }

            override fun onBindViewHolder(viewHolder: PostViewHolder, position: Int, post: Post) {
                // Bind to ViewHolder
                viewHolder.bind(post)
            }
        }
    }

    private fun getFirstQuery() = mCollectionReference.limit(5)
    private fun getLastQuery() = mCollectionReference.orderBy("authorName").limitToLast(5)

    override fun onClick(v: View?) {
        val query = if (v == button_first) getFirstQuery() else getLastQuery()

        // Make new options
        val newOptions = FirestoreRecyclerOptions.Builder<Post>()
            .setQuery(query, Post::class.java)
            .build()

        // Change options of adapter.
        mAdapter.updateOptions(newOptions)
    }
}