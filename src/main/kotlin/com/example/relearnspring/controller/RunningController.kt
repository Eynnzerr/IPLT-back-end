@file:Suppress("SpringJavaInjectionPointsAutowiringInspection")

package com.example.relearnspring.controller

import com.example.relearnspring.mapper.RunningMapper
import com.example.relearnspring.model.FileBytes
import com.example.relearnspring.model.HttpResponse
import com.example.relearnspring.model.Running
import io.swagger.annotations.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.File

@Api(
    tags = ["Running数据请求"],
    description = "以列表形式获取Running数据请求"
)
@RestController
@RequestMapping("/run")
class RunningController {

    @Autowired
    lateinit var runningMapper: RunningMapper

    //test
    @ApiOperation("测试数据连通性")

    @GetMapping("/test")
    fun testRunning(): HttpResponse {
        val list = runningMapper.testRunList()
        return HttpResponse.success("test is successful", list)
    }

    // get all position data
    @ApiOperation("获取全部数据")
    @ApiResponses(
        ApiResponse(code = 200, message = "请求成功"),
        ApiResponse(code = -1, message = "找不到数据"),
    )
    @GetMapping("/all")
    fun getAllRun(): HttpResponse {
        val list = runningMapper.getAllRun()
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
    fun getRunByBatch(@RequestParam(value = "batch", defaultValue = "27") batch: Int): HttpResponse {
        val list = runningMapper.getRunByBatch(batch)
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
        val list = runningMapper.getBatchNum()
        return if (list.isNotEmpty()) HttpResponse.success("OK", list)
        else HttpResponse.fail(-1, "No position data in database.")
    }

    @ApiOperation("更新数据库中的一条数据")
    @ApiResponses(
        ApiResponse(code = 200, message = "更新成功"),
        ApiResponse(code = -1, message = "更新失败"),
    )
    @PostMapping("/update")
    fun updateById(@RequestBody running: Running) = if (runningMapper.updateById(running) > 0) HttpResponse.success("OK")
    else HttpResponse.fail(-1, "Failed to update data of id ${running.id}")

    @ApiOperation("根据id删除数据库中的一条数据")
    @ApiImplicitParam(name = "id", value = "待删除数据唯一id", defaultValue = "-1", required = true, paramType = "query")
    @ApiResponses(
        ApiResponse(code = 200, message = "删除成功"),
        ApiResponse(code = -1, message = "删除失败"),
    )
    @GetMapping("/delete")
    fun deleteById(@RequestParam(value = "id", defaultValue = "-1") id: Int) = if (runningMapper.deleteById(id) > 0) HttpResponse.success("OK")
    else HttpResponse.fail(-1, "Failed to delete data of id $id")

    @ApiOperation("上传csv文件并存入running数据库")
    @ApiImplicitParams(
        ApiImplicitParam(name = "name", value = "文件名", defaultValue = "-1", required = true, paramType = "form"),
        ApiImplicitParam(name = "multipartFile", value = "文件字节流", defaultValue = "null", required = true, paramType = "form")
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "上传成功"),
        ApiResponse(code = -1, message = "上传失败"),
    )
    @PostMapping("/upload")
    fun receiveFile(fileBytes: FileBytes): HttpResponse {
        // println("接收文件名：${fileBytes.name} 文件比特：${fileBytes.multipartFile.bytes}")
        val file = File(fileBytes.name).apply {
            writeBytes(fileBytes.multipartFile.bytes)
        }
        val runnings = file.readLines()
            .drop(1)
            .map {
                val fields = it.split(",")
                Running(
                    id = fields[0].toInt(),
                    address = fields[1],
                    accx = fields[8].toInt(),
                    accy = fields[9].toInt(),
                    accz = fields[10].toInt(),
                    gyroscopex = fields[11].toInt(),
                    gyroscopey = fields[12].toInt(),
                    gyroscopez = fields[13].toInt(),
                    stay = fields[14].toInt(),
                    timestamp = fields[17].toLong(),
                    bsAddress = fields[18].toInt(),
                    sampleTime = fields[19].drop(1) + "," + fields[20].dropLast(1),
                    sampleBatch = fields[21].toInt()
                )
            }
        val count = runningMapper.insertCsv(runnings)
        return if (count > 0) HttpResponse.success("csv上传成功") else HttpResponse.fail(-1, "csv上传失败：未能导入数据库")
    }
}