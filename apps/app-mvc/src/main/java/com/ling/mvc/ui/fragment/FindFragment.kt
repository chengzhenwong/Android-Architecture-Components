package com.ling.mvc.ui.fragment

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.ling.common.app.TitleBarFragment
import com.ling.mvc.R
import com.ling.mvc.http.glide.GlideApp
import com.ling.mvc.ui.activity.HomeActivity
import com.ling.widget.view.SwitchButton
import com.ling.widget.view.text.CountdownView

/**
 * author : wangchengzhen
 * github : https://github.com/getActivity/AndroidProject-Kotlin
 * time   : 2022/6/4
 * desc   : 发现 Fragment
 */
class FindFragment : TitleBarFragment<HomeActivity>(), SwitchButton.OnCheckedChangeListener {

    companion object {

        fun newInstance(): FindFragment {
            return FindFragment()
        }
    }

    private val circleView: ImageView? by lazy { findViewById(R.id.iv_find_circle) }
    private val cornerView: ImageView? by lazy { findViewById(R.id.iv_find_corner) }
    private val switchButton: SwitchButton? by lazy { findViewById(R.id.sb_find_switch) }
    private val countdownView: CountdownView? by lazy { findViewById(R.id.cv_find_countdown) }

    override fun getLayoutId(): Int {
        return R.layout.find_fragment
    }

    override fun initView() {
        setOnClickListener(countdownView)
        switchButton?.setOnCheckedChangeListener(this)
    }

    override fun initData() {
        circleView?.let {
            // 显示圆形的 ImageView
            GlideApp.with(this)
                .load(R.mipmap.update_app_top_bg)
                .transform(MultiTransformation(CenterCrop(), CircleCrop()))
                .into(it)
        }

        cornerView?.let {
            // 显示圆角的 ImageView
            GlideApp.with(this)
                .load(R.mipmap.update_app_top_bg)
                .transform(MultiTransformation(CenterCrop(), RoundedCorners(resources.getDimension(R.dimen.dp_10).toInt())))
                .into(it)
        }
    }

    // @SingleClick
    override fun onClick(view: View) {
        if (view === countdownView) {
            toast(R.string.common_code_send_hint)
            countdownView?.start()
        }
    }

    override fun isStatusBarEnabled(): Boolean {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled()
    }

    /**
     * [SwitchButton.OnCheckedChangeListener]
     */
    override fun onCheckedChanged(button: SwitchButton, checked: Boolean) {
        toast(checked)
    }
}
