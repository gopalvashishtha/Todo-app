package com.example.todolist



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_user.view.*
import java.text.SimpleDateFormat
import java.util.*


class UserAdapter( val users: List<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_user,
                parent,
                false
            )
        )
    }


    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }
    override fun getItemId(position: Int): Long {
        return users [position].id
    }


class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(user: User) {

        with(itemView) {
            val colors = resources.getIntArray(R.array.random_color)
            val randomColor = colors[Random().nextInt(colors.size)]
            viewColorTag.setBackgroundColor(randomColor)
            txtShowTitle.text = user.title
            txtShowtask.text = user.description
//            txtShowCategory.text = user.category
            updateTime(user.time)
            updateDate(user.date)
        }

    }

    private fun updateTime(time: Long) {
        val myFormat = "hh:mm a"
        val sdf = SimpleDateFormat(myFormat)
        itemView.txtShowTime.text = sdf.format(Date(time))

    }

    private fun updateDate(date: Long) {
        val myFormat = "EEE, d MMM yyyy"
        val sdf = SimpleDateFormat(myFormat)
        itemView.txtShowDate.text = sdf.format(Date(date))
    }


}

}



