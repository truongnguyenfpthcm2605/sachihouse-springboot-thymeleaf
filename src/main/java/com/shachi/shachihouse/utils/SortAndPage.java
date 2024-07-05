package com.shachi.shachihouse.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class SortAndPage {

    public static Sort getSortDown(String keySort) {
        return Sort.by(Sort.Direction.DESC, keySort);
    }

    public static Pageable getPage(Integer number,Integer pageSize,Sort sort){
        return PageRequest.of(number, pageSize,sort);
    }



}
