package com.example.kotlin_zhcs.login.model

class NewsShowModel {

    var code = 0
    var msg : String? = null
    var data : DataBean? = null

    class DataBean {

        var id = 0
        var appType : String? = null
        var cover : String? = null
        var title : String? = null
        var subTitle : String? = null
        var content : String? = null
        var status : String? = null
        var publishData : String? = null
        var tags : Any? = null
        var commentNum : Any? = null
        var likeNum = 0
        var readNum : Int? = null
        var type : String? = null
        var top : String? = null
        var hot : String? = null
        var createTime : String? = null


    }

}