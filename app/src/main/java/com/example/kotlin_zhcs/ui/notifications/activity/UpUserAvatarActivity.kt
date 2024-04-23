package com.example.kotlin_zhcs.ui.notifications.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.kotlin_zhcs.App
import com.example.kotlin_zhcs.R
import com.example.kotlin_zhcs.Util.Util.show
import com.example.kotlin_zhcs.databinding.ActivityUpUserAvatarBinding
import com.example.kotlin_zhcs.login.Repository
import com.example.kotlin_zhcs.login.model.UserInfoModel
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.FileOutputStream

const val XC : Int = 0
const val PZ : Int = 1

class UpUserAvatarActivity : AppCompatActivity() {

    lateinit var binding : ActivityUpUserAvatarBinding
    lateinit var user : UserInfoModel.User
    lateinit var regisXc : ActivityResultLauncher<Intent>
    lateinit var regisPz : ActivityResultLauncher<Intent>
    var pathXc : String? = null

    override fun onStart() {
        super.onStart()

        Glide.with(this).load(user.avatar)
            .error(R.mipmap.user_icon).transform(CenterCrop()).into(binding.userAvatar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpUserAvatarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getSerializableExtra("user") as UserInfoModel.User
//        Log.e("pdxuser",user.toString())

        regisXc = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

            //此处是相册跳转的回调方法
            //1.获取用户选择图片返回的Uri数据
            var uri : Uri? = it.data?.data

            //2.进行手机文件的查询
            var cursor : Cursor? = contentResolver.query(uri!!,null,null,null,null,null)

            //3.Cursor游标的指针指向查询的第一个结果
            cursor!!.moveToFirst()

            //寻找索引为(获取列名为_data的索引，索引从0开始，没找到返回-1) .getColumnIndexOrThrow(String columnName)——从零开始返回指定列名称，如果不存在将抛出IllegalArgumentException 异常
            //这个path返回的是一个路径文件地址，storage/emulated/0 对应的就是你机内文件管理器的根目录
            pathXc = cursor!!.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))

            Log.e("pdxpath",pathXc.toString())  ///storage/emulated/0/Download/v2-23045b78997574a255bffc9993e3596f_1440w.webp

            //将文件路径解码为位图后再使用图片加载器加载，BitmapFactory.decodeFile.将文件路径解码为位图
            Glide.with(this).load(BitmapFactory.decodeFile(pathXc))
                .error(R.mipmap.user_icon).transform(CenterCrop()).into(binding.userAvatar)

            //准备修改服务器存储的用户头像地址
            user.avatar = pathXc

        }

        regisPz = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            //此处是相机跳转回调方法
            //获取拍照的相片
            var extras : Bundle? = it.data!!.extras

            var imageBitmap : Bitmap = extras?.get("data") as Bitmap

            //TODO 得到一个存入照片的路径
            var imagePath : String = "${getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString()}/${System.currentTimeMillis()}.jpg"

            Log.e("pdxpath1",imagePath) ///storage/emulated/0/Android/data/com.example.kotlin_zhcs/files/Download/1701519685774.jpg

            /*把位图数据保存到指定路径的图片文件*/
            savaImage(imagePath, imageBitmap)

            /*更新用户数据*/
            user.avatar = imagePath

            /*显示图片*/
//            binding.userAvatar.setImageBitmap(imageBitmap)
            Glide.with(this).load(BitmapFactory.decodeFile(imagePath))
                .error(R.mipmap.user_icon).transform(CenterCrop()).into(binding.userAvatar)
        }

        initXc()

        initPz()
    }

    /**
     * 把位图数据保存到指定路径的图片文件
     */
    private fun savaImage(imagePath: String, imageBitmap: Bitmap) {
        var fos : FileOutputStream? = null
        //根据指定的文件路径构建文件输出流对象
        val any = try {
            fos = FileOutputStream(imagePath)

            //把位图数据压缩到文件输出流中
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            fos?.close()
        }

    }

    /**
     * 从相册中选择
     */
    private fun initXc() {

        binding.playPhoto.setOnClickListener {

            //判断手机系统版本是否是23以上，23以下的版本不用申请，它在应用安装的时候就授权好了
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                //这个判断用户是否已经开启了外部存储写入的权限
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                    //在模拟器上申请READ_EXTERNAL_STORAGE 或 WRITE_EXTERNAL_STORAGE 都会申请照片与媒体
                    //在自己手机上需要相册需要READ_MEDIA_IMAGES才行
                    requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), XC)

                }else {
                    //进入这里代表用户已经授权了外部存储写入的权限，直接创建意图打开系统相册
                    regisXc.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
                }
            }
        }
    }

    /**
     * 现在拍一张
     */
    private fun initPz() {
        binding.phoneAlbum.setOnClickListener {

            //判断手机版本是否需要申请权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                //camera
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

                    requestPermissions(arrayOf(Manifest.permission.CAMERA), PZ)

                }else {

                    //这里代表用户已经授予了打开相机的权限，直接创建意图打开相机
                    regisPz.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))

                }

            }

        }
    }


    /**
     * 这里是申请权限后的回调
     * 可以判断是否成功，成功便可直接打开相册或相机
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == XC){

            //请求成功直接跳入系统相册中
            regisXc.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))

        }else if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == PZ){

            //请求成功跳入拍照界面
            regisPz.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))

        }else {
            "暂无权限".show()
        }

    }


    /**
     * 结束当前页面
     */
    fun back(view: View) {
        finish()
    }

    /**
     * 保存头像
     */
    fun save(view: View) {

        var map = mapOf<String, Any?>(
            "avatar" to user.avatar,
            "nickName" to user!!.nickName,
            "phonenumber" to user!!.phonenumber,
            "sex" to user!!.sex,
            "email" to user!!.email,
            "idCard" to user!!.idCard
        )

        var mediaType : MediaType = "application/json;charset=utf-8".toMediaType()

        var body = JSONObject(map).toString().toRequestBody(mediaType)

        Repository.coroutine(Dispatchers.Main) {

            var response = Repository.api.putUserInfo(App.getToken(),body)

            if (response.toString().contains("200")){
                "修改成功".show()
                finish()
            }else {
                "修改失败".show()
            }

        }

    }

}


/*activityResultRegistry.register("111",ActivityResultContracts.RequestPermission()){

                    }*/