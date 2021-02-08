package alexb.roommvvmrxjava2kotlin.ui.adapter

import alexb.roommvvmrxjava2kotlin.R
import alexb.roommvvmrxjava2kotlin.data.local.entity.User
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter:
    RecyclerView.Adapter<UserAdapter.UserViewHolder>(
    ) {

    private var userList = mutableListOf<User>()

    var onClick: ((User) -> Unit)? = null

    fun setUser(list: List<User>) {
        userList.clear()
        userList.addAll(list)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var user: User? = null

        private val userNameTextView: TextView = itemView.findViewById(R.id.tv_user_name)
        private val userSportTextView: TextView = itemView.findViewById(R.id.tv_sport)

        fun bind(data: User) {
            user = data
            userNameTextView.text =
                itemView.context.getString(R.string.user_name, data.firstName, data.lastName)
            userSportTextView.text = itemView.context.getString(R.string.sport, data.sport)
        }

        init {
            itemView.setOnClickListener {
                user?.let { it1 -> onClick?.invoke(it1) }
            }
        }
    }

    fun removeAt(position: Int): User {
        val user = userList[position]
        userList.removeAt(position)
        notifyItemRemoved(position)
        return user
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item_list, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }
}

