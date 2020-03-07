package dev.shreyaspatil.firebase.recyclerqueryupdate.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.shreyaspatil.firebase.recyclerqueryupdate.R
import dev.shreyaspatil.firebase.recyclerqueryupdate.model.Post

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var authorView: TextView = itemView.findViewById(R.id.post_AuthorName)
    private var messageView: TextView = itemView.findViewById(R.id.post_Message)

    fun bind(post: Post) {
        authorView.text = post.authorName
        messageView.text = post.message
    }
}