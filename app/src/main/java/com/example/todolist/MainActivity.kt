package com.example.todolist


import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
//import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.item_user.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.nio.file.Files.delete


class MainActivity : AppCompatActivity()  {
  var list = arrayListOf<User>()

     var adapter = UserAdapter(list)

    val db by lazy {
        AppDatabase.getDatabase(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        Rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
        db.userDao().getTask().observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                list.clear()
                list.addAll(it)
                adapter.notifyDataSetChanged()
            }else{
                list.clear()
                adapter.notifyDataSetChanged()
            }
        })

       btnadd.setOnClickListener {  val i = Intent( this, Main2Activity::class.java)
            startActivity(i)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu,menu)

        val item = menu.findItem(R.id.search)
        val searchView = item.actionView as SearchView
        item.setOnActionExpandListener(object :MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                displayTodo()
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                displayTodo()
                return true
            }

        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(!newText.isNullOrEmpty()){
                    displayTodo(newText)
                }
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    fun displayTodo(newText: String = "") {
        db.userDao().getTask().observe(this, Observer {
            if(it.isNotEmpty()){
                list.clear()
                list.addAll(
                    it.filter { todo ->
                        todo.title.contains(newText,true)
                    }
                )
                adapter.notifyDataSetChanged()
            }
        })
    }

    fun openNewTask(view: View) {
        startActivity(Intent(this, Main2Activity::class.java))
    }


//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.delete ->
//                if (note != null) delete() else context?.toast("Cannot Delete")
//        }
//        return super.onOptionsItemSelected(item)
    }

