package com.binish.todoapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.binish.todoapp.R
import kotlinx.android.synthetic.main.layout_input_dialog.*

class InputDialogFragment(val listener: InputDialogInteraction): DialogFragment() {

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_input_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        imageButtonDoneInput.setOnClickListener {
            listener.onAdded(editTextCheckListTitle.text.toString())
            dialog?.dismiss()
        }
        imageButtonCloseInput.setOnClickListener {
            dialog?.dismiss()
        }
    }

    fun interface InputDialogInteraction{
        fun onAdded(name: String)
    }
}