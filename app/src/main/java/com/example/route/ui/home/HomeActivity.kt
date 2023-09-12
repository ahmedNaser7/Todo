package com.example.route.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.route.R
import com.example.route.databinding.ActivityHomeBinding
import com.example.route.ui.tabs.AddTask.AddTaskFragment
import com.example.route.ui.tabs.SettingList.SettingFragment
import com.example.route.ui.tabs.TasksList.TasksListFragment
import com.google.android.material.snackbar.Snackbar

class HomeActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityHomeBinding
    var tasksListFragmentRef:TasksListFragment?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.list_navigation_item->{
                    tasksListFragmentRef = TasksListFragment()
                  showFragment(tasksListFragmentRef!!)
                }
                R.id.setting_navigation_item->{
                  showFragment(SettingFragment())
                }
            }
            return@setOnItemSelectedListener true
        }

        viewBinding.addTask.setOnClickListener {
            showTaskBottomSheet()
        }

        viewBinding.bottomNavigation.selectedItemId=R.id.list_navigation_item


    }

    private fun showTaskBottomSheet() {
     val addTask = AddTaskFragment()
              // *******  call back ***** //
        // add fragment => home
        addTask.onTaskAddedListener= AddTaskFragment.OnTaskAddedListener {
            Snackbar.make(viewBinding.root,
            "Task Added Successfully",Snackbar.LENGTH_INDEFINITE
                ).show()

            // home => list fragment
            // notify tasks list fragment
            tasksListFragmentRef?.loadTasks()

        }
        addTask.show(supportFragmentManager,"")
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
            .commit()

    }
}