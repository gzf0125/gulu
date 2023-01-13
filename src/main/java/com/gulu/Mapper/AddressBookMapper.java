package com.gulu.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulu.Entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
