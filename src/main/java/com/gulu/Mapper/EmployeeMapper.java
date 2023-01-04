package com.gulu.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulu.Entity.Employee;
import org.apache.ibatis.annotations.Mapper;
//mapper接口
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
