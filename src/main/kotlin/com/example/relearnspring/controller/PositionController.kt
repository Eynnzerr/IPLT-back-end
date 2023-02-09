@file:Suppress("SpringJavaInjectionPointsAutowiringInspection")

package com.example.relearnspring.controller

import com.example.relearnspring.mapper.PositionMapper
import com.example.relearnspring.model.FileBytes
import com.example.relearnspring.model.HttpResponse
import com.example.relearnspring.model.Position
import com.example.relearnspring.model.Running
import io.swagger.annotations.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.io.File

@Api(
    tags = ["Position数据请求"],
    description = "以列表形式获取Position数据"
)
@RestController
@RequestMapping("/pos")
class PositionController {

    @Autowired
    private lateinit var positionMapper: PositionMapper

    // test
    @ApiOperation("测试数据连通性")
    @GetMapping("/test")
    fun testPosition(): HttpResponse {
        val list = positionMapper.testPosList()
        return HttpResponse.success("test is successful", list)
    }

    // get all position data
    @ApiOperation("获取全部数据")
    @ApiResponses(
        ApiResponse(code = 200, message = "请求成功"),
        ApiResponse(code = -1, message = "找不到数据"),
    )
    @GetMapping("/all")
    fun getAllPos(): HttpResponse {
        val list = positionMapper.getAllPos()
        return if (list.isNotEmpty()) HttpResponse.success("OK", list)
        else HttpResponse.fail(-1, "No corresponding data.")
    }

    // get position data by batch number
    @ApiOperation("获取指定批次数据")
    @ApiImplicitParam(name = "batch", value = "数据批次", defaultValue = "27", required = false, paramType = "query")
    @ApiResponses(
        ApiResponse(code = 200, message = "请求成功"),
        ApiResponse(code = -1, message = "批次不存在"),
    )
    @GetMapping("/batch")
    fun getPosByBatch(@RequestParam(value = "batch", defaultValue = "27") batch: Int): HttpResponse {
        print("Receive a request for batch selecting.")
        val list = positionMapper.getPosByBatch(batch)
        return if (list.isNotEmpty()) HttpResponse.success("OK", list)
            else HttpResponse.fail(-1, "No corresponding data.")
    }

    @ApiOperation("获取数据库中存在的批次信息")
    @ApiResponses(
        ApiResponse(code = 200, message = "请求成功"),
        ApiResponse(code = -1, message = "数据库为空"),
    )
    @GetMapping("/batch_num")
    fun getBatchNumbers(): HttpResponse {
        val list = positionMapper.getBatchNum()
        return if (list.isNotEmpty()) HttpResponse.success("OK", list)
            else HttpResponse.fail(-1, "No position data in database.")
    }

    @ApiOperation("更新数据库中的一条数据")
    @ApiResponses(
        ApiResponse(code = 200, message = "更新成功"),
        ApiResponse(code = -1, message = "更新失败"),
    )
    @PostMapping("/update")
    fun updateById(@RequestBody position: Position) = if (positionMapper.updateById(position) > 0) HttpResponse.success("OK")
    else HttpResponse.fail(-1, "Failed to update data of id ${position.id}")

    @ApiOperation("根据id删除数据库中的一条数据")
    @ApiImplicitParam(name = "id", value = "待删除数据唯一id", defaultValue = "-1", required = true, paramType = "query")
    @ApiResponses(
        ApiResponse(code = 200, message = "删除成功"),
        ApiResponse(code = -1, message = "删除失败"),
    )
    @GetMapping("/delete")
    fun deleteById(@RequestParam(value = "id", defaultValue = "-1") id: Int) = if (positionMapper.deleteById(id) > 0) HttpResponse.success("OK")
    else HttpResponse.fail(-1, "Failed to delete data of id $id")

    @PostMapping("/upload")
    fun receiveFile(fileBytes: FileBytes): HttpResponse {
        println("接收文件名：${fileBytes.name} 文件比特：${fileBytes.multipartFile.bytes}")
        File(fileBytes.name).apply {
            writeBytes(fileBytes.multipartFile.bytes)
        }
        return HttpResponse.success("连接正常")
    }
}