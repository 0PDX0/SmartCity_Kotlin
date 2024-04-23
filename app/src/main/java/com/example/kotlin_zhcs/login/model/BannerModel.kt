package com.example.kotlin_zhcs.login.model

class BannerModel {
    /**
     * code :  200
     * msg : 查询成功
     * rows : [{"id":14,"sort":1,"advTitle":"测试首页轮播","advImg":"http://152.136.210.130:7777/profile/upload/image/2021/04/26/183e63c6-a59d-4551-a5b4-7055ff7a9575.jpg","servModule":"新闻","targetId":1,"type":"2"}]
     * total : 1
     */
    var code: String? = null
    var msg: String? = null
    var total: String? = null
    var rows: List<RowsBean>? = null

    class RowsBean {
        /**
         * id : 14
         * sort : 1
         * advTitle : 测试首页轮播
         * advImg : http://152.136.210.130:7777/profile/upload/image/2021/04/26/183e63c6-a59d-4551-a5b4-7055ff7a9575.jpg
         * servModule : 新闻
         * targetId : 1
         * type : 2
         */
        var id = 0
        var sort = 0
        var advTitle: String? = null
        var advImg: String? = null
        var servModule: String? = null
        var targetId = 0
        var type: String? = null

    }
}