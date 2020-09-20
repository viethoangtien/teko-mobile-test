package com.teko.hoangviet.androidtest.base.view.dialogfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.beetech.productmanagement.di.annotation.LayoutId
import com.teko.hoangviet.androidtest.extension.put
import com.teko.hoangviet.androidtest.R
import java.util.HashMap

abstract class BaseDialogFragment(
    val cancelable: Boolean = true,
    val isStyleFullScreen: Boolean = false
) : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isStyleFullScreen) setStyle(
            STYLE_NORMAL,
            android.R.style.Theme_Black_NoTitleBar_Fullscreen
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(javaClass.getAnnotation(LayoutId::class.java)?.value ?: throw Exception("Haven't provided layout id for dialog fragment"), container, false)
        if (!isStyleFullScreen) dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_dialog)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        isCancelable = cancelable
        dialog?.setCanceledOnTouchOutside(cancelable)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
    }

    open fun setData(data: HashMap<String, Any>) {
        if (data.isEmpty()) {
            arguments = Bundle()
            return
        }
        val bundle = Bundle()
        for ((key, value) in data) {
            bundle.put(key, value)
        }
        arguments = bundle
    }

    abstract fun initListeners()

    abstract fun initViews()
}