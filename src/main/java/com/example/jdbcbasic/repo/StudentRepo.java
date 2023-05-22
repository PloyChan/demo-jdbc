package com.example.jdbcbasic.repo;

import com.example.jdbcbasic.model.Student;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepo implements AutoCloseable {
    private Connection connection;

    @Override
    public void close() throws Exception {
        if(connection != null){
            connection.close();
            System.out.println("closed");
        }

    }
    public void open() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:./target/db;AUTO_SERVER=TRUE", "sa", "sa");
    }
    public void save(Student student) throws SQLException {
        if(connection == null){
            throw new IllegalStateException("must call open() first!");
        }

        Statement statement = connection.createStatement();
        statement.executeUpdate("insert into student (id ,name) values ("
                + student.getId()+", '"+student.getName()+"')");
//        try (Statement statement = connection.createStatement()) {
//            String sql = String.format("insert into student(id,name) values(%d,'%s')", student.getId(), student.getName());
//            System.out.println(sql);
//            statement.executeUpdate(sql);
//        }
    }
    public Optional<Student> findById(Integer id) throws SQLException {

        try(PreparedStatement statement = connection.prepareStatement("select id,name from student where id=?")){
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                int id1 = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                Student st = new Student(id1,name);
                return Optional.of(st);
            } else {
                return Optional.empty();
            }
        }
    }
    public List<Student> findAll() throws SQLException {
        List<Student> students = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("select id,name from student")){
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                int id1 = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                Student st = new Student(id1,name);
                students.add(st);
            }
        }
        return students;
    }
}
