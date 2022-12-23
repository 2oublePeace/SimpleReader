package emiryanvl.simplereader.ui.view

import emiryanvl.simplereader.R
import emiryanvl.simplereader.data.local.entity.Book
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.barteksc.pdfviewer.PDFView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class BookActivity : AppCompatActivity() {

    private var book: Book? = null
    private var pdfView: PDFView? = null
    private val pdfSuffix = "pdf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        if (intent.hasExtra(EXTRA_CHOOSE_BOOK)) {
            book = intent.extras?.get(EXTRA_CHOOSE_BOOK) as Book
        }

        initViews()
        initBook()
    }

    private fun initViews() {
        pdfView = findViewById(R.id.pdfView)
    }

    private fun initBook() {
        val storage = Firebase.storage
        val storageRef = storage.reference
        val islandRef = storageRef.child(book!!.path)
        val localFile = File.createTempFile(book!!.path, pdfSuffix)
        islandRef.getFile(localFile).addOnSuccessListener {
            pdfView!!.fromFile(File(localFile.path))
                .swipeHorizontal(true)
                .pageSnap(true)
                .autoSpacing(true)
                .pageFling(true)
                .load();
        }.addOnFailureListener {
            Exception("Нету книги")
        }
    }
}