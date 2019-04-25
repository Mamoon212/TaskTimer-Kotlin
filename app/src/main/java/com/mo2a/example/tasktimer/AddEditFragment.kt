package com.mo2a.example.tasktimer

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_add_edit.*

private const val TAG= "AddEditFragment"
private const val ARG_TASK = "task"

class AddEditFragment : Fragment() {
    private var task: Task? = null
    private var listener: OnSaveClicked? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG,"onCreate:starts")
        super.onCreate(savedInstanceState)
        task = arguments?.getParcelable(ARG_TASK)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG,"onCreateView:starts")
        return inflater.inflate(R.layout.fragment_add_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: called")
        if(savedInstanceState == null){
            val task= task
            if( task != null){
                Log.d(TAG, "${task.id}")
                addedit_name.setText(task.name)
                addedit_description.setText(task.description)
                addedit_sortorder.setText(task.sortOrder.toString())
            }else{

            }
        }
    }

    private fun saveTask(){
        val sortOrder= if(addedit_sortorder.text.isNotEmpty()){
            Integer.parseInt(addedit_sortorder.text.toString())
        }else{
            0
        }
        val values= ContentValues()
        val task= task

        if(task != null){
            Log.d(TAG, "saveTask updating existing task")
            if(addedit_name.text.toString() != task.name){
                values.put(TasksContract.Columns.TASK_NAME, addedit_name.text.toString())
            }
            if(addedit_description.text.toString() != task.description){
                values.put(TasksContract.Columns.TASK_DESCRIPTION, addedit_description.text.toString())
            }
            if(sortOrder != task.sortOrder){
                values.put(TasksContract.Columns.TASK_SORT_ORDER, sortOrder)
            }
            if(values.size() != 0){
                activity?.contentResolver?.update(TasksContract.buildUriFromId(task.id), values, null, null)
            }
        }else{
            Log.d(TAG, "saveTask creating new task")
            if(addedit_name.text.isNotEmpty()){
                values.put(TasksContract.Columns.TASK_NAME, addedit_name.text.toString())
                if(addedit_description.text.isNotEmpty()){
                    values.put(TasksContract.Columns.TASK_DESCRIPTION, addedit_description.text.toString())
                }
                values.put(TasksContract.Columns.TASK_SORT_ORDER, sortOrder)
                activity?.contentResolver?.insert(TasksContract.CONTENT_URI, values)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated starts")
        super.onActivityCreated(savedInstanceState)
        if(listener is AppCompatActivity) {
            val actionBar = (listener as AppCompatActivity?)?.supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
        }

        addedit_save.setOnClickListener{
            saveTask()
            listener?.onSaveClicked()
        }
    }

    override fun onAttach(context: Context) {
        Log.d(TAG,"onAttach:starts")
        super.onAttach(context)
        if (context is OnSaveClicked) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnSaveClicked")
        }
    }

    override fun onDetach() {
        Log.d(TAG,"onDetach:starts")
        super.onDetach()
        listener = null
    }

    interface OnSaveClicked {
        fun onSaveClicked()
    }

    companion object {
        @JvmStatic
        fun newInstance(task: Task?) =
            AddEditFragment().apply {
                arguments = Bundle().apply {
                   putParcelable(ARG_TASK, task)
                }
            }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewStateRestored: called")
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onStart() {
        Log.d(TAG, "onStart: called")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG, "onResume: called")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "onPause: called")
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(TAG, "onSaveInstanceState: called")
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        Log.d(TAG, "onStop: called")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView: called")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: called")
        super.onDestroy()
    }
}
