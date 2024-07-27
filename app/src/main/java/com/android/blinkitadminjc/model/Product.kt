package com.android.blinkitadminjc.model

import java.util.UUID

data class Product(
    var productRandomId:String =UUID.randomUUID().toString(),
    var productQuantity:Int?=null,
    var productUnit:String?=null,
    var productPrice:Int?=null,
    var productStock:Int?=null,
    var productCategory:String?=null,
    var productType:String?=null,
    var itemCount:Int?=null,
    var productTitle:String?=null,
    var adminUID:String?=null,
    var imageUrls: List<String?>? = null

)
