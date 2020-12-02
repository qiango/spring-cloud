package com.ribbonconsumer.thirdparty.stream;

import java.util.*;
import java.util.stream.Collectors;

/**
 * jdk-1.8流式操作
 *
 * @Authod: wangqian
 * @Date: 2020-12-01  10:29
 */

public class User {
    private long id;
    private int age;
    private String name;
    private String address;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(long id, String name, int age, String address) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static void main(String[] args) {
        User user1 = new User(1, "jim", 23, "北京");
        User user2 = new User(2, "tom", 24, "武汉");
        User user3 = new User(3, "echo", 25, "深圳");
        User user4 = new User(4, "jerry", 26, "上海");
        User user5 = new User(5, "bob", 27, "北京");
        //数据库、集合 ： 存数据的
        // Stream：计算和处理数据交给 Stream
        List<User> users = Arrays.asList(user1, user2, user3, user4, user5);
        users.stream()
                .filter(u -> u.getId() % 2 == 0)//筛选
                .filter(u -> u.getAge() > 24)
                .map(u -> u.getName().toUpperCase())//取单体对象做操作并只保留单个字段
                .sorted(Comparator.reverseOrder())//倒排序
                .limit(1)
                .sorted(Comparator.naturalOrder())//顺排序
                .forEach(System.out::println);

        List<Long> collect = users.stream().map(User::getId).collect(Collectors.toList());//取集合中一个元素放入另一个集合

        Map<String, Integer> map = new HashMap<>();
        map.put("q", 1);
        map.put("w", 2);
        map.put("e", 3);
        map.keySet().stream().filter(m -> m.equals("w"));
        map.values().stream().filter(m -> m == 2).forEach(System.out::println);
        map.values().forEach(System.out::println);
    }
}
