package com.gulu.Entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
/*
员工实体类
 */
@Data//可以省去代码中大量的get()、set(),toString()方法，提高代码的简介，需引入lombok
public class Employee implements Serializable {//对象的序列化处理Serializable
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String name;

    private String password;

    private String phone;

    private String sex;

    private String idNumber;//驼峰映射，需在application配置中开启该命名方法

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)//自动填充属性实现在进行插入（insert）操作时对添加了注解@TableField(fill = FieldFill.INSERT)的字段进行自动填充
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)//进行插入（insert）和更新（update）时进行自动填充。
    private Long updateUser;

}
