package com.diamond.it.desihisaab.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diamond.it.desihisaab.R
import com.diamond.it.desihisaab.common.FinalTotal
import com.diamond.it.desihisaab.model.Calculation
import com.diamond.it.desihisaab.uc.CustomTextWatcher
import kotlinx.android.synthetic.main.raw_desi_hisaab_adapter_item.view.*

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
        var view = LayoutInflater.from(mContext).inflate(R.layout.raw_desi_hisaab_adapter_item, parent, false)
        return HisaabHolder(view, list,finalTotal)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HisaabHolder, position: Int) {
    }

    class HisaabHolder(itemView: View, list: ArrayList<Calculation>,finalTotal: FinalTotal) : RecyclerView.ViewHolder(itemView) {
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
                    var calculation: Calculation = list[adapterPosition]
                    calculation.quantity = if (string.isEmpty()) 0.0 else string.toDouble()
                    calculation.total = calculation.quantity * calculation.price
                    edTotal.text = calculation.total.toString()
                    calculateFinalTotal(finalTotal)
                }
            }))

            edPrice.addTextChangedListener(CustomTextWatcher(object : CustomTextWatcher.MyTextWatcher {
                override fun onValueChanged(string: String) {
                    var calculation: Calculation = list[adapterPosition]
                    calculation.price = if (string.isEmpty()) 0.0 else string.toDouble()
                    calculation.total = calculation.quantity * calculation.price
                    edTotal.text = calculation.total.toString()
                    calculateFinalTotal(finalTotal)
                }
            }))
            var calculation: Calculation = list[adapterPosition]
            edQuantity.setText(calculation.quantity)
        }

        fun calculateFinalTotal(finalTotal: FinalTotal) {
            var sum = 0.0
            for ((index, value) in list.withIndex()) {
                Log.e("DesiHisaabAdapter", "index = " + index + "  value = " + value)
                sum = sum + value.total
            }
            Log.e("DesiHisaabAdapter", "F.Total = " + sum)
            finalTotal.onFinalTotalChanged(sum.toString())
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}

