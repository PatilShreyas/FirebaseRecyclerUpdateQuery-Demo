package dev.shreyaspatil.firebase.recyclerqueryupdate.firestore

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dev.shreyaspatil.firebase.recyclerqueryupdate.Constants
import dev.shreyaspatil.firebase.recyclerqueryupdate.R
import dev.shreyaspatil.firebase.recyclerqueryupdate.model.Post
import dev.shreyaspatil.firebase.recyclerqueryupdate.viewholder.PostViewHolder
import kotlinx.android.synthetic.main.activity_recycler.*

class FirestorePagingActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mAdapter: FirestorePagingAdapter<Post, PostViewHolder>
    private var mCollectionReference =
        FirebaseFirestore.getInstance().collection(Constants.FIRESTORE_POSTS)

    // Paging List configuration to use it with FirestorePagingOptions
    private var pagingConfig = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPrefetchDistance(2)
        .setPageSize(10)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        mAdapter = getAdapter()

        // Init RecyclerView
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@FirestorePagingActivity)
            adapter = mAdapter
        }

        button_first.setOnClickListener(this)
        button_last.setOnClickListener(this)
    }

    private fun getAdapter(): FirestorePagingAdapter<Post, PostViewHolder> {

        val options = FirestorePagingOptions.Builder<Post>()
            .setLifecycleOwner(this)
            .setQuery(getFirstQuery(), pagingConfig, Post::class.java)
            .build()

        return object : FirestorePagingAdapter<Post, PostViewHolder>(options) {
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
    private fun getLastQuery() =
        mCollectionReference.orderBy("authorName", Query.Direction.DESCENDING)

    override fun onClick(v: View?) {
        val query = if (v == button_first) getFirstQuery() else getLastQuery()

        // Make new options
        val newOptions = FirestorePagingOptions.Builder<Post>()
            .setQuery(query, pagingConfig, Post::class.java)
            .build()

        // Change options of adapter.
        mAdapter.updateOptions(newOptions)
    }
}