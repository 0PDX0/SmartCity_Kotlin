package com.example.kotlin_zhcs.ui.notifications.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.kotlin_zhcs.App.Companion.getToken
import com.example.kotlin_zhcs.R
import com.example.kotlin_zhcs.Util.Util.show
import com.example.kotlin_zhcs.databinding.ActivityUserInfoBinding
import com.example.kotlin_zhcs.login.Repository.api
import com.example.kotlin_zhcs.login.Repository.coroutine
import com.example.kotlin_zhcs.login.model.UserInfoModel
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class UserInfoActivity : AppCompatActivity(),View.OnClickListener {

    lateinit var binding : ActivityUserInfoBinding

    var user : UserInfoModel.User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.clName.setOnClickListener(this)
        binding.clSex.setOnClickListener(this)
        binding.clEmail.setOnClickListener(this)
        binding.clIdCard.setOnClickListener(this)
        binding.clPhone.setOnClickListener(this)
        binding.clAvatar.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        var intent : Intent = Intent(this, UpUserInfoActivity::class.java)
        intent.putExtra("data", user)

        if (v != null){
            if (v.id == R.id.cl_name){

                intent.putExtra("tab","昵称")
                startActivity(intent)

            }else if (v.id == R.id.cl_sex){

                var view : View = LayoutInflater.from(this).inflate(R.layout.sex_select_popup, null, false)
                var man = view.findViewById<ImageView>(R.id.man)
                var girl = view.findViewById<ImageView>(R.id.girl)

                var popup : PopupWindow = PopupWindow(view)

                popup.width = LinearLayout.LayoutParams.MATCH_PARENT
                popup.height = LinearLayout.LayoutParams.MATCH_PARENT

                view.findViewById<ConstraintLayout>(R.id.root).setOnClickListener {
                    popup.dismiss()
                }

                view.findViewById<LinearLayout>(R.id.is_man).setOnClickListener {
                    man.visibility = View.VISIBLE
                    girl.visibility = View.GONE

                    user!!.sex = "0"

                    upUserSex(user!!){
                        if (it == "1"){
                            "修改成功".show()
                            binding.userSex.text = "男"
                        }else {
                            "修改失败".show()
                            binding.userSex.text = "女"
                        }
                    }

                    Handler(Looper.getMainLooper()).postDelayed(Runnable {
                        popup.dismiss()
                    }, 250)
                }

                view.findViewById<LinearLayout>(R.id.is_girl).setOnClickListener {
                    girl.visibility = View.VISIBLE
                    man.visibility = View.GONE

                    user!!.sex = "1"

                    upUserSex(user!!){
                        if (it == "1"){
                            "修改成功".show()
                            binding.userSex.text = "女"
                        }else {
                            "修改失败".show()
                            binding.userSex.text = "男"
                        }
                    }

                    Handler(Looper.getMainLooper()).postDelayed(Runnable {
                        popup.dismiss()
                    }, 250)
                }

                if (user!!.sex == "1"){
                    girl.visibility = View.VISIBLE
                    man.visibility = View.GONE
                }else {
                    man.visibility = View.VISIBLE
                    girl.visibility = View.GONE
                }

                popup.showAtLocation(binding.root, Gravity.CENTER, 0, 0)

            }else if (v.id == R.id.cl_email){

                intent.putExtra("tab","邮箱")
                startActivity(intent)


            }else if (v.id == R.id.cl_id_card){

                intent.putExtra("tab","身份证")
                startActivity(intent)


            }else if (v.id == R.id.cl_phone){

                intent.putExtra("tab","手机号")
                startActivity(intent)


            }else if(v.id == R.id.cl_avatar){

                var intent : Intent = Intent(this, UpUserAvatarActivity::class.java)
                intent.putExtra("user", user)
                startActivity(intent)

            }
        }
    }

    fun upUserSex(user : UserInfoModel.User, re:(msg : String) -> Unit ){
        var map = mapOf<String, Any?>(
            "nickName" to user!!.nickName,
            "phonenumber" to user!!.phonenumber,
            "sex" to user!!.sex,
            "email" to user!!.email,
            "idCard" to user!!.idCard
        )

        var mediaType : MediaType = "application/json;charset=utf-8".toMediaType()

        var body = JSONObject(map).toString().toRequestBody(mediaType)

        coroutine(Dispatchers.Main) {

            var response = api.putUserInfo(getToken(),body)

            if (response.toString().contains("200")){
                re("1")
            }else {
                re("0")
            }
        }
    }


    fun back(view: View) {
        finish()
    }

    override fun onStart() {
        super.onStart()
        Log.e("pdx1","onStart")
        coroutine(Dispatchers.Main){

            var userinfo : UserInfoModel = api.getUserInfo(getToken())

            if (userinfo.code == 200){

                user = userinfo.user

                if (user != null){

                    binding.userName.text = user!!.nickName

                    binding.userSex.text = "${if (user!!.sex == "1") "女" else "男"}"

                    binding.userEmail.text = user!!.email

                    binding.userIdCard.text = "${user!!.idCard?.substring(0,2)}**************${user!!.idCard?.substring(user!!.idCard!!.length - 4,user!!.idCard!!.length)}"

                    binding.userTel.text = "${user!!.phonenumber!!.substring(0, 3)}******${user!!.phonenumber!!.substring(user!!.phonenumber!!.length - 2, user!!.phonenumber!!.length)}"

                    Glide.with(this@UserInfoActivity).load(userinfo!!.user!!.avatar)
                        .error(R.mipmap.user_icon).transform(CircleCrop()).into(binding.userAvatar)
                }
            }

        }

    }



}




















