package com.mobiletask.model

data class Category(
    var id: Int = 0,
    var name: String = "",
    var description: String = "",
    var image: String = "",
    var slug: String = "",
    var children: ArrayList<CategoryChildren> = ArrayList()
) {

    override fun toString(): String {
        return name
    }

    data class CategoryChildren(
        var id: Int = 0,
        var name: String = "",
        var description: String = "",
        var image: String = "",
        var slug: String = "",
        var circle_icon: String = "",
        var disable_shipping: String = ""
    )
}