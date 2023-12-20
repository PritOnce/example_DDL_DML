package com.dam.example_DDL_DML.Universidad;

public interface DbQueryUniversidad {

    public static final String PATH_MATRICULA_JUNIO = "C:\\Users\\alumne-DAM.DESKTOP-ODR0FV0\\Desktop\\Victor\\example_DDL_DML\\src\\main\\resources\\matricula_junio.json";
    public static final String PATH_MATRICULA_SEPTIEMBRE = "C:\\Users\\alumne-DAM.DESKTOP-ODR0FV0\\Desktop\\Victor\\example_DDL_DML\\src\\main\\resources\\matricula_septiembre.json";

    public static final String PATH_MATRICULA_JUNIO_LOCAL = "C:\\Users\\prito\\OneDrive\\Escritorio\\Accés a dades\\example_DDL_DML\\src\\main\\resources\\matricula_junio.json";
    public static final String PATH_MATRICULA_SEPTIEMBRE_LOCAL = "C:\\Users\\prito\\OneDrive\\Escritorio\\Accés a dades\\example_DDL_DML\\src\\main\\resources\\matricula_septiembre.json";

    public static final String JDBC_DB_URL_UNIVERSIDAD ="jdbc:mysql://localhost:3306/universidad2";
    public static final String JDBC_USER_UNIVERSIDAD = "root";
    public static final String JDBC_PASS_UNIVERSIDAD = "familiaonce112004";


    //DML
    public static final String SELECT_USER_ACTIVE = "SELECT * FROM ESTUDIANTES WHERE ESTADO = 1";

    public static final String SELECT_STUDENT_BY_NIF = "SELECT * FROM ESTUDIANTES WHERE NIF = ?";

    public static final String SELECT_AGE_BY_NIF = "SELECT CALCULAR_EDAD(?) AS EDAD_RESULT";

    public static final String UPDATE_COLUMN = "UPDATE ESTUDIANTES " +
            "SET ULTIMA_ACTUALIZACION = CURRENT_TIMESTAMP(), ENVIADO = ? " +
            "WHERE NIF = ?";

}
