package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "店铺相关接口")
@Slf4j
public class ShopController {

    public static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 设置店铺的营业状态
     * @param status
     * @return
     */
    @PutMapping("/{status}")
    @ApiOperation("设置店铺的营业状态")
    //    @CachePut(cacheNames = "usercach",key = "#employeeLoginDTO.username")//将数据存储到缓存，key=usercach::admin
//    @CachePut(cacheNames = "usercach",key = "#result.username")
//    @CachePut(cacheNames = "usercach",key = "#p1.username")
//    @CachePut(cacheNames = "usercach",key = "#a1.username")
//    @CachePut(cacheNames = "usercach",key = "#root.args[0].username")
//    @Cacheable(cacheNames = "usercach",key = "#employeeLoginDTO.username")//若缓存没有该数据则执行方法并存入缓存，如果有就直接取缓存数据
//    @CacheEvict(cacheNames = "usercach",key = "#employeeLoginDTO.username")
//    @CacheEvict(cacheNames = "usercach",allEntries = true)  //删除全部缓存数据
    public Result setStatus(@PathVariable Integer status){
        log.info("设置店铺的营业状态为：{}",status == 1 ? "营业中" : "打烊中");
        redisTemplate.opsForValue().set(KEY,status);
        return Result.success();
    }

    @GetMapping("/status")
    @ApiOperation("获取店铺的营业状态")
    public Result<Integer> getStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("获取到店铺的营业状态为：{}",status == 1 ? "营业中" : "打烊中");
        return Result.success(status);

    }
}

