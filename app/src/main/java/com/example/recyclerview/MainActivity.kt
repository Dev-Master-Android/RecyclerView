package com.example.recyclerview

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), ItemAdapter.OnItemClickListener {
    private lateinit var items: MutableList<Item>
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Мой гардероб"
        setSupportActionBar(toolbar)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        items = generateItems().toMutableList()
        adapter = ItemAdapter(items, this)
        recyclerView.adapter = adapter
    }

    private fun generateItems(): List<Item> {
        val items = mutableListOf<Item>()
        for (i in 1..30) {
            items.add(Item(R.drawable.ic_launcher_background, "Item $i", "Description $i"))
        }
        return items
    }

    override fun onItemClick(item: Item, position: Int) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("item", item)
            putExtra("position", position)
        }
        startActivityForResult(intent, REQUEST_CODE)
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            data?.let {
                val updatedItem = it.getSerializableExtra("item") as Item
                val position = it.getIntExtra("position", -1)
                if (position != -1) {
                    items[position] = updatedItem
                    adapter.notifyItemChanged(position)
                }
            }
        }
    }

    companion object {
        const val REQUEST_CODE = 1
    }

}
