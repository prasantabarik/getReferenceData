package com.tcs.service.controller

import com.microsoft.applicationinsights.TelemetryClient
import com.tcs.service.constant.ExceptionMessage.BAD_REQUEST
import com.tcs.service.constant.ExceptionMessage.NO_DATA_FOUND
import com.tcs.service.constant.ServiceLabels.API_TAG_DESC
import com.tcs.service.constant.ServiceLabels.API_TAG_NAME
import com.tcs.service.constant.ServiceLabels.DATA_FOUND
import com.tcs.service.constant.ServiceLabels.MEDIA_TYPE
import com.tcs.service.constant.ServiceLabels.OPENAPI_DELETE_BY_ID_DEF
import com.tcs.service.constant.ServiceLabels.OPENAPI_GET_BY_ID_DEF
import com.tcs.service.constant.ServiceLabels.OPENAPI_GET_DEF
import com.tcs.service.constant.ServiceLabels.OPENAPI_POST_DEF
import com.tcs.service.constant.ServiceLabels.OPENAPI_PUT_DEF
import com.tcs.service.constant.URLPath.BASE_URI
import com.tcs.service.constant.URLPath.GET_ALL_URI
import com.tcs.service.constant.URLPath.GET_BY_ID_URI
import com.tcs.service.constant.URLPath.POST_PUT_DELETE_URI
import com.tcs.service.model.*
import com.tcs.service.repository.CustomRepositoryImpl
import com.tcs.service.repository.DeliveryChannelRepository
import com.tcs.service.repository.DeliveryStreamRepository
import com.tcs.service.repository.LogisticChannelRepository
import com.tcs.service.service.Service
import com.tcs.service.validator.BaseValidator
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.apache.logging.log4j.kotlin.logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.reflect.typeOf

@RestController
@RequestMapping(BASE_URI)
@Tag(name = API_TAG_NAME, description = API_TAG_DESC)
class Controller(private val service: Service,
                    private val validator: BaseValidator,private  val repo: DeliveryChannelRepository,
                 private val repo1: DeliveryStreamRepository,
                 private val repo2: LogisticChannelRepository
                   ) {

    val logger = logger()

    /**
     * TelemetryClient is responsible for sending events to App Insights
     */
    @Autowired
    lateinit var telemetryClient: TelemetryClient

    /**
     * This is a sample of the GET Endpoint
     */
    @Operation(summary = OPENAPI_GET_DEF, description = OPENAPI_GET_DEF, tags = [API_TAG_NAME])
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = DATA_FOUND, content = [
            (Content(mediaType = MEDIA_TYPE, array = (
                    ArraySchema(schema = Schema(implementation = BaseModel::class)))))]),
        ApiResponse(responseCode = "400", description = BAD_REQUEST, content = [Content()]),
        ApiResponse(responseCode = "404", description = NO_DATA_FOUND, content = [Content()])]
    )
    @RequestMapping(value = ["/delivererById"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun get(@RequestParam delivererNumber:Int?): ResponseEntity<ServiceResponse> {
        logger.info("Get All")
        var records = mutableListOf<Any>()
        service.getDeliverer(delivererNumber).forEach { model -> records.add(model.data)}
        return ResponseEntity.ok(ServiceResponse("200",
                "SUCCESS", records))
    }
    @Operation(summary = OPENAPI_GET_DEF, description = OPENAPI_GET_DEF, tags = [API_TAG_NAME])
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = DATA_FOUND, content = [
            (Content(mediaType = MEDIA_TYPE, array = (
                    ArraySchema(schema = Schema(implementation = BaseModel::class)))))]),
        ApiResponse(responseCode = "400", description = BAD_REQUEST, content = [Content()]),
        ApiResponse(responseCode = "404", description = NO_DATA_FOUND, content = [Content()])]
    )
    @RequestMapping(value = ["/deliveryscheduleall"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getdeliveryscheduleall(): ResponseEntity<ServiceResponse> {
        logger.info("Get by query")
        var records = mutableListOf<DeliveryScheduleModel>()
        records =  service.getDeliveryscheduleService()
        return ResponseEntity.ok(ServiceResponse("200",
                "SUCCESS", records))
    }
// delivery channel by query
    @Operation(summary = OPENAPI_GET_DEF, description = OPENAPI_GET_DEF, tags = [API_TAG_NAME])
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = DATA_FOUND, content = [
            (Content(mediaType = MEDIA_TYPE, array = (
                    ArraySchema(schema = Schema(implementation = BaseModel::class)))))]),
        ApiResponse(responseCode = "400", description = BAD_REQUEST, content = [Content()]),
        ApiResponse(responseCode = "404", description = NO_DATA_FOUND, content = [Content()])]
    )
    @RequestMapping(value = ["/deliveryChannel"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun get(@RequestParam (required = false) storeNumber:Long?,
            @RequestParam(required = false) deliveryStream:Int?,
            @RequestParam(required = false) startDate:String,
            @RequestParam(required = false) endDate:String): ResponseEntity<ServiceResponse> {
        logger.info("Get by query")
        var records = mutableListOf<DeliveryChannel>()
       records =  service.getDeliveryChannelService(storeNumber,deliveryStream,startDate,endDate)
        return ResponseEntity.ok(ServiceResponse("200",
                "SUCCESS", records))
    }

    // delivery channel all
    @Operation(summary = OPENAPI_GET_DEF, description = OPENAPI_GET_DEF, tags = [API_TAG_NAME])
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = DATA_FOUND, content = [
            (Content(mediaType = MEDIA_TYPE, array = (
                    ArraySchema(schema = Schema(implementation = BaseModel::class)))))]),
        ApiResponse(responseCode = "400", description = BAD_REQUEST, content = [Content()]),
        ApiResponse(responseCode = "404", description = NO_DATA_FOUND, content = [Content()])]
    )
    @RequestMapping(value = ["/deliveryChannelAll"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllDelChannel(): ResponseEntity<ServiceResponse> {
        logger.info("Get All")
        var records = mutableListOf<Any>()
        records = repo.findAll().toMutableList()
        return ResponseEntity.ok(ServiceResponse("200",
                "SUCCESS", records))
    }
    @Operation(summary = OPENAPI_GET_DEF, description = OPENAPI_GET_DEF, tags = [API_TAG_NAME])
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = DATA_FOUND, content = [
            (Content(mediaType = MEDIA_TYPE, array = (
                    ArraySchema(schema = Schema(implementation = BaseModel::class)))))]),
        ApiResponse(responseCode = "400", description = BAD_REQUEST, content = [Content()]),
        ApiResponse(responseCode = "404", description = NO_DATA_FOUND, content = [Content()])]
    )
    @RequestMapping(value = ["/deliveryschedulesorted"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getdelschedulesorted(@RequestParam (required = false) storeNumber:Long?,
                             @RequestParam(required = false) deliveryStream:Int?,
                             @RequestParam(required = false) startDate:String,
                             @RequestParam(required = false) endDate:String): ResponseEntity<ServiceResponse> {
        logger.info("Get All")
        var records = mutableListOf<Any>()
        records = service.getDeliveryScheduleSorted(storeNumber,deliveryStream,startDate,endDate).toMutableList()
        return ResponseEntity.ok(ServiceResponse("200",
                "SUCCESS", records))
    }

    //Delivery Stream all
    @Operation(summary = OPENAPI_GET_DEF, description = OPENAPI_GET_DEF, tags = [API_TAG_NAME])
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = DATA_FOUND, content = [
            (Content(mediaType = MEDIA_TYPE, array = (
                    ArraySchema(schema = Schema(implementation = BaseModel::class)))))]),
        ApiResponse(responseCode = "400", description = BAD_REQUEST, content = [Content()]),
        ApiResponse(responseCode = "404", description = NO_DATA_FOUND, content = [Content()])]
    )
    @RequestMapping(value = ["/deliveryStreamAll"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllDelStream(): ResponseEntity<ServiceResponse> {
        logger.info("Get All")
        var records = mutableListOf<Any>()
        records = repo1.findAll().toMutableList()
        return ResponseEntity.ok(ServiceResponse("200",
                "SUCCESS", records))
    }

    // Delivery Stream By Number

    @Operation(summary = OPENAPI_GET_DEF, description = OPENAPI_GET_DEF, tags = [API_TAG_NAME])
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = DATA_FOUND, content = [
            (Content(mediaType = MEDIA_TYPE, array = (
                    ArraySchema(schema = Schema(implementation = BaseModel::class)))))]),
        ApiResponse(responseCode = "400", description = BAD_REQUEST, content = [Content()]),
        ApiResponse(responseCode = "404", description = NO_DATA_FOUND, content = [Content()])]
    )
    @RequestMapping(value = ["/deliveryStream/{deliveryStreamNumber}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getDeliveryStreamByNumber(@PathVariable deliveryStreamNumber: Int): ResponseEntity<ServiceResponse> {
        logger.info("Get by query in del stream")
        println(deliveryStreamNumber)
        var records = mutableListOf<DeliveryStream>()
        records =  service.getDeliveryStreamService(deliveryStreamNumber)
        println(records)
        return ResponseEntity.ok(ServiceResponse("200",
                "SUCCESS", records))
    }

    //Logistic Channel All
    @Operation(summary = OPENAPI_GET_DEF, description = OPENAPI_GET_DEF, tags = [API_TAG_NAME])
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = DATA_FOUND, content = [
            (Content(mediaType = MEDIA_TYPE, array = (
                    ArraySchema(schema = Schema(implementation = BaseModel::class)))))]),
        ApiResponse(responseCode = "400", description = BAD_REQUEST, content = [Content()]),
        ApiResponse(responseCode = "404", description = NO_DATA_FOUND, content = [Content()])]
    )
    @RequestMapping(value = ["/logisticChannelAll"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllLogisticChannel(): ResponseEntity<ServiceResponse> {
        logger.info("Get All")
        var records = mutableListOf<Any>()
        records = repo2.findAll().toMutableList()
        return ResponseEntity.ok(ServiceResponse("200",
                "SUCCESS", records))
    }

    // logistic channel by query
    @Operation(summary = OPENAPI_GET_DEF, description = OPENAPI_GET_DEF, tags = [API_TAG_NAME])
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = DATA_FOUND, content = [
            (Content(mediaType = MEDIA_TYPE, array = (
                    ArraySchema(schema = Schema(implementation = BaseModel::class)))))]),
        ApiResponse(responseCode = "400", description = BAD_REQUEST, content = [Content()]),
        ApiResponse(responseCode = "404", description = NO_DATA_FOUND, content = [Content()])]
    )
    @RequestMapping(value = ["/logisticChannel"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getLogisticByQuery(@RequestParam (required = false) storeNumber:Long?,
                           @RequestParam(required = false) deliveryStream: Int?,
                           @RequestParam(required = false) startDate:String,
                           @RequestParam(required = false) endDate:String): ResponseEntity<ServiceResponse> {
        logger.info("Get by query in logistic")
        var records = mutableListOf<LogisticChannel>()
        records =  service.getLogisticChannelService(storeNumber,deliveryStream,startDate,endDate)
        return ResponseEntity.ok(ServiceResponse("200",
                "SUCCESS", records))
    }
    /**
     * This is a sample of the GET Endpoint
     */
//    @Operation(summary = OPENAPI_GET_BY_ID_DEF, description = OPENAPI_GET_BY_ID_DEF, tags = [API_TAG_NAME])
//    @ApiResponses(value = [
//        ApiResponse(responseCode = "200", description = DATA_FOUND, content = [Content(schema = Schema(implementation = ServiceResponse::class))]),
//        ApiResponse(responseCode = "400", description = BAD_REQUEST, content = [Content()]),
//        ApiResponse(responseCode = "404", description = NO_DATA_FOUND, content = [Content()])]
//    )
//   @RequestMapping(value = [GET_BY_ID_URI], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
//    fun getById(
//            @PathVariable id: String
//    ): ResponseEntity<ServiceResponse> {
//        logger.info("Get by id: ")
//        return ResponseEntity.ok(ServiceResponse("200",
//                "SUCCESS", service.getById(id).data))
//    }

    /**
     * This is a sample of the POST Endpoint
     */
//    @Operation(summary = OPENAPI_POST_DEF, description = OPENAPI_POST_DEF, tags = [API_TAG_NAME])
//    @ApiResponses(value = [
//        ApiResponse(responseCode = "200", description = DATA_FOUND, content = [Content(schema = Schema(implementation = BaseModel::class))]),
//        ApiResponse(responseCode = "400", description = BAD_REQUEST, content = [Content()]),
//        ApiResponse(responseCode = "404", description = NO_DATA_FOUND, content = [Content()])]
//    )
//    @RequestMapping(value = [POST_PUT_DELETE_URI], method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
//    fun post(@RequestBody model: Model): ResponseEntity<ServiceResponse> {
//        service.save(model)
//        return ResponseEntity.ok(ServiceResponse("200",
//                "SUCCESS", "Data Successfully Inserted"))
//    }



//    @Operation(summary = OPENAPI_PUT_DEF, description = OPENAPI_PUT_DEF, tags = [API_TAG_NAME])
//    @ApiResponses(value = [
//        ApiResponse(responseCode = "200", description = DATA_FOUND, content = [Content(schema = Schema(implementation = BaseModel::class))]),
//        ApiResponse(responseCode = "400", description = BAD_REQUEST, content = [Content()]),
//        ApiResponse(responseCode = "404", description = NO_DATA_FOUND, content = [Content()])]
//    )
//    @RequestMapping(value = [POST_PUT_DELETE_URI], method = [RequestMethod.PUT], produces = [MediaType.APPLICATION_JSON_VALUE])
//    fun put(@RequestBody model: Model): ResponseEntity<ServiceResponse> {
//        service.save(model)
//        return ResponseEntity.ok(ServiceResponse("200",
//                "SUCCESS", "Data Successfully Updated"))
//    }
//
//
//
//    @Operation(summary = OPENAPI_DELETE_BY_ID_DEF, description = OPENAPI_DELETE_BY_ID_DEF, tags = [API_TAG_NAME])
//    @ApiResponses(value = [
//        ApiResponse(responseCode = "200", description = DATA_FOUND, content = [Content(schema = Schema(implementation = BaseModel::class))]),
//        ApiResponse(responseCode = "400", description = BAD_REQUEST, content = [Content()]),
//        ApiResponse(responseCode = "404", description = NO_DATA_FOUND, content = [Content()])]
//    )
//    @RequestMapping(value = [POST_PUT_DELETE_URI], method = [RequestMethod.DELETE], produces = [MediaType.APPLICATION_JSON_VALUE])
//    fun delete(@PathVariable id: String): ResponseEntity<ServiceResponse> {
//        service.delete(id)
//        return ResponseEntity.ok(ServiceResponse("200",
//                "SUCCESS", "Data Successfully Deleted"))
//    }
}


