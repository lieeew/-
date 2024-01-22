package com.leikooo.copy;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/22
 * @description
 */
public class copyTest {
    public static void main(String[] args) {
        Teacher teacherWang = Teacher.builder().student(new Student("小明")).teacherName("王老师").build();
        Teacher teacherLiu = teacherWang.copy();
        teacherLiu.setTeacherName("刘老师");
        teacherLiu.getStudent().setStudentName("小红");
        System.out.println("teacherLiu = " + teacherLiu);
        System.out.println("teacherWang = " + teacherWang);
    }
}
