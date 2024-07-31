package com.android.blinkitadminjc.model

data class Product(
    var productRandomId:String? =null,
    var productQuantity:Int?=null,
    var productUnit:String?=null,
    var productPrice:Int?=null,
    var productStock:Int?=null,
    var productCategory:String?=null,
    var productType:String?=null,
    var itemCount:Int?=null,
    var productTitle:String?=null,
    var adminUID:String?=null,
    var imageUrls: MutableList<String>? = mutableListOf()

)
