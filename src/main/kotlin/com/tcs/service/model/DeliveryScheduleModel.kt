package com.tcs.service.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection="delivery-schedule")
data class DeliveryScheduleModel(
        var id: String? = null,
        var storeNumber: Long?,
        var deliveryStreamNumber: Int?,
        var deliveryStreamName: String?,
        var schemaName: String?,
        var deliverySchemaType: Int?,
        var startDate: String?,
        var endDate: String?,
        var notes: String?,
        var timeTable: List<TimeTableModel>?,
        var createdBy : String?,
        var creationDateTime: String?,
        var updatedBy : String?,
        var updateDateTime : String?



) {
}