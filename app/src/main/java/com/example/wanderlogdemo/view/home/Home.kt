package com.example.wanderlogdemo.view.home

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bayonefelipecuervoasset.database.domain.ToDoDomain
import com.example.wanderlogdemo.R
import com.example.wanderlogdemo.databinding.ActivityHomeBinding
import com.example.wanderlogdemo.view.AddNewNote
import com.example.wanderlogdemo.viewModel.HomeViewModel

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerViewAdapter: HomeListAdapter
    private lateinit var taskArray: ArrayList<ToDoDomain>
    private lateinit var taskListRecyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        getAllTasks()
        binding.floatingHomeButton.setOnClickListener {addNote()}

    }


    private fun getAllTasks() {
        taskArray = arrayListOf<ToDoDomain>()
        homeViewModel.getAllTasks()
        homeViewModel.getAllTask.observe(this) { transactionList ->
            // limpiar lista local
            taskArray.clear()

            // llenar taskArray si transactionList no es nulo
            transactionList?.forEach {
                taskArray.add(
                    ToDoDomain(
                        it.id,
                        it.title,
                        it.date,
                        it.task.toString()
                    )
                )
            }

            if (!transactionList.isNullOrEmpty()) {


                val layoutManager = LinearLayoutManager(this)
                taskListRecyclerView = binding.totalListToDo
                taskListRecyclerView.layoutManager = layoutManager
                taskListRecyclerView.setHasFixedSize(true)
                recyclerViewAdapter = HomeListAdapter(taskArray)
                taskListRecyclerView.adapter = recyclerViewAdapter

                val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                    0,
                    ItemTouchHelper.LEFT
                ) {

                    private val background: ColorDrawable = ColorDrawable(Color.RED)

                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val position = viewHolder.bindingAdapterPosition
                        if (position >= 0 && position < taskArray.size) {
                            val taskToDelete = taskArray[position]
                            // elimino del adapter (asumo que HomeListAdapter tiene este método)
                            recyclerViewAdapter.deleteItem(position)
                            // borro en la DB vía ViewModel si id no es null
                            taskToDelete.id?.let { homeViewModel.deleteTask(it) }
                        }
                    }

                    override fun onChildDraw(
                        c: Canvas,
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        dX: Float,
                        dY: Float,
                        actionState: Int,
                        isCurrentlyActive: Boolean
                    ) {
                        val itemView = viewHolder.itemView

                        if (dX < 0) {
                            // swiping left (dX < 0)
                            background.setBounds(
                                itemView.right + dX.toInt(),
                                itemView.top,
                                itemView.right,
                                itemView.bottom
                            )


                        } else {
                            background.setBounds(0, 0, 0, 0)
                        }

                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    }
                })

                itemTouchHelper.attachToRecyclerView(taskListRecyclerView)
            }

        }
    }


    private fun addNote(){
        val intent = Intent(this, AddNewNote::class.java)
        startActivity(intent)
    }


}