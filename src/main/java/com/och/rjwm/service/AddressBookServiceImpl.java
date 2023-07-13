package com.och.rjwm.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.och.rjwm.entity.AddressBook;
import com.och.rjwm.mapper.AddressBookMapper;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService{
}
