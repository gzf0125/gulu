package com.gulu.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.gulu.Common.BaseContext;
import com.gulu.Common.R;
import com.gulu.Entity.AddressBook;
import com.gulu.Service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addressBook")
@Slf4j
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    /**
     * 添加收获地址
     * @param addressBook
     * @return
     */
    @PostMapping
    public R<AddressBook> save(@RequestBody AddressBook addressBook){
        addressBook.setUserId((BaseContext.getCurrentId()));
        log.info("addressbook:{}",addressBook);
        addressBookService.save(addressBook);
        return R.success(addressBook);
    }
    //查询用户所有的地址信息
    @GetMapping("/list")
    public R<List<AddressBook>> list(AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("addressbook:{}",addressBook);
        LambdaQueryWrapper<AddressBook> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(addressBook.getUserId()!=null,AddressBook::getUserId,addressBook.getUserId());
        queryWrapper.orderByDesc(AddressBook::getUpdateTime);
        List<AddressBook> addressBooks = addressBookService.list(queryWrapper);
        return R.success(addressBooks);
    }

    /**
     * 修改地址时回显菜品信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<AddressBook> update(@PathVariable Long id){
        AddressBook addressBook = addressBookService.getById(id);
        if (addressBook != null) {
            return R.success(addressBook);
        } else {
            return R.error("没有找到该对象");
        }
    }

    @PutMapping
    public R<String> updateAddress(@RequestBody AddressBook addressBook){
        addressBookService.updateById(addressBook);
        return R.success("修改地址成功");
    }

    /**
     * 设置默认地址，先把全部默认字段改为0，再把当前字段变为1
     * @param addressBook
     * @return
     */
    @PutMapping("/default")
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook){
        log.info(addressBook.toString());
        LambdaUpdateWrapper<AddressBook> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.set(AddressBook::getIsDefault,0);
        addressBookService.update(updateWrapper);
        addressBook.setIsDefault(1);
        addressBookService.updateById(addressBook);
        return R.success(addressBook);
    }

    /**
     * 删除地址
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam Long ids){
        log.info(ids.toString());
        addressBookService.removeById(ids);
        return R.success("删除地址成功");
    }

    @GetMapping("/default")
    public R<AddressBook> getdefault(){
        Long currentId = BaseContext.getCurrentId();
        LambdaQueryWrapper<AddressBook> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId,currentId);
        queryWrapper.eq(AddressBook::getIsDefault,1);
        AddressBook one = addressBookService.getOne(queryWrapper);
        if (one!=null){
            return R.success(one);
        }else return R.error("无默认地址");
    }


}
