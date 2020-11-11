package com.binish.todoapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.binish.todoapp.R
import kotlinx.android.synthetic.main.layout_time_date.*

class TimeDateDialogFragment(val listener: TimeDateDialogInteraction): DialogFragment() {
    var isTime = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isTime = arguments?.getBoolean(IS_TIME)?: true
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_time_date, container, false)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        timePicker.visibility = if(isTime) View.VISIBLE else View.INVISIBLE
        datePicker.visibility = if(!isTime) View.VISIBLE else View.INVISIBLE
        timePicker.setIs24HourView(false)

        timeDatePickerButton.setOnClickListener {
            val value = if(isTime){
                val hour = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) timePicker.hour else timePicker.currentHour
                val minute = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) timePicker.minute else timePicker.currentMinute
                if(hour > 12 ) "${hour - 12} : $minute  PM" else "$hour : $minute  AM"
            } else{
                "${datePicker.year}-${datePicker.month + 1}-${datePicker.dayOfMonth}"
            }
            listener.onTimeDateSet(value)
            dialog?.dismiss()
        }
    }

    fun interface TimeDateDialogInteraction{
        fun onTimeDateSet(value: String)
    }

    companion object{
        const val IS_TIME = "isTime"
        fun newInstance(isTime: Boolean, listener: TimeDateDialogInteraction): TimeDateDialogFragment = TimeDateDialogFragment(listener).apply {
                arguments = Bundle().apply {
                    putBoolean(IS_TIME, isTime)
                }
            }
    }
}