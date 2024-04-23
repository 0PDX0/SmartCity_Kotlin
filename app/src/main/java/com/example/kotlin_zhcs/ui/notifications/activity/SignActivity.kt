package com.example.kotlin_zhcs.ui.notifications.activity

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_zhcs.App
import com.example.kotlin_zhcs.Util.Util.show
import com.example.kotlin_zhcs.databinding.ActivitySignBinding
import com.example.kotlin_zhcs.login.Repository
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class SignActivity : AppCompatActivity() {

    lateinit var binding : ActivitySignBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSign()
    }

    /**
     *注册
     */
    private fun initSign() {

        binding.signBtn.setOnClickListener {

            if (binding.signPassword.text.toString() == binding.signOkPassword.text.toString()){

                Repository.coroutine(Dispatchers.Main) {

                    var map = mapOf<String, Any>(
                        "userName" to binding.signUsername.text.toString(),
                        "nickName" to binding.signName.text.toString(),
                        "password" to binding.signPassword.text.toString(),
                        "phonenumber" to binding.signTel.text.toString(),
                        "sex" to "${if (binding.signSex.text.toString() == "女") "1" else "0"}",
                        "email" to binding.signEmail.text.toString(),
                        "idCard" to binding.signIdCard.text.toString()
                    )

                    var mediaType: MediaType = "application/json;charset=utf-8".toMediaType()

                    val body: RequestBody = JSONObject(map).toString().toRequestBody(mediaType)

                    var response = Repository.api.postSign(body)

                    if (response.toString().contains("200")) {
                        "注册成功".show()
                        finish()
                    } else {
                        var reStr = response.toString()
                        "${reStr.substring(reStr.indexOf("msg") + 4, reStr.indexOf(","))}".show()
                    }

                }
            }else {
                "两次密码输入不一致，请重新输入".show()
            }

        }



        /*初始化性别选择*/
        initSex()

    }

    /**
     * 性别选择
     */
    private fun initSex() {
        //性别选择
        binding.signMan.setOnClickListener {
            binding.signMan.animation = App.getAnimationLogin()

            binding.signMan.setColorFilter(Color.parseColor("#0C7DFF"))
            var bck_man = binding.signMan.layoutParams
            binding.signMan.layoutParams = twoOrThree(bck_man, true)

            binding.signGirl.setColorFilter(Color.parseColor("#AAAAAA"))
            var bck_girl = binding.signGirl.layoutParams
            binding.signGirl.layoutParams = twoOrThree(bck_girl, false)

            binding.signSex.text = "男"
            binding.signSex.setTextColor(Color.parseColor("#000000"))
        }
        binding.signGirl.setOnClickListener {
            binding.signGirl.animation = App.getAnimationTopAndBottom()

            binding.signGirl.setColorFilter(Color.parseColor("#FFC0CB"))
            var bck_girl = binding.signGirl.layoutParams
            binding.signGirl.layoutParams = twoOrThree(bck_girl, true)

            binding.signMan.setColorFilter(Color.parseColor("#AAAAAA"))
            var bck_man = binding.signMan.layoutParams
            binding.signMan.layoutParams = twoOrThree(bck_man, false)

            binding.signSex.text = "女"
            binding.signSex.setTextColor(Color.parseColor("#000000"))
        }
    }

    //转20或30dp
    fun twoOrThree(params : ViewGroup.LayoutParams, is_ : Boolean) : ViewGroup.LayoutParams {
        if (is_){
            params.width = dp2px(30f)
            params.height = dp2px(30f)
        }else {
            params.width = dp2px(20f)
            params.height = dp2px(20f)
        }

        return params
    }


    //dp转px
    fun dp2px(dp : Float) : Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt();
    }


    fun back(view : View?){
        finish()
    }
}




























