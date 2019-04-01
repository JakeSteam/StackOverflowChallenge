package uk.co.jakelee.stackoverflowchallenge.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import uk.co.jakelee.stackoverflowchallenge.R
import uk.co.jakelee.stackoverflowchallenge.UserActivity
import uk.co.jakelee.stackoverflowchallenge.model.User



class UserListAdapter (private val users: List<User>)
    : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.element_userrow, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.userId.text = user.id.toString()
        holder.userName.text = user.name
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, UserActivity::class.java)
            intent.putExtra(UserActivity.USER_EXTRA, user)
            startActivity(it.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userId: TextView = view.findViewById(R.id.user_id)
        val userName: TextView = view.findViewById(R.id.user_name)
    }
}