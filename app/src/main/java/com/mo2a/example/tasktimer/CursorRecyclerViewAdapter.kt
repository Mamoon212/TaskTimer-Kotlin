package com.mo2a.example.tasktimer

import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.task_list_items.*

class TaskViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer{
}

private const val TAG= "Cursor"

class CursorRecyclerViewAdapter(private var cursor: Cursor?) : RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        Log.d(TAG, "ocvh: new view requested")
        val view= LayoutInflater.from(parent.context).inflate(R.layout.task_list_items, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        Log.d(TAG, "obvh: starts")
        val cursor= cursor
        if(cursor == null || cursor.count == 0){
            Log.d(TAG, "obvh: instructions")
            holder.tli_name.text = "Instructions"
            holder.tli_description.setText("Use the add button (+) in the toolbar above to create new tasks." +
                    "\n\nTasks with lower sort orders will be placed higher up the list." +
                    "Tasks with the same sort order will be sorted alphabetically." +
                    "\n\nTapping a task will start the timer for that task (and will stop the timer for any previous task that was being timed)." +
                    "\n\nEach task has Edit and Delete buttons if you want to change the details or remove the task.")
            holder.tli_edit.visibility= View.GONE
            holder.tli_delete.visibility= View.GONE
        }else{
            if(!cursor.moveToPosition(position)){
                throw IllegalStateException("couldn't move to $position")
            }
            val task = Task(
                cursor.getString(cursor.getColumnIndex(TasksContract.Columns.TASK_NAME)),
                cursor.getString(cursor.getColumnIndex(TasksContract.Columns.TASK_DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndex(TasksContract.Columns.TASK_SORT_ORDER))
            )
            task.id= cursor.getLong(cursor.getColumnIndex(TasksContract.Columns.ID))
            holder.tli_name.text= task.name
            holder.tli_description.text= task.description
            holder.tli_edit.visibility= View.VISIBLE
            holder.tli_delete.visibility= View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}