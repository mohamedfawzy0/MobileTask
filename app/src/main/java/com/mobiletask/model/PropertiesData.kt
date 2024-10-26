package com.mobiletask.model


data class PropertiesData(

    var id: Int = 0,
    var name: String = "",
    var description: String = "",
    var slug: String = "",
    var parent: String = "",
    var list: Boolean = false,
    var type: String = "",
    var value: String = "",
    var other_value: String = "",
    var options: ArrayList<Option> = ArrayList(),
    var updatedValue: String="",
    var isOther:Boolean=false,
    var otherInput: String? = null

) {
    data class Option(

        var id: Int = 0,
        var name: String = "",
        var slug: String = "",
        var parent: String = "",
        var child: String = "",

        )
}