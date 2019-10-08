package com.diamond.it.desihisaab.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diamond.it.desihisaab.R
import com.diamond.it.desihisaab.common.FinalTotal
import com.diamond.it.desihisaab.model.data_model.Calculation
import com.diamond.it.desihisaab.uc.CustomTextWatcher
import kotlinx.android.synthetic.main.raw_desi_hisaab_adapter_item.view.*
import java.lang.Exception

class DesiHisaabAdapter(
    context: Context,
    list: ArrayList<Calculation>,
    finalTotal: FinalTotal
) :
    RecyclerView.Adapter<DesiHisaabAdapter.HisaabHolder>() {

    private var mContext: Context
    private var list: ArrayList<Calculation>
    private var finalTotal: FinalTotal

    init {
        mContext = context
        this.list = list
        this.finalTotal = finalTotal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HisaabHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.raw_desi_hisaab_adapter_item, parent, false)
        return HisaabHolder(view, list,finalTotal)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HisaabHolder, position: Int) {
        val calculation = list[position]
        if(calculation.quantity != 0.0 || calculation.price != 0.0) {
            holder.edQuantity.setText(calculation.quantity.toString())
            holder.edPrice.setText(calculation.price.toString())
            holder.edTotal.setText(calculation.total.toString())
        }
    }

    class HisaabHolder(itemView: View, list: ArrayList<Calculation>, finalTotal: FinalTotal) : RecyclerView.ViewHolder(itemView) {
        var edQuantity: EditText
        var edPrice: EditText
        var edTotal: TextView
        var list: ArrayList<Calculation>

        init {
            edQuantity = itemView.edQuantity
            edPrice = itemView.edPrice
            edTotal = itemView.edTotal
            this.list = list

            edQuantity.addTextChangedListener(CustomTextWatcher(object : CustomTextWatcher.MyTextWatcher {
                override fun onValueChanged(string: String) {
                    val calculation: Calculation = list[adapterPosition]
                    try {
                        calculation.quantity = if (string.isEmpty()) 0.0 else string.toDouble()
                    }catch (e:Exception){calculation.quantity = 0.0}
                    val temp = calculation.quantity * calculation.price
                    val f = String.format("%.2f",temp)
                    calculation.total = f.toDouble()
                    edTotal.text = calculation.total.toString()
                    calculateFinalTotal(finalTotal)
                }
            }))

            edPrice.addTextChangedListener(CustomTextWatcher(object : CustomTextWatcher.MyTextWatcher {
                override fun onValueChanged(string: String) {
                    val calculation: Calculation = list[adapterPosition]
                    try {
                        calculation.price = if (string.isEmpty()) 0.0 else string.toDouble()
                    }catch (e:Exception){calculation.price = 0.0}
                    val temp = calculation.quantity * calculation.price
                    val f = String.format("%.2f",temp)
                    calculation.total = f.toDouble()
                    edTotal.text = calculation.total.toString()
                    calculateFinalTotal(finalTotal)
                }
            }))

        }

        fun calculateFinalTotal(finalTotal: FinalTotal) {
            var sum = 0.0
            for (value in list) {
                sum = sum + value.total
            }
            val f = String.format("%.2f",sum)
            finalTotal.onFinalTotalChanged(f)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun getList():ArrayList<Calculation>
    {
        return this.list
    }


}

