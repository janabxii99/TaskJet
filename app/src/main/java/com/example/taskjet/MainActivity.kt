package com.example.taskjet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.content.DialogInterface


class MainActivity : AppCompatActivity(), DialogCloseListener {

    private lateinit var db: DatabaseHandler
    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var tasksAdapter: Adapter
    private lateinit var fab: FloatingActionButton
    private lateinit var taskList: MutableList<Model>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        db = DatabaseHandler(this)
        db.openDatabase()

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView)
        tasksRecyclerView.layoutManager = LinearLayoutManager(this)
        tasksAdapter = Adapter(db, this)
        tasksRecyclerView.adapter = tasksAdapter

        val itemTouchHelper = ItemTouchHelper(RecyclerItemTouchHelper(tasksAdapter))
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView)

        fab = findViewById(R.id.Addbutton)

        taskList = db.getAllTasks().toMutableList()
        taskList.reverse()

        tasksAdapter.setTasks(taskList)

        fab.setOnClickListener {
            NewTask.newInstance().show(supportFragmentManager, NewTask.TAG)
        }
    }

    override fun handleDialogClose(dialog: DialogInterface) {
        taskList = db.getAllTasks().toMutableList()
        taskList.reverse()
        tasksAdapter.setTasks(taskList)
        tasksAdapter.notifyDataSetChanged()
    }
}
