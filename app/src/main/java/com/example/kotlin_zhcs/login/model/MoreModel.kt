package com.example.kotlin_zhcs.login.model

/**
 *  created by ikun
 *  ANDROID STUDIO - version 4.0
 *  email: jinkunwei41@gmail.com
 * --------------------------------
 *
 **/
class MoreModel {
    /**
     * code : 200
     * msg : 查询成功
     * rows : [{"id":17,"serviceName":"停车场","serviceDesc":"查询停车场","serviceType":"车主服务","imgUrl":"http://localhost:7777/profile/upload/image/2021/05/10/814fc6c4-de48-48a1-95f8-de3e749e348d.png","link":"park/index","sort":1,"isRecommend":"N"}]
     * total : 1
     */
     var code = 0
     var msg: String? = null
     var total = 0
     var rows: List<RowsBean>? = null



    class RowsBean {
        /**
         * id : 17
         * serviceName : 停车场
         * serviceDesc : 查询停车场
         * serviceType : 车主服务
         * imgUrl : http://localhost:7777/profile/upload/image/2021/05/10/814fc6c4-de48-48a1-95f8-de3e749e348d.png
         * link : park/index
         * sort : 1
         * isRecommend : N
         */
        var id = 0
        var serviceName: String? = null
        var serviceDesc: String? = null
        var serviceType: String? = null
        var imgUrl: String? = null
        var link: String? = null
        var sort = 0
        var isRecommend: String? = null
        override fun toString(): String {
            return "RowsBean(id=$id, serviceName=$serviceName, serviceDesc=$serviceDesc, serviceType=$serviceType, imgUrl=$imgUrl, link=$link, sort=$sort, isRecommend=$isRecommend)"
        }


    }

    override fun toString(): String {
        return "MoreModel(code=$code, msg=$msg, total=$total, rows=$rows)"
    }
}