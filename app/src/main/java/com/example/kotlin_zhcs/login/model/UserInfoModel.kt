package com.example.kotlin_zhcs.login.model

import java.io.Serializable

class UserInfoModel : Serializable {

    var code = 0
    var msg : String? = null
    var user : User? = null

    class User : Serializable{

        var avatar : String? = null
        var balance : Int = 0   //账户余额
        var email : String? = null
        var idCard : String? = null
        var nickName : String? = null
        var phonenumber : String? = null
        var score : Int = 0     //用户积分
        var sex : String? = null    //0 男 1 女
        var userId : Int? = null
        var userName : String? = null
        override fun toString(): String {
            return "User(avatar=$avatar, balance=$balance, email=$email, idCard=$idCard, nickName=$nickName, phonenumber=$phonenumber, score=$score, sex=$sex, userId=$userId, userName=$userName)"
        }



    }

}