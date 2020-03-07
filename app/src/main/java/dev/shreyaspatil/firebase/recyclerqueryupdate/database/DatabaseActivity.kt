package dev.shreyaspatil.firebase.recyclerqueryupdate.database

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import dev.shreyaspatil.firebase.recyclerqueryupdate.Constants
import dev.shreyaspatil.firebase.recyclerqueryupdate.R
import dev.shreyaspatil.firebase.recyclerqueryupdate.model.Post
import dev.shreyaspatil.firebase.recyclerqueryupdate.viewholder.PostViewHolder
import kotlinx.android.synthetic.main.activity_recycler.*

class DatabaseActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mAdapter: FirebaseRecyclerAdapter<Post, PostViewHolder>
    private var mBaseQuery =
        FirebaseDatabase.getInstance().reference.child(Constants.DATABASE_POSTS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        mAdapter = getAdapter()

        // Init RecyclerView
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@DatabaseActivity)
            adapter = mAdapter
        }

        button_first.setOnClickListener(this)
        button_last.setOnClickListener(this)
    }

    private fun getAdapter(): FirebaseRecyclerAdapter<Post, PostViewHolder> {

        val options = FirebaseRecyclerOptions.Builder<Post>()
            .setLifecycleOwner(this)
            .setQuery(getFirstQuery(), Post::class.java)
            .build()

        return object : FirebaseRecyclerAdapter<Post, PostViewHolder>(options) {
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

    private fun getFirstQuery() = mBaseQuery.limitToFirst(5)
    private fun getLastQuery() = mBaseQuery.limitToLast(5)

    override fun onClick(v: View?) {
        val query = if (v == button_first) getFirstQuery() else getLastQuery()

        // Make new options
        val newOptions = FirebaseRecyclerOptions.Builder<Post>()
            .setQuery(query, Post::class.java)
            .build()

        // Change options of adapter.
        mAdapter.updateOptions(newOptions)
    }
}