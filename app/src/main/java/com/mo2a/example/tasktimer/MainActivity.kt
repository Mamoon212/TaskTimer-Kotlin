package com.mo2a.example.tasktimer

import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

private const val TAG= "MainActivity"

class MainActivity : AppCompatActivity(), AddEditFragment.OnSaveClicked{

    private var mTwoPane= false

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate starts")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mTwoPane= resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Log.d(TAG, "onCreate twopane is $mTwoPane")


        val fragment= supportFragmentManager.findFragmentById(R.id.task_details_container)
        if(fragment != null){
            showEditPane()
        }else{
            task_details_container.visibility = if(mTwoPane) View.INVISIBLE else View.GONE
            mainFragment.view?.visibility= View.VISIBLE
        }
        Log.d(TAG, "onCreate finishes")

    }

    private fun showEditPane(){
        task_details_container.visibility= View.VISIBLE
        mainFragment.view?.visibility= if(mTwoPane) View.VISIBLE else View.GONE
    }

    private fun removeEditPane(fragment: Fragment?= null){
        Log.d(TAG, "removeEditPane called")
        if(fragment !=null){
            supportFragmentManager.beginTransaction()
                .remove(fragment)
                .commit()
        }
        task_details_container.visibility= if(mTwoPane) View.INVISIBLE else View.GONE
        mainFragment.view?.visibility= View.VISIBLE

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.menuMain_addTask -> taskEditRequest(null)
//            R.id.menuMain_settings -> true
            android.R.id.home ->{
                val fragment = supportFragmentManager.findFragmentById(R.id.task_details_container)
                removeEditPane(fragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun taskEditRequest(task: Task?){
        Log.d(TAG, "taskEditReq: Starts")
        val newFragment= AddEditFragment.newInstance(task)
        supportFragmentManager.beginTransaction()
            .replace(R.id.task_details_container, newFragment)
            .commit()
        showEditPane()
        Log.d(TAG, "taskEditReq: ends")
    }

    override fun onSaveClicked() {
        Log.d(TAG, "onSaveClicked called")
        val fragment= supportFragmentManager.findFragmentById(R.id.task_details_container)
        removeEditPane(fragment)
    }

    override fun onBackPressed() {
        val fragment= supportFragmentManager.findFragmentById(R.id.task_details_container)
        if(fragment == null || mTwoPane) {
            super.onBackPressed()
        }else{
            removeEditPane(fragment)
        }
    }


    // MainActivity Lifecycle callback events - added for logging only
    override fun onStart() {
        Log.d(TAG, "onStart: called")
        super.onStart()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        Log.d(TAG, "onRestoreInstanceState: called")
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onResume() {
        Log.d(TAG, "onResume: called")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "onPause: called")
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        Log.d(TAG, "onSaveInstanceState: called")
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        Log.d(TAG, "onStop: called")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: called")
        super.onDestroy()
    }

}
