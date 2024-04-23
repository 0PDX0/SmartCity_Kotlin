package com.example.kotlin_zhcs.ui.notifications.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_zhcs.App.Companion.setToken
import com.example.kotlin_zhcs.Util.Util.show
import com.example.kotlin_zhcs.databinding.ActivityLoginBinding
import com.example.kotlin_zhcs.login.Repository.api
import com.example.kotlin_zhcs.login.Repository.coroutine
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //用户登录
        initLogin()

        //用户注册
        initSign()
    }

    /**
     * 注册
     */
    private fun initSign() {

        binding.playSign.setOnClickListener {
            startActivity(Intent(this, SignActivity::class.java))
        }

    }

    /**
     * 登录
     */
    private fun initLogin() {

        binding.loginBtn.setOnClickListener {

            var map = mapOf<String, Any>(
                Pair("username", binding.loginUsername.text.toString()),
                Pair("password", binding.loginPassword.text.toString())
            )

            var mediaType : MediaType = "application/json;charset=utf-8".toMediaType()

            var json : String = JSONObject(map).toString()

            var body : RequestBody = json.toRequestBody(mediaType)
//            Log.e("pdxbody",body.contentType().toString())

            coroutine(Dispatchers.Main) {

                var respone = api.postLogin(body)

                if (respone.code == 200){
                    "登录成功".show()
                    setToken(respone.token.toString())
                    finish()
                }else{
                    "用户名不存在/密码错误".show()
                }

            }

        }

    }


    /**
     * 结束当前页面
     */
    fun back(view: View) {
        finish()
    }
}