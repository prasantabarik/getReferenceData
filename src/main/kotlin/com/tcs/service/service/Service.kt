package com.tcs.service.service

import com.tcs.service.constant.ExceptionMessage
import com.tcs.service.error.customexception.DataNotFoundException
import com.tcs.service.model.*
import com.tcs.service.repository.*
import org.apache.logging.log4j.kotlin.logger
import org.springframework.stereotype.Service


@Service
class Service(private val deliverer: DelivererRepository,
              private val deliverchannel: DeliveryChannelRepository,
              private val deliveryStream: DeliveryStreamRepository,
              private val logistic: LogisticChannelRepository,
              private  val customQuery: CustomRepositoryImpl,
              private val deliveryschedule:DeliveryScheduleRepository ) {
    val logger = logger()
//    fun getById(id: String): Model {
//        logger.info("Before Cast")
//        return Model(repository.findById(id.toInt()).get() ?: throw DataNotFoundException(ExceptionMessage.NO_DATA_FOUND))
//    }

    fun getDeliveryStreamService(deliveryStreamNumber: Int?) : MutableList<DeliveryStream> {
        var result = deliveryStream.findByDeliveryStreamNumber(deliveryStreamNumber)

        return result
    }
    fun getDeliveryScheduleSorted(storeNumber:Long?,deliveryStream:Int?,startDate:String,endDate:String)
            : MutableList<DeliveryScheduleModel> {
        var models = mutableListOf<Model>()
        var result = customQuery.getDeliveryScheduleSorted(storeNumber,deliveryStream,startDate,endDate) ?: throw DataNotFoundException(ExceptionMessage.NO_DATA_FOUND)
        return result



    }
    fun getDeliverer(delivererNumber:Int?): MutableList<Model>{
        //The below lines of code is for POC on Mongo Template
        //repository.getAllByDesc("Sample").forEach{i -> println(i.modId)}
        var models = mutableListOf<Model>()
        var result = deliverer.findByDelivererNumber(delivererNumber) ?: throw DataNotFoundException(ExceptionMessage.NO_DATA_FOUND)
        return models
    }

    fun getDeliveryChannelService(storeNumber:Long?,deliveryStream:Int?,startDate:String,endDate:String): MutableList<DeliveryChannel> {
        //The below lines of code is for POC on Mongo Template
        //repository.getAllByDesc("Sample").forEach{i -> println(i.modId)}
        var models = mutableListOf<Model>()


            var result = customQuery.getDeliveryChannel(storeNumber, deliveryStream, startDate, endDate)
                    ?: throw DataNotFoundException(ExceptionMessage.NO_DATA_FOUND)
            //       result.forEach { entity -> models.add(Model(data = entity)) }
            return result


    }

  fun  getLogisticChannelService(storeNumber:Long?,deliveryStream:Int?,startDate:String,endDate:String) :
          MutableList<LogisticChannel> {

      var models = mutableListOf<Model>()
      var result = customQuery.getLogisticChannelRepo(storeNumber, deliveryStream, startDate, endDate)

      return result
  }

    fun getDeliveryscheduleService(): MutableList<DeliveryScheduleModel> {

        var models = mutableListOf<Model>()
        var result = deliveryschedule.findAll()
        return result.toMutableList()

    }

}