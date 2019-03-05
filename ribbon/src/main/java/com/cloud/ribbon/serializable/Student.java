package com.cloud.ribbon.serializable;

import java.io.*;

/**
 * @author qian.wang
 * @description:深度拷贝 利用序列化
 * @date 2019/3/5
 */
public class Student implements Serializable {

    public static final long  serialVersionUID=-787284728758242L;

    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    //深度拷贝
    public Object deepClone() throws IOException,ClassNotFoundException{
        //序列化,转成字节存在内存中
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(this);

        //反序列化
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream objectInputStream=new ObjectInputStream(byteArrayInputStream);
        return objectInputStream.readObject();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Student student=new Student();
        student.setAge(10);
        student.setName("wangqian");

        Student student1= (Student) student.deepClone();
        System.out.println(student);
        System.out.println(student1);
    }

}
