package com.dam.example_DDL_DML;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;

@SpringBootApplication
public class ExampleDdlDmlApplication implements DbQueryConstants {

	public static void main(String[] args) {
		SpringApplication.run(ExampleDdlDmlApplication.class, args);
		Connection connObj = null;
		Statement stmtObj = null;
		try {
			connObj = DriverManager.getConnection(JDBC_DB_URL,
					JDBC_USER,
					JDBC_PASS);
			stmtObj = connObj.createStatement();
			stmtObj.executeUpdate(CREATE_DATABASE_QUERY);
		} catch(Exception sqlException){
			sqlException.printStackTrace();
		} finally {
			try {
				if (stmtObj != null) {
					stmtObj.close();
				}
				if(connObj != null){
					connObj.close();
				}
			} catch(Exception e){

			}
		}
	}

}
