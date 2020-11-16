package com.tcs.service.model

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "delivery-stream")
data class DeliveryStream(
        val deliveryStreamNumber: Int?,
        val deliveryStreamName: String?,
        val replenishmentUsedFlag: String?,
        val createdBy: String?,
        val creationDateTime: String?,
        val updatedBy:String?,
        val updatedDateTime: String?
)