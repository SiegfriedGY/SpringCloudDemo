package com.siegfried.springcloud.alibaba.dao;

import com.siegfried.springcloud.alibaba.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface OrderDao {

    //创建订单
    void create(Order order);

    // 修改订单状态 from 0 to 1 (和视频里不一样，视频里两个参数第一个是userId，第二个是status)
    void finishOrder(@Param("orderId") Long orderId);
}
