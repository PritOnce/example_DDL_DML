package com.dam.example_DDL_DML.Universidad;

import com.dam.example_DDL_DML.ExampleDdlDmlApplication;
import org.springframework.boot.SpringApplication;

import java.sql.*;

public class DdmUniversidad implements DbQueryUniversidad{

    public static void main(String[] args) {
        SpringApplication.run(ExampleDdlDmlApplication.class, args);
        Connection connObj = null;
        Statement stmtObj = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        CallableStatement cbs = null;
        try {
            connObj = DriverManager.getConnection(JDBC_DB_URL_UNIVERSIDAD,
                    JDBC_USER_UNIVERSIDAD,
                    JDBC_PASS_UNIVERSIDAD);
            stmtObj = connObj.createStatement();

            if(args.length == 0){
                getUserNoParams(stmtObj, rs);
            }else if (args.length == 1){
                getUserByNif(pst, rs, args[0], connObj);
            }else if(args.length == 2){
                getAgeByNif(cbs, rs, args[0], connObj);
            }

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

    public static void getUserNoParams(Statement st, ResultSet rs) throws SQLException {
        rs = st.executeQuery(SELECT_USER_NO_PARAMS);

        while(rs.next()){
            System.out.println("ID: " + rs.getString("id") + "; NIF: " + rs.getString("nif")
                    + "; FECHA NACIMIENTO: " + rs.getString("fecha_nacimiento") + "; ESTADO: " + rs.getString("estado") );
        }
    }

    public static void getUserByNif(PreparedStatement pst, ResultSet rs, String arg, Connection connObj) throws SQLException {
        pst = connObj.prepareStatement(SELECT_STUDENT_BY_NIF);

        pst.setString(1, arg);
        rs = pst.executeQuery();

        while(rs.next()){
            System.out.println("ID: " + rs.getString("id") + "; NIF: " + rs.getString("nif")
                    + "; FECHA NACIMIENTO: " + rs.getString("fecha_nacimiento") + "; ESTADO: " + rs.getString("estado") );
        }
    }

    public static void getAgeByNif(CallableStatement cbs, ResultSet rs, String arg, Connection connObj) throws SQLException {
        cbs = connObj.prepareCall(SELECT_AGE_BY_BIRTH);

        cbs.setString(1, arg);
        rs = cbs.executeQuery();

        while(rs.next()){
            System.out.println("AGE: " + rs.getString("edad_result"));
        }
    }

}
