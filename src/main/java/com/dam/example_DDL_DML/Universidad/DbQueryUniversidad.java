package com.dam.example_DDL_DML.Universidad;

public interface DbQueryUniversidad {

    public static final String JDBC_DB_URL_UNIVERSIDAD ="jdbc:mysql://localhost:3306/universidad2";
    public static final String JDBC_USER_UNIVERSIDAD = "root";
    public static final String JDBC_PASS_UNIVERSIDAD = "familiaonce112004";


    //DML
    public static final String SELECT_USER_NO_PARAMS = "SELECT * FROM ESTUDIANTES WHERE ESTADO = 1";
    public static final String SELECT_STUDENT_BY_NIF = "SELECT * FROM ESTUDIANTES WHERE NIF = ?";

    public static final String SELECT_AGE_BY_BIRTH = "SELECT CALCULAR_EDAD(?) AS EDAD_RESULT";

}
