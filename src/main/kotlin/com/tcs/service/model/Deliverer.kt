package com.tcs.service.model

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "deliverer")
data class Deliverer (
        val delivererNumber:Int?,
        val supplierNumber:Long?,
        val siteNumber: Int?
)