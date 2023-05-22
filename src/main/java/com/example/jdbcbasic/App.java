package com.example.jdbcbasic;

import com.example.jdbcbasic.model.Student;
import com.example.jdbcbasic.repo.StudentRepo;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) {

        try(StudentRepo repo = new StudentRepo();){
            repo.open();
            //insert(repo);
            findAll(repo);
            //findById(repo,2);

        }catch (Exception e){
            e.getStackTrace();
        }

    }
    private static void findAll(StudentRepo repo) throws SQLException {
        repo.findAll().forEach(System.out::println);
    }

    private static void findById(StudentRepo repo,Integer id) throws SQLException {
        Student student = repo.findById(id).orElse(null);
        System.out.println(student);

    }

    private static void insert(StudentRepo repo) throws SQLException {
        repo.save(new Student(1,"Ploy"));
        repo.save(new Student(2,"Ploy"));
        repo.save(new Student(3,"Ploy"));
        repo.save(new Student(4,"Ploy"));
        repo.save(new Student(5,"Ploy"));
    }
}
