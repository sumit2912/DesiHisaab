package com.dalakiya.infotech.desihisaab.adapter

import android.content.Context
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.dalakiya.infotech.desihisaab.MyApplication
import com.dalakiya.infotech.desihisaab.R
import com.dalakiya.infotech.desihisaab.common.FinalTotal
import com.dalakiya.infotech.desihisaab.common.FocusListener
import com.dalakiya.infotech.desihisaab.model.data_model.Calculation
import com.dalakiya.infotech.desihisaab.pref.PrefConst
import com.dalakiya.infotech.desihisaab.uc.CustomTextWatcher
import com.dalakiya.infotech.desihisaab.uc.KeyBoardEditText
import com.dalakiya.infotech.desihisaab.utils.Utils
import kotlinx.android.synthetic.main.raw_desi_hisaab_adapter_item.view.*

class DesiHisaabAdapter(
    context: Context,
    private var list: ArrayList<Calculation>,
    private var finalTotal: FinalTotal,
    private var srNoVisibility: Int,
    private val focusListener: FocusListener,
    private var actionListener: ActionListener
) :
    RecyclerView.Adapter<DesiHisaabAdapter.HisaabHolder>() {
    private var mContext: Context = context
    private var mVisibilityOfSrNo: Int = srNoVisibility

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HisaabHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.raw_desi_hisaab_adapter_item, parent, false)
        return HisaabHolder(view, mContext, list, finalTotal, actionListener,focusListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HisaabHolder, position: Int, payload: MutableList<Any>) {
        if (payload.isNotEmpty()) {
            // payload could be batched. If so, we only care about the final state.
            when (payload.last()) {
                UPDATE_SR_NO_VISIBILITY -> {
                    if (mVisibilityOfSrNo == View.GONE) {
                        holder.clSrNo.post { Utils.scaleGone(context = mContext, holder.clSrNo) }
                    } else {
                        holder.clSrNo.post { Utils.scaleVisible(context = mContext, holder.clSrNo) }
                    }
                }
            }
        } else {
            onBindViewHolder(holder, position)
        }
    }


    override fun onBindViewHolder(holder: HisaabHolder, position: Int) {
        holder.tvSrNo.text = (position + 1).toString()
        holder.clSrNo.visibility = mVisibilityOfSrNo
        holder.bindCalculation(list)
    }

    class HisaabHolder(
        itemView: View,
        mContext: Context,
        var list: ArrayList<Calculation>,
        finalTotal: FinalTotal,
        actionListener: ActionListener,
        focusListener: FocusListener
    ) : RecyclerView.ViewHolder(itemView) {
        var tvSrNo: TextView = itemView.tvSrNo
        var clSrNo: ConstraintLayout = itemView.clSrNo
        var edQuantity: KeyBoardEditText = itemView.edQuantity
        var edPrice: KeyBoardEditText = itemView.edPrice
        var edTotal: TextView = itemView.edTotal

        init {
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

            edQuantity.setOnFocusChangeListener { v, hasFocus ->
                focusListener.onFocusChange("Quantity",adapterPosition,hasFocus)
                Log.e("Quantity", hasFocus.toString())
            }

            /*edQuantity.setKeyBoardBackListener {
                focusListener.onFocusChange("Quantity",-1,false)
                focusListener.onFocusChange("Price",-1,false)
            }*/

            edQuantity.setOnClickListener {
                edQuantity.requestFocus()
                focusListener.onFocusChange("Quantity",adapterPosition,true)
                focusListener.onFocusChange("Price",-1,false)
            }

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

            edPrice.setOnFocusChangeListener { v, hasFocus ->
                focusListener.onFocusChange("Price",adapterPosition,hasFocus)
                Log.e("Price", hasFocus.toString())
            }

            /*edPrice.setKeyBoardBackListener {
                focusListener.onFocusChange("Quantity",-1,false)
                focusListener.onFocusChange("Price",-1,false)
            }*/

            edPrice.setOnClickListener {
                edPrice.requestFocus()
                focusListener.onFocusChange("Price",adapterPosition,true)
                focusListener.onFocusChange("Quantity",-1,false)
            }

            edPrice.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (adapterPosition == list.size - 1) {
                        actionListener.onAction(adapterPosition)
                        return@setOnEditorActionListener true
                    }

                }
                false;
            }

        }

        fun calculateFinalTotal(finalTotal: FinalTotal) {
            var sum = 0.0
            for (value in list) {
                sum += value.total
            }
            val f = String.format("%.2f", sum)
            finalTotal.onFinalTotalChanged(f)
        }

        fun bindCalculation(list: ArrayList<Calculation>) {
            if (list[adapterPosition].quantity != 0.0 || list[adapterPosition].price != 0.0) {
                edQuantity.setText(list[adapterPosition].quantity.toString())
                edPrice.setText(list[adapterPosition].price.toString())
                edTotal.text = list[adapterPosition].total.toString()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun getList(): ArrayList<Calculation> {
        return this.list
    }

    interface ActionListener {
        fun onAction(adapterPosition: Int)
    }

    fun toggleDeleteVisibility() {
        mVisibilityOfSrNo =
            if (mVisibilityOfSrNo == View.GONE) {
                View.VISIBLE
            } else {
                View.GONE
            }
        notifyItemRangeChanged(0, list.size, UPDATE_SR_NO_VISIBILITY)
    }

    companion object {
        const val UPDATE_SR_NO_VISIBILITY = 1
    }
}

