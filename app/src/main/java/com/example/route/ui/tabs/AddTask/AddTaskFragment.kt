package com.example.route.ui.tabs.AddTask

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.route.databinding.FragmentAddTaskBinding
import com.example.route.db.MyDataBase
import com.example.route.model.Task
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class AddTaskFragment:BottomSheetDialogFragment() {

    lateinit var viewBinding: FragmentAddTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding= FragmentAddTaskBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.addTaskBtn.setOnClickListener {
            creatTask()
        }
        viewBinding.date.setOnClickListener {
            showDatePickerDialog()
        }
    }
val calendar = Calendar.getInstance()
    private fun showDatePickerDialog() {
        context?.let {
            val dialog = DatePickerDialog(it)
            dialog.setOnDateSetListener { datePicker, year, month, day ->
                viewBinding.date.setText(
                    "$day-${month+1}-$year"
                )
                calendar.set(year,month,day)
                // ignore his time
                calendar.set(Calendar.HOUR_OF_DAY,0)
                calendar.set(Calendar.MINUTE,0)
                calendar.set(Calendar.SECOND,0)
                calendar.set(Calendar.MILLISECOND,0)
            }
            dialog.show()
        }

    }

    private fun creatTask() {
        if(!valid()){
            return
        }

        val task = Task(
        title=viewBinding.title.text.toString(),
        description = viewBinding.desc.text.toString(),
          dataTime = calendar.timeInMillis.toLong()
        )

        MyDataBase.getInstance(requireContext())
            .userDao()
            .insertTask(task)

       onTaskAddedListener?.onTaskAdded()
        dismiss()
    }

    var onTaskAddedListener: OnTaskAddedListener?=null
    fun interface OnTaskAddedListener{
        fun onTaskAdded()
    }

    private fun valid(): Boolean {
       if(viewBinding.title.text.toString().isNullOrBlank()){
           viewBinding.titleContainer.error="please enter title"
           return false
       }
        else{
           viewBinding.titleContainer.error=null
       }
        if(viewBinding.desc.text.toString().isNullOrBlank()){
            viewBinding.descContainer.error="please enter description"
            return false
        }
        else{
            viewBinding.descContainer.error=null
        }
        if(viewBinding.date.text.toString().isNullOrBlank()){
            viewBinding.dateContainer.error="please enter dateTime"
            return false
        }  else{
            viewBinding.dateContainer.error=null
        }

        return true
    }


}