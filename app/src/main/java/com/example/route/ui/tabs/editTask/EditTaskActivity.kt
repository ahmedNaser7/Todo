package com.example.route.ui.tabs.editTask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.route.R
import com.example.route.databinding.ActivityEditBinding
import com.example.route.db.MyDataBase
import com.example.route.model.Task
import com.example.route.ui.home.HomeActivity
import java.sql.Date
import java.text.SimpleDateFormat


class EditTaskActivity : AppCompatActivity() {
     lateinit var viewBinding: ActivityEditBinding
     private lateinit var task:Task
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        task = ((intent.getSerializableExtra("TASK")as?Task)!!)
        showData()
    }

    private fun showData() {
        viewBinding.title.setText(task.title)
        viewBinding.desc.setText(task.description)
        val date = converLongToTime(task.dataTime)
        viewBinding.date.text = date
        viewBinding.saveTaskBtn.setOnClickListener {
            updateTodo()
        }

    }

    private fun updateTodo() {
        if(!valid())return

        task.title=viewBinding.title.text.toString()
        task.description=viewBinding.desc.text.toString()
        MyDataBase.getInstance(this)
            .userDao()
            .updateTask(task)
        showInsertDialog()
       startActivity(Intent(this,HomeActivity::class.java))
        finish()
    }

    private fun showInsertDialog() {
            val alertDialogBuilder = AlertDialog.Builder(this)
                .setMessage("Update is successfully")
                .setPositiveButton(R.string.app_name)
                {
                    dialog,which->dialog.dismiss()
                }
        alertDialogBuilder.show()
    }

    private fun valid(): Boolean{

        var valid =true
        if(viewBinding.title.text.toString().isNullOrBlank()){
            viewBinding.titleContainer.error="please enter title"
            valid =false
        }
        else{
            viewBinding.titleContainer.error=null
        }
        if(viewBinding.desc.text.toString().isNullOrBlank()){
            viewBinding.descContainer.error="please enter description"
            valid =false
        }
        else{
            viewBinding.descContainer.error=null
        }
        if(viewBinding.date.text.toString().isNullOrBlank()){
            viewBinding.dateContainer.error="please enter dateTime"
            valid =false
        }  else{
            viewBinding.dateContainer.error=null
        }

        return valid
    }

    private fun converLongToTime(dataTime: Long?): String {
        val date = Date(dataTime!!)
        val format = SimpleDateFormat("yyy/MM/dd")
        return format.format(date)
    }
}