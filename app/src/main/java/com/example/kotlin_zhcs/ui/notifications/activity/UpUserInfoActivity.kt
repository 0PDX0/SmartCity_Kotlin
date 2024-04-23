package com.example.kotlin_zhcs.ui.notifications.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_zhcs.App.Companion.getToken
import com.example.kotlin_zhcs.Util.Util.show
import com.example.kotlin_zhcs.databinding.ActivityUpUserInfoBinding
import com.example.kotlin_zhcs.login.Repository.api
import com.example.kotlin_zhcs.login.Repository.coroutine
import com.example.kotlin_zhcs.login.model.UserInfoModel
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class UpUserInfoActivity : AppCompatActivity() {

    lateinit var binding : ActivityUpUserInfoBinding
    lateinit var user : UserInfoModel.User
    var tab : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getSerializableExtra("data") as UserInfoModel.User
        tab = intent.getStringExtra("tab")

        if (tab == "昵称"){

            binding.title.text = "修改昵称"
            binding.upEt.setText(user.nickName)
            binding.upEt.hint = "请输入您要修改的昵称"

        }else if (tab == "邮箱"){

            binding.title.text = "修改邮箱"
            binding.upEt.setText(user.email)
            binding.upEt.hint = "请输入您要修改的邮箱"

        }else if (tab == "身份证"){

            binding.title.text = "修改身份证号"
            binding.upEt.setText(user.idCard)
            binding.upEt.hint = "请输入您要修改的身份证号"

        }else if (tab == "手机号"){

            binding.title.text = "修改手机号"
            binding.upEt.setText(user.phonenumber)
            binding.upEt.hint = "请输入您要修改的手机号"

        }

    }


    override fun onStart() {
        super.onStart()


    }

    /**
     * 结束当前页面
     */
    fun back(view: View) {
        finish()
    }

    /**
     * 保存
     */
    fun save(view: View) {

        if (binding.upEt.text.toString().trim() == ""){
            "修改项不能为空"
            return
        }

        if (tab == "昵称"){

            user.nickName = binding.upEt.text.toString().trim()

        }else if (tab == "邮箱"){

            user.email = binding.upEt.text.toString().trim()

        }else if (tab == "身份证"){

            user.idCard = binding.upEt.text.toString().trim()

        }else if (tab == "手机号"){

            user.phonenumber = binding.upEt.text.toString().trim()

        }

        var map = mapOf<String, Any?>(
            "nickName" to user.nickName,
            "phonenumber" to user.phonenumber,
            "sex" to user.sex,
            "email" to user.email,
            "idCard" to user.idCard
        )

        var mediaType : MediaType = "application/json;charset=utf-8".toMediaType()

        var body = JSONObject(map).toString().toRequestBody(mediaType)

        coroutine(Dispatchers.Main) {

            var response = api.putUserInfo(getToken(),body)

            if (response.toString().contains("200")){
                "修改成功".show()
                finish()
            }else {
                "修改失败".show()
            }


        }

    }
}