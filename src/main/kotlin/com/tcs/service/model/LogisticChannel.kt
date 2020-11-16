package com.tcs.service.model

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "logistic-channel")
data class LogisticChannel (
        val logisticGroupNumber: Int?,
        val storeNumber: Long?,
        val startDate: String?,
        val deliveryStream: Int?,
        val warehouseNumber: Int?,
        val endDate: String?
)