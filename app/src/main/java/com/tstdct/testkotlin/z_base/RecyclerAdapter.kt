package com.tstdct.testkotlin.z_base
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Dechert on 2018-02-12.
 * Company: www.chisalsoft.co
 */
abstract class RecyclerAdapter<Model>(context: Context, arrayList: ArrayList<Model>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var arrayList = arrayList
    val context = context
    var layoutRes: Int = loadLayoutRes()

    abstract fun loadLayoutRes(): Int

    override fun getItemCount(): Int {
        if (arrayList != null) {
            return arrayList!!.size
        } else {
            return 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder= ClassViewHolder(context, layoutRes, parent)
        return viewHolder
    }

    override fun onBindViewHolder(v: RecyclerView.ViewHolder?, position: Int) {
        if (arrayList != null && v!=null) {
            var m = arrayList!!.get(position)
            if(m!=null){
                setData(v.itemView,position, m)
            }else{
                Log.e("RecyclerAdapter","Model is null")
            }

        } else {
            Log.e("RecyclerAdapter", "List is null!")
        }
    }

    fun notifyDataChanged(arrayList: ArrayList<Model>?) {
        if(arrayList!=null){
            this.arrayList = arrayList
            notifyDataSetChanged()
        }else{
            Log.e("RecyclerAdapter", "List is null!")
        }

    }

    open abstract fun setData(itemView: View, position: Int, m: Model)



    class ClassViewHolder(context: Context,layoutRes: Int,parent: ViewGroup?) : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(layoutRes, parent, false)) {

    }

}