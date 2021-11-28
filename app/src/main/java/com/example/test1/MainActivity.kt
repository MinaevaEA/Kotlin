package com.example.test1

import android.content.Intent
import android.icu.text.CaseMap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class MainActivity : AppCompatActivity(), MainUi {

    lateinit var title: EditText
    lateinit var message: EditText
    lateinit var btnToast: Button
    lateinit var btnActivity: Button
    lateinit var btnShare: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = findViewById(R.id.title)
        message = findViewById(R.id.message)
        btnToast = findViewById(R.id.buttonToast)
        btnActivity = findViewById(R.id.buttonActivity)
        btnShare = findViewById(R.id.buttonShare)
        val presenter = MainPresenter(this)

        btnToast.setOnClickListener {
            presenter.onNewTodo(title.text.toString(), message.text.toString())
        }
        btnActivity.setOnClickListener {
            presenter.activityBtnClick()
        }
        btnShare.setOnClickListener {
            presenter.shareBtnClick(
                title.text.toString(), message.text.toString()
            )
        }

    }

    override fun onSaveSuccess() {
        showNotification(getString(R.string.save_msg))
    }

    override fun onSaveFailed() {
        showNotification(getString(R.string.msg_error))
    }

    override fun showNotification(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT)

            .apply {
                setGravity(Gravity.TOP, 0, 250)
                show()
            }

    }

    override fun startIntent() {
        val intent = Intent(this, DataActivity::class.java)
        startActivity(intent)
    }

    override fun shareNote(title: String, message: String) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "$title/$message")

        }
        startActivity(shareIntent)

    }
}


//private fun broser(){
//    val browseIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
//    startActivity(browseIntent)
//}









