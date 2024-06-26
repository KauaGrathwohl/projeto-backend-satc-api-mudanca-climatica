package com.satc.projeto.mudancaclimatica.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PageUtil {

    public static <T> Page<T> getPage(List<T> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        List<T> sublist = start >= list.size() ? new ArrayList<>() : list.subList(start, end);
        return new PageImpl<>(sublist, pageable, list.size());
    }
}
