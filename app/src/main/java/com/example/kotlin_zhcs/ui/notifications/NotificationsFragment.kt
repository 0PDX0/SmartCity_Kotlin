package com.example.kotlin_zhcs.ui.notifications

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.kotlin_zhcs.App.Companion.getToken
import com.example.kotlin_zhcs.R
import com.example.kotlin_zhcs.Util.CheckLoginUtils
import com.example.kotlin_zhcs.databinding.FragmentNotificationsBinding
import com.example.kotlin_zhcs.login.Repository.api
import com.example.kotlin_zhcs.login.Repository.coroutine
import com.example.kotlin_zhcs.login.model.UserInfoModel
import com.example.kotlin_zhcs.ui.guide.NetworkSettingActivity
import com.example.kotlin_zhcs.ui.notifications.Dialog.OutLoginDialog
import com.example.kotlin_zhcs.ui.notifications.activity.*
import kotlinx.coroutines.Dispatchers

class NotificationsFragment : Fragment() {

    lateinit var binding : FragmentNotificationsBinding

    //执行顺序 01
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    //执行顺序 02
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //设置选项卡的单击事件
        initTab()

        //登录
        initPlayLogin()

        //退出登录
        initExitLogin()
    }

    /**
     * 退出登录
     */
    @RequiresApi(Build.VERSION_CODES.R)
    private fun initExitLogin() {
        binding.exitLogin.setOnClickListener {
            alert()
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun alert(){
        //删除token
//            App.delToken()
//            this.onStart()

        /*方式一(因为适配第二个，稍微改了一点布局)*/
        //这里有许多方法可以实现，1.传递一个匿名方法(lambda表达式)，2.将自身传递过去，让那边调用自身的onStart方法
        var outLoginDialog : OutLoginDialog = OutLoginDialog(requireContext()) {
            this.onStart()
        }
        outLoginDialog.show()

        /*方式二*/
//            var view : View = LayoutInflater.from(context).inflate(R.layout.out_login_dialog, null, false)
//
//            var popup : PopupWindow = PopupWindow(view)
//
//            popup.width = LinearLayout.LayoutParams.MATCH_PARENT
//            popup.height = ViewGroup.LayoutParams.WRAP_CONTENT
//
//            popup.isFocusable = true    //点击外面消失
//
//            popup.setBackgroundDrawable(ColorDrawable(Color.parseColor("#99000000")))
//
//            //展示
//            popup.showAtLocation(binding.tabMenu, Gravity.BOTTOM, 0, 0)
//
//            view.findViewById<View>(R.id.cs).setOnClickListener {
//                popup.dismiss()
//            }

    }

    /**
     * 创建选项卡的单击事件
     */
    private fun initTab() {

        //点击事件(登录和退出登录单独设置监听)
        for (i in 0 until binding.tabMenu.childCount){
            binding.tabMenu.getChildAt(i).setOnClickListener {
                setMenuClick(i)
            }
        }

    }

    /**
     * 跳转
     */
    private fun setMenuClick(position : Int){
        if (position != 6){
            CheckLoginUtils.isLogin(context , false, object : CheckLogin{
                //登录调用
                override fun onAlready() {

                    if (position == 0){
                        //个人信息
                        startActivity(Intent(context, UserInfoActivity::class.java))
                    }else if (position == 1){
                        //我的钱包
                        startActivity(Intent(context, BalanceTopUpActivity::class.java))
                    }else if (position == 2){
                        //我的积分
                        return
                    }else if (position == 3){
                        //我的订单
                        startActivity(Intent(context, AllOrdersActivity::class.java))
                    }else if (position == 4){
                        //修改密码
                        startActivity(Intent(context, ChangePasswordActivity::class.java))
                    }else if (position == 5){
                        //意见反馈
                        startActivity(Intent(context, FeedBackActivity::class.java))
                    }

                }

                //未登录调用
                override fun onNone() {
                    startActivity(Intent(context, LoginActivity::class.java))
                }
            })
        }else {
            //网络设置
            startActivity(Intent(context, NetworkSettingActivity::class.java))
        }
    }

    interface CheckLogin{
        fun onAlready() : Unit
        fun onNone() : Unit
    }

    /**
     * 登录
     */
    @RequiresApi(Build.VERSION_CODES.R)
    private fun initPlayLogin() {

        binding.include.userName.setOnClickListener {
            if (getToken() == ""){
                startActivity(Intent(context, LoginActivity::class.java))
            }else {
                alert()
            }
        }

    }

//    lateinit var userinfo : UserInfoModel

    //执行顺序 03 判断用户是否登录
    override fun onStart() {
        super.onStart()

        if (getToken().trim() == ""){
            binding.include.userName.text = "登录/注册\n请点击头像登录"
        }else {
            coroutine(Dispatchers.Main){

                var userinfo : UserInfoModel = api.getUserInfo(getToken())

                binding.include.userName.text = userinfo.user!!.userName


                Glide.with(this@NotificationsFragment).load(userinfo!!.user!!.avatar)
                    .error(R.mipmap.user_icon).transform(CircleCrop()).into(binding.include.userAvatar)

            }
        }
    }

}





































