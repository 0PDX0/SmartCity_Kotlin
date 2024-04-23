package com.example.kotlin_zhcs.login.model

class NewsComment {

    var code : Int = 0
    var msg : String? = null
    var total : Int = 0
    var rows : ArrayList<RowsBean>? = null

    class RowsBean{

        var commentData : String? = null
        var content : String? = null
        var id : Int = 0
        var likeNum : Int? = null
        var newsId : Int = 0
        var userId : Int = 0
        var userName : String? = null
        var appType : String? = null
        var newsTitle : String? = null
        var commentDate : String? = null

    }

}
















