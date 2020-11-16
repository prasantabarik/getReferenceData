package com.tcs.service.model

data class TimeTableModel(
        var  orderDay: String?,
        var  orderTime : String? ,
        var  deliveryDay: String?,
        var  deliveryTime: String?,
        var  fillDay: String?,
        var  fillTime: String?,
        var  boxSize: Int?,
        var  initialDeliveryFlag: String?,
        var  mainDeliveryFlag: String?,
        var  startDay: String?,
        var  startTime: String?,
        var exclusion: List<Exclusion>?
)
