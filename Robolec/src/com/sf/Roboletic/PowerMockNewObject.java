package com.sf.Roboletic;

/**
 * Created by NetEase on 2016/7/21 0021.
 */
public class PowerMockNewObject {

    public static class Student{
        public String name="name";
        public int number=10000;

        public Student() {

        }

        public Student(String name, int number) {
            this.name = name;
            this.number = number;
        }

        public String doTest(){
            return "doTest";
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            System.out.println("setName method");
            this.name = name;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + getName() + '\'' +
                    '}';
        }
    }

    public String getOneStudentName(){

        Student helloStudents=new Student("bbb",10);
        String test=helloStudents.doTest();
//        helloStudents.setName(test);
        String name= helloStudents.getName();
        return name;
    }
}
