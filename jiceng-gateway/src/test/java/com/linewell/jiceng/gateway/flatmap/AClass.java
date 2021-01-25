package com.linewell.jiceng.gateway.flatmap;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/***
 *  @author wping created on 2021-01-22 17:31 
 */

@Data
public class AClass {

    private Integer id;
    private String name;
    private String description;

    public AClass(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static void main(String[] args) {
//        List<String> list = Arrays.asList("a-b-c-d", "g-h-i");
//        List<String> list1 = list.stream()
//                .flatMap(s -> Arrays.stream(s.split("-")))
//                .collect(Collectors.toList());
//        System.out.println(list1);


    }
}
