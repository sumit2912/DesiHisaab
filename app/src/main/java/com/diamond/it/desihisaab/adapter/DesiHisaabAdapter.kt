package com.diamond.it.desihisaab.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diamond.it.desihisaab.MyApplication
import com.diamond.it.desihisaab.R
import com.diamond.it.desihisaab.common.FinalTotal
import com.diamond.it.desihisaab.model.data_model.Calculation
import com.diamond.it.desihisaab.pref.PrefConst
import com.diamond.it.desihisaab.uc.CustomTextWatcher
import kotlinx.android.synthetic.main.raw_desi_hisaab_adapter_item.view.*

class DesiHisaabAdapter(
    context: Context,
    list: ArrayList<Calculation>,
    finalTotal: FinalTotal,
    actionListener: ActionListener
) :
    RecyclerView.Adapter<DesiHisaabAdapter.HisaabHolder>() {

    private var mContext: Context = context
    private var list: ArrayList<Calculation>
    private var finalTotal: FinalTotal
    private var actionListener: ActionListener

    init {
        this.list = list
        this.finalTotal = finalTotal
        this.actionListener = actionListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HisaabHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.raw_desi_hisaab_adapter_item, parent, false)
        return HisaabHolder(view, mContext, list, finalTotal, actionListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HisaabHolder, position: Int) {
        val calculation = list[position]
        if (calculation.quantity != 0.0 || calculation.price != 0.0) {
            holder.edQuantity.setText(calculation.quantity.toString())
            holder.edPrice.setText(calculation.price.toString())
            holder.edTotal.setText(calculation.total.toString())
        }
    }

    class HisaabHolder(
        itemView: View,
        mContext: Context,
        list: ArrayList<Calculation>,
        finalTotal: FinalTotal,
        actionListener: ActionListener
    ) : RecyclerView.ViewHolder(itemView) {
        var edQuantity: EditText
        var edPrice: EditText
        var edTotal: TextView
        var list: ArrayList<Calculation>

        init {
            edQuantity = itemView.edQuantity
            edPrice = itemView.edPrice
            edTotal = itemView.edTotal
            this.list = list

            edQuantity.addTextChangedListener(CustomTextWatcher(object :
                CustomTextWatcher.MyTextWatcher {
                override fun onValueChanged(string: String) {
                    val calculation: Calculation = list[adapterPosition]
                    try {
                        calculation.quantity = if (string.isEmpty()) 0.0 else string.toDouble()
                    } catch (e: Exception) {
                        calculation.quantity = 0.0
                    }
                    val temp = calculation.quantity * calculation.price
                    val f = String.format("%.2f", temp)
                    calculation.total = f.toDouble()
                    edTotal.text = calculation.total.toString()
                    calculateFinalTotal(finalTotal)
                }
            }))

            edPrice.addTextChangedListener(CustomTextWatcher(object :
                CustomTextWatcher.MyTextWatcher {
                override fun onValueChanged(string: String) {
                    val calculation: Calculation = list[adapterPosition]
                    try {
                        calculation.price = if (string.isEmpty()) 0.0 else string.toDouble()
                    } catch (e: Exception) {
                        calculation.price = 0.0
                    }
                    if (calculation.quantity == 0.0) {
                        if (((mContext.applicationContext) as MyApplication).getAppManager()
                                .getPrefManager().getBoolean(PrefConst.PREF_DEF_QUANTITY)
                        ) {
                            calculation.quantity = 1.0
                            edQuantity.setText("1")
                        }
                    }
                    val temp = calculation.quantity * calculation.price
                    val f = String.format("%.2f", temp)
                    calculation.total = f.toDouble()
                    edTotal.text = calculation.total.toString()
                    calculateFinalTotal(finalTotal)
                }
            }))

            edPrice.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (adapterPosition == list.size - 1) {
                        actionListener.onAction("scrollTop")
                        return@setOnEditorActionListener true;
                    }

                }
                false;
            }

        }

        fun calculateFinalTotal(finalTotal: FinalTotal) {
            var sum = 0.0
            for (value in list) {
                sum = sum + value.total
            }
            val f = String.format("%.2f", sum)
            finalTotal.onFinalTotalChanged(f)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun getList(): ArrayList<Calculation> {
        return this.list
    }

    interface ActionListener {
        fun onAction(action: String)
    }
}

