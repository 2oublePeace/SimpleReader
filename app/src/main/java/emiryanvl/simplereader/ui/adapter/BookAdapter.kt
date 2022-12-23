package emiryanvl.simplereader.ui.adapter

import emiryanvl.simplereader.R
import emiryanvl.simplereader.data.local.entity.Book
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookAdapter:
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private var bookList = mutableListOf<Book>()
    lateinit var onClick: ((Book) -> Unit)

    fun setBook(list: List<Book>) {
        bookList.clear()
        bookList.addAll(list)
        notifyDataSetChanged()
    }

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var book: Book? = null

        private val bookTitleTextView: TextView = itemView.findViewById(R.id.tv_user_name)
        private val bookAuthorTextView: TextView = itemView.findViewById(R.id.tv_sport)

        fun bind(data: Book) {
            book = data
            bookTitleTextView.text = itemView.context.getString(R.string.title, data.title)
            bookAuthorTextView.text = itemView.context.getString(R.string.author, data.author)
        }

        init {
            itemView.setOnClickListener {
                book?.let { it1 -> onClick.invoke(it1) }
            }
        }
    }

    fun removeAt(position: Int): Book {
        val book = bookList[position]
        bookList.removeAt(position)
        notifyItemRemoved(position)
        return book
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item_list, parent, false)
        return BookViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(bookList[position])
    }
}