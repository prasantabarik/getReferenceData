package com.tcs.service.repository

import com.tcs.service.model.*
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface DelivererRepository : MongoRepository<Deliverer, Int>, CustomRepository {
fun findByDelivererNumber(delivererNumber:Int?): List<Deliverer>
}

@Repository
interface DeliveryChannelRepository : MongoRepository<DeliveryChannel, Int> {

}

@Repository
interface DeliveryStreamRepository : MongoRepository<DeliveryStream, Int> {

    fun findByDeliveryStreamNumber(deliveryStreamNumber: Int?) : MutableList<DeliveryStream>
}

@Repository
interface LogisticChannelRepository : MongoRepository<LogisticChannel, Int>{}
@Repository
interface DeliveryScheduleRepository : MongoRepository<DeliveryScheduleModel, Int> {

}
