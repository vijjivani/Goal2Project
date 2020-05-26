package com.cts.goal2project.ViewModel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.cts.goal2project.Model.EmpModelClass
import com.cts.goal2project.R
import kotlinx.android.synthetic.main.employee_list.view.*

class EmpAdapter : RecyclerView.Adapter<EmpAdapter.EmployeeViewHolder>{

    private val context: Context
    private val mEmployeelist: List<EmpModelClass>
    private val mRowlayout: Int

    constructor(context: Context, mEmployeelist: List<EmpModelClass>, mRowlayout: Int) {
        this.context = context
        this.mEmployeelist = mEmployeelist
        this.mRowlayout = mRowlayout
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {

        val view=  LayoutInflater.from(parent.context).inflate(R.layout.employee_list,parent,false)
        return EmployeeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mEmployeelist.size
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {


        holder.firstName?.text=mEmployeelist[position].firstName
        holder.lastName?.text=mEmployeelist[position].lastName
        holder.dest?.text=mEmployeelist[position].dest
        holder.email?.text=mEmployeelist[position].userEmail
        holder.containerView.setOnClickListener{
            Toast.makeText(context, "Clicked on: " + holder.firstName.text, Toast.LENGTH_SHORT).show();
        }

    }


    class EmployeeViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView){
        val firstName=containerView.firstName
        val lastName=containerView.lastName
        val dest=containerView.dest
        val email=containerView.email

    }
}