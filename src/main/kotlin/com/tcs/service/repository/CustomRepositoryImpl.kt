package com.tcs.service.repository

import com.tcs.service.model.*
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Repository

@Repository
class CustomRepositoryImpl(private val mongoTemplate: MongoTemplate) : CustomRepository{


    override fun getAllByDesc(modDesc: String): List<BaseModel>{
        return mongoTemplate.find(Query(Criteria.where("modDesc").`is`(modDesc)),
        BaseModel::class.java)
    }



fun getDeliveryScheduleSorted(storeNumber:Long?,deliveryStream:Int?,startDate:String,endDate:String)
        : MutableList<DeliveryScheduleModel> {
        var query = Query()

        var criteria = Criteria()
//        criteria.andOperator(
//                Criteria.where("storeNumber").isEqualTo(storeNumber),
//                Criteria.where("deliveryStreamNumber").isEqualTo(deliveryStream)
//                , Criteria.where("startDate").lte(startDate)
//                .orOperator(
//                        Criteria.where("endDate").gte(startDate),
//                        Criteria.where("endDate").`is`(null))
//
//        )

    criteria.andOperator(
            Criteria.where("storeNumber").isEqualTo(storeNumber),
            Criteria.where("deliveryStreamNumber").isEqualTo(deliveryStream)
            , Criteria.where("startDate").lte(endDate).orOperator(
              Criteria.where("endDate").gte(startDate),
                Criteria.where("endDate").`is`(null))



    )

        var toPrint = query.addCriteria(criteria);
        println(toPrint)


        return mongoTemplate.find(toPrint,
                DeliveryScheduleModel::class.java)

    }


//del schedule for moment
    fun getDeliveryScheduleForMomentCustom(storeNumber:Long?,deliveryStream:Int?,startDate:String)
        :MutableList<DeliveryScheduleModel> {
    var query = Query()

    var criteria = Criteria()

    criteria.andOperator(
            Criteria.where("storeNumber").isEqualTo(storeNumber),
            Criteria.where("deliveryStreamNumber").isEqualTo(deliveryStream)
            , Criteria.where("startDate").lte(startDate).orOperator(
            Criteria.where("endDate").gte(startDate)
            ,Criteria.where("endDate").`is`(null)
    )
    )

    var toPrint = query.addCriteria(criteria);

println(toPrint)

    return mongoTemplate.find(toPrint,
            DeliveryScheduleModel::class.java)

}




fun getDeliveryChannel(storeNumber:Long?,deliveryStream:Int?,startDate:String): MutableList<DeliveryChannel> {
    var query = Query()
//    var criteria = startDate?.let {
//        Criteria.where("storeNumber").isEqualTo(storeNumber).and("deliveryStream").isEqualTo(deliveryStream).and("startDate").and("endDate").lte(endDate)
//    }

    var criteria = Criteria()
    criteria.andOperator(
            Criteria.where("storeNumber").isEqualTo(storeNumber),
            Criteria.where("deliveryStream").isEqualTo(deliveryStream),
            Criteria.where("startDate").lte(startDate).orOperator(
                    Criteria.where("endDate").gte(startDate)
                    ,Criteria.where("endDate").`is`(null)
            )
//            Criteria.where("startDate").lte(startDate),
//            Criteria.where("endDate").lte(endDate).orOperator
//            (Criteria.where("endDate").isEqualTo(null))
    )



    var toPrint = query.addCriteria(criteria);
    println(toPrint)
//    return mongoTemplate.find(query,
//            DeliveryChannel::class.java)

    return mongoTemplate.find(toPrint,
            DeliveryChannel::class.java)

            }

    // for logistic one
    fun getLogisticChannelRepo(storeNumber: Long?,deliveryStream:Int?,startDate:String):
            MutableList<LogisticChannel> {
        var query = Query()

        var criteria = Criteria()
        var criteria1 = Criteria.where("endDate").gte(startDate)
                .orOperator(Criteria.where("endDate").`is`(null))

        criteria.andOperator(
                Criteria.where("storeNumber").isEqualTo(storeNumber),
                Criteria.where("deliveryStream").isEqualTo(deliveryStream),
                Criteria.where("startDate").lte(startDate)
                //,criteria1
                        .orOperator(
                        Criteria.where("endDate").gte(startDate),
                        Criteria.where("endDate").`is`(null) )

        )


        var toPrint = query.addCriteria(criteria);
        println("check in logistic")
        println(toPrint)
        return mongoTemplate.find(toPrint,
                LogisticChannel::class.java)
    }





}