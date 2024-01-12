package com.example.route.ui.tabs.TasksList

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.route.base.BaseFragment
import com.example.route.databinding.FragmentTasksListBinding
import com.example.route.db.MyDataBase
import com.example.route.model.Task
import com.example.route.ui.tabs.editTask.EditTaskActivity
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import java.util.Calendar


class TasksListFragment : BaseFragment() {
lateinit var viewBinding: FragmentTasksListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
          viewBinding = FragmentTasksListBinding.inflate(
              inflater,container,false
          )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onStart() {
        super.onStart()
        loadTasks()
    }


    fun loadTasks() {
         context?.let {
             val tasks = MyDataBase.getInstance(it)
                 .userDao()
                 .getTasksByDate(selectedDate.timeInMillis)

             adapter.bindTasks(tasks.toMutableList())
         }

    }

    private val adapter = TasksAdapter(null)
    val selectedDate  = Calendar.getInstance()
    init {
        // ignore hours
        selectedDate.set(Calendar.HOUR,0)
        selectedDate.set(Calendar.MINUTE,0)
        selectedDate.set(Calendar.SECOND,0)
        selectedDate.set(Calendar.MILLISECOND ,0)
    }

    private fun initView() {
        viewBinding.recycler.adapter = adapter
        adapter.onItemDeleteListener = TasksAdapter.OnItemClickListener { position, task ->
            deleteTaskFromDatabase(task)
            adapter.taskDeleted(task)
        }

        adapter.onCardClickedListener =
            TasksAdapter.OnItemClickedListener { task ->
                showMessage("what do you want",
                    "update",
                    { _, dialog ->
                        updateTodoTask(task)
                    }, "Make done", { _, dialog ->
                        makeDone(task)
                    }
                )
            }

        viewBinding.calendarView.setSelectedDate(
            CalendarDay.today()
        )
        viewBinding.calendarView.setOnDateChangedListener { widget, date, selected ->

            if (selected) {
                //reload tasks in the selected date
                selectedDate.set(Calendar.YEAR, date.year)
                selectedDate.set(Calendar.MONTH, date.month - 1)
                selectedDate.set(Calendar.DAY_OF_MONTH, date.day)
                loadTasks()
            }

        }


    }

    private fun makeDone(task: Task) {
           task.isDone = true
        MyDataBase.getInstance(requireContext())
            .userDao()
            .updateTask(task)
       adapter.taskUpdated(task)
    }

    private fun updateTodoTask(task: Task) {
        val intent = Intent(requireContext(), EditTaskActivity::class.java)
        intent.putExtra("TASK",task)
        startActivity(intent)
    }

    private fun deleteTaskFromDatabase(task: Task) {

        MyDataBase.getInstance(requireContext())
            .userDao()
            .deleteTask(task)
    }


}