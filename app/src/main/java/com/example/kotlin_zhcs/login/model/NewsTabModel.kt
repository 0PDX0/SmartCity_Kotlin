package com.example.kotlin_zhcs.login.model

class NewsTabModel {

    var code : Int? = null
    var msg : String? = null
    var data : List<DataBean>?  = null



    class DataBean{

        var id : Int? = null
        var appType : String? = null
        var name : String? = null
        var sort : Int? = null
        var status : String? = null
        override fun toString(): String {
            return "DataBean(id=$id, appType=$appType, name=$name, sort=$sort, status=$status)"
        }


    }

    override fun toString(): String {
        return "NewsTabModel(code=$code, msg=$msg, data=$data)"
    }

}