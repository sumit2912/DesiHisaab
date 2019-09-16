package com.diamond.it.desihisaab.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.diamond.it.desihisaab.R
import com.diamond.it.desihisaab.model.Calculation
import kotlinx.android.synthetic.main.raw_desi_hisaab_adapter_item.view.*

class DesiHisaabAdapter(context: Context, list: ArrayList<Calculation>) :
    RecyclerView.Adapter<DesiHisaabAdapter.HisaabHolder>() {
    private var mContext: Context
    private var list: ArrayList<Calculation>

    init {
        mContext = context
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HisaabHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.raw_desi_hisaab_adapter_item, parent, false)
        return HisaabHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HisaabHolder, position: Int) {
    }

    class HisaabHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val edQuantity:EditText = itemView.edQuantity
        val edPrice:EditText = itemView.edPrice
        val edTotal:TextView = itemView.edTotal

    }
}

