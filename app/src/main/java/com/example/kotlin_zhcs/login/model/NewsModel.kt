package com.example.kotlin_zhcs.login.model

/**
 *  created by ikun
 *  ANDROID STUDIO - version 4.0
 *  email: jinkunwei41@gmail.com
 * --------------------------------
 *
 **/
class NewsModel {
    /**
     * code : 200
     * msg : 查询成功
     * rows : [{"id":5,"cover":"/dev-api/profile/upload/image/2021/04/01/c1eb74b2-e964-4388-830a-1b606fc9699f.png","title":"测试新闻标题","subTitle":"测试新闻子标题","content":"
     *
     *内容<img src=\"/dev-api/profile/upload/image/2021/04/07/a9434ccf-5acf-4bf5-a06e-c3457c6762e9.png\"></img><\/p>","status":"Y","publishDate":"2021-04-01","tags":null,"commentNum":1,"likeNum":2,"readNum":10,"type":"2","top":"Y","hot":"N"}]
     * total : 1
     */
    var code: Int? = null
     var msg: String? = null
     var total = 0
     var rows: List<RowsBean>? = null



    class RowsBean {
        /**
         * id : 5
         * cover : /dev-api/profile/upload/image/2021/04/01/c1eb74b2-e964-4388-830a-1b606fc9699f.png
         * title : 测试新闻标题
         * subTitle : 测试新闻子标题
         * content :
         *
         *内容<img src="/dev-api/profile/upload/image/2021/04/07/a9434ccf-5acf-4bf5-a06e-c3457c6762e9.png"></img>
         * status : Y
         * publishDate : 2021-04-01
         * tags : null
         * commentNum : 1
         * likeNum : 2
         * readNum : 10
         * type : 2
         * top : Y
         * hot : N
         */
        var id: Int? = null
        var cover: String? = null
        var title: String? = null
        var subTitle: String? = null
        var content: String? = null
        var status: String? = null
        var publishDate: String? = null
        var tags: Any? = null
        var commentNum = 0
        var likeNum = 0
        var readNum = 0
        var type: String? = null
        var top: String? = null
        var hot: String? = null
        override fun toString(): String {
            return "RowsBean(id=$id, cover=$cover, title=$title, subTitle=$subTitle, content=$content, status=$status, publishDate=$publishDate, tags=$tags, commentNum=$commentNum, likeNum=$likeNum, readNum=$readNum, type=$type, top=$top, hot=$hot)"
        }


    }

    override fun toString(): String {
        return "NewsModel(code=$code, msg=$msg, total=$total, rows=$rows)"
    }
}