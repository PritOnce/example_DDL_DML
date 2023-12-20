package com.dam.example_DDL_DML.Universidad;

import com.dam.example_DDL_DML.ExampleDdlDmlApplication;
import com.dam.example_DDL_DML.Universidad.Dto.Estudiante;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.SpringApplication;

import java.io.FileReader;
import java.io.FileWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class DdmUniversidad implements DbQueryUniversidad{

    public static void main(String[] args) {
        SpringApplication.run(ExampleDdlDmlApplication.class, args);
        Connection connObj = null;
        Statement stmtObj = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        CallableStatement cbs = null;

        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();

        Gson gson = gsonBuilder.create();


        try {
            connObj = DriverManager.getConnection(JDBC_DB_URL_UNIVERSIDAD,
                    JDBC_USER_UNIVERSIDAD,
                    JDBC_PASS_UNIVERSIDAD);
            stmtObj = connObj.createStatement();

            if(args.length == 0){
                getUserActive(stmtObj, rs, gson);
            }else if (args.length == 1){
                getUserByNif(pst, rs, args[0], connObj);
            }else if(args.length == 2){
                getAgeByNif(cbs, rs, args[0], connObj);
            } else if (args.length == 3) {
                updateTimeSent(stmtObj, gson, pst, rs, connObj, args[2]);
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

    public static void getUserActive(Statement st, ResultSet rs, Gson gson) throws SQLException {
        rs = st.executeQuery(SELECT_USER_NO_PARAMS);

        while(rs.next()){
            System.out.println(rs.getInt("id") + rs.getString("nif") +
                    rs.getDate("fecha_nacimiento") + rs.getBoolean("estado") +
                    rs.getDate("fecha_matriculacion"));
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

    public static void updateTimeSent(Statement st, Gson gson, PreparedStatement pst, ResultSet rs, Connection connObj,  String arg) throws SQLException {
        rs = st.executeQuery(SELECT_USER_NO_PARAMS);

        ArrayList<Estudiante> estudiantesJunioList = new ArrayList<>();
        ArrayList<Estudiante> estudiantesSeptiembreList = new ArrayList<>();

        int comprobanteFecha = 0;

        while(rs.next()){
            Estudiante estudiante = new Estudiante(rs.getInt("id") ,rs.getString("nif"),
                    rs.getDate("fecha_nacimiento"), rs.getBoolean("estado"),
                    rs.getDate("fecha_matriculacion"), rs.getDate("ultima_actualizacion"), rs.getString("enviado"));
            comprobanteFecha = comprobarFecha(rs.getDate("fecha_matriculacion"));
            if(comprobanteFecha == 0){
                System.out.println("USUARIO NO ACTIVO: " + rs.getString("nif"));
            }else if(comprobanteFecha == 1){
                estudiantesJunioList.add(estudiante);
            } else if (comprobanteFecha == 2) {
                estudiantesSeptiembreList.add(estudiante);
            }
        }

        saveInf(PATH_MATRICULA_JUNIO_LOCAL, gson.toJson(estudiantesJunioList));
        saveInf(PATH_MATRICULA_SEPTIEMBRE_LOCAL, gson.toJson(estudiantesSeptiembreList));

        for(Estudiante estudiante : estudiantesJunioList){
            String nif = estudiante.getDni();
//            System.out.println("JUNIO: \n" + nif);
            executeSentence(pst, rs, connObj, nif, arg);
        }

        for(Estudiante estudiante : estudiantesSeptiembreList){
            String nif = estudiante.getDni();
//            System.out.println("SEPTIEMBRE: \n" + nif);
            executeSentence(pst, rs, connObj, nif, arg);
        }
    }

    private static int comprobarFecha(Date fecha_matricula){
        Date fechaInicioJunio = Date.valueOf("2024-06-01");
        Date fechaFinJunio = Date.valueOf("2024-06-30");

        Date fechaInicioSeptiembre = Date.valueOf("2024-09-01");
        Date fechaFinSeptiembre = Date.valueOf("2024-09-30");

        if( (fecha_matricula.before(fechaInicioJunio) && fecha_matricula.after(fechaFinJunio))  &&
                (fecha_matricula.before(fechaInicioSeptiembre) && fecha_matricula.after(fechaFinSeptiembre)) ){
            return 0;
        }

        if(fecha_matricula.after(fechaInicioJunio) && fecha_matricula.before(fechaFinJunio)){
            return 1;
        } else if (fecha_matricula.after(fechaInicioSeptiembre) && fecha_matricula.before(fechaFinSeptiembre)) {
            return 2;
        }
        return 0;
    }

    private static void saveInf(String path, String contenido){
        try{
            FileWriter fw = new FileWriter(path);
            fw.write(contenido);
            fw.close();
            System.out.println("Fichero escrito");
        }catch (Exception e){
            System.out.println("ERROR GENERAL saveInf. " +e.getMessage());
        }
    }

    private static void executeSentence(PreparedStatement pst, ResultSet rs, Connection connObj, String nif, String arg) throws SQLException {
        pst = connObj.prepareStatement(UPDATE_COLUMN);

        pst.setString(1, arg);
        pst.setString(2, nif);
        int elementosActualizados = pst.executeUpdate();

        if(elementosActualizados == 0){
            System.out.println("NO SE HA ACTUALIZADO EL NIF: " + nif);
        }else{
            System.out.println("ACTUALIZACION EN EL NIF " + nif + " EXITOSA");
        }
    }
}
