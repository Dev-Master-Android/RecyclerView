package com.example.recyclerview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class DetailActivity : AppCompatActivity() {
    private var position = -1
    private lateinit var item: Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val toolbar: Toolbar = findViewById(R.id.toolbarDetail)
        toolbar.title = "Изменить данные"
        setSupportActionBar(toolbar)

        val imageView: ImageView = findViewById(R.id.detail_image)
        val titleView: TextView = findViewById(R.id.detail_title)
        val descView: TextView = findViewById(R.id.detail_description)
        val back = findViewById<Button>(R.id.detail_back)

        item = intent.getSerializableExtra("item") as Item
        position = intent.getIntExtra("position", -1)

        item?.let {
            imageView.setImageResource(it.image)
            titleView.text = it.title
            descView.text = it.description
        }

        findViewById<View>(R.id.root_view).setOnClickListener {
            showUpdateDialog(item)
        }

        back.setOnClickListener {
            finish()
        }
    }

    private fun showUpdateDialog(item: Item) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_update, null)
        val editTitle = dialogView.findViewById<EditText>(R.id.edit_title)
        val editDescription = dialogView.findViewById<EditText>(R.id.edit_description)
        editTitle.setText(item.title)
        editDescription.setText(item.description)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Обновить")
            .setView(dialogView)
            .setPositiveButton("Обновить") { _, _ ->
                val title = editTitle.text.toString()
                val description = editDescription.text.toString()
                val updatedItem = Item(item.image, title, description)
                val resultIntent = Intent()
                resultIntent.putExtra("item", updatedItem)
                resultIntent.putExtra("position", position)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
            .setNegativeButton("Отмена", null)
            .create()
        dialog.show()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_exit -> {
                finishAffinity()
                Toast.makeText(this, "Программа завершена", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
