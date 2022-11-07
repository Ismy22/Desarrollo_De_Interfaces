/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbdd_Zoo_Interfaz;

import Login.LoginUser;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

/**
 *
 * @author Ismael
 */
public class DBManagerZoo {

    // Conexión a la base de datos
    private static Connection conn = null;

    // Configuración de la conexión a la base de datos
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "zoologic";
    private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + ""
            + "?serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "1234";
    private static final String DB_MSQ_CONN_OK = "CONEXIÓN CORRECTA";
    private static final String DB_MSQ_CONN_NO = "ERROR EN LA CONEXIÓN";

    // Configuración de la tabla Animals
    private static final String DB_ANIMALS = "ANIMALS";
    private static final String DB_ANIMALS_SELECT = "SELECT * FROM " + DB_ANIMALS;
    private static final String DB_ANIMALS_SELECT_ID = "SELECT ID FROM " + DB_ANIMALS;
    private static final String DB_ANIMALS_ID = "ID_ANIMAL";
    private static final String DB_ANIMALS_NOM = "NOMBRE";
    private static final String DB_ANIMALS_ESPE = "ESPECIE";
    private static final String DB_ANIMALS_PELIGROSIDAD = "PELIGROSIDAD";
    private static final String DB_ANIMALS_PESO = "PESO";

    // Configuración de la tabla Caregivers
    private static final String DB_CAREGIVERS = "CAREGIVERS";
    private static final String DB_CAREGIVERS_SELECT = "SELECT * FROM " + DB_CAREGIVERS;
    private static final String DB_CAREGIVERS_ID_CAREGIVER = "ID_CAREGIVER";
    private static final String DB_CAREGIVERS_NOM = "NOMBRE";
    private static final String DB_CAREGIVERS_APE = "APELLIDOS";
    private static final String DB_CAREGIVERS_DNI = "DNI";
    private static final String DB_CAREGIVERS_PASS = "PASS";
    private static final String DB_CAREGIVERS_ESPE = "ESPECIALIDAD";
    private static final String DB_CAREGIVERS_CARGO = "CARGO";
    private static final String DB_CAREGIVERS_SALARIO = "SALARIO_BASE";

    // Configuración de la tabla Tasks
    private static final String DB_TASKS = "TASKS";
    private static final String DB_TASKS_SELECT = "SELECT * FROM " + DB_TASKS;
    private static final String DB_TASKS_ID_TAREA = "ID_TAREA";
    private static final String DB_TASKS_TAREA = "TAREA";
    private static final String DB_TASKS_ANIMAL = "ANIMAL";
    private static final String DB_TASKS_CUIDADOR = "CUIDADOR";
    private static final String DB_TASKS_PLUS_SALARIO = "PLUS_SALARIO";
    private static final String DB_TASKS_FECHA = "FECHA_TAREA";

    // Configuración de la tabla Login
    private static final String DB_LOGIN = "LOGIN";
    private static final String DB_LOGIN_SELECT = "SELECT * FROM " + DB_LOGIN;
    private static final String DB_LOGIN_NOMBRE = "NOMBRE";
    private static final String DB_LOGIN_APELLIDOS = "APELLIDOS";
    private static final String DB_LOGIN_DNI = "DNI";
    private static final String DB_LOGIN_PASS = "PASS";
    private static final String DB_LOGIN_TIPO_USUARIO = "TIPO_USUARIO";

    // Configuración de la tabla Especie
    private static final String DB_ESPECIE = "ESPECIE";
    private static final String DB_ESPECIE_SELECT = "SELECT * FROM " + DB_ESPECIE;
    private static final String DB_ESPECIE_ID_ESPECIE = "ID_ESPECIE";
    private static final String DB_ESPECIE_NOMBRE_ESPECIE = "NOMBRE_ESPECIE";
    private static final String DB_ESPECIE_PELIGROSIDAD = "PELIGROSIDAD";

    // Configuración de la tabla Especialidad
    private static final String DB_ESPECIALIDAD = "ESPECIALIDAD";
    private static final String DB_ESPECIALIDAD_SELECT = "SELECT * FROM " + DB_ESPECIALIDAD;
    private static final String DB_ESPECIALIDAD_ID_ESPECIALIDAD = "ID_ESPECIE";
    private static final String DB_ESPECIALIDAD_NOMBRE_ESPECIALIDAD = "NOMBRE_ESPECIALIDAD";
    private static final String DB_ESPECIALIDAD_PLUS_SALARIO = "PLUS_SALARIO";

    // Configuración de la tabla Trabajo
    private static final String DB_TRABAJO = "TRABAJO";
    private static final String DB_TRABAJO_SELECT = "SELECT * FROM " + DB_TRABAJO;
    private static final String DB_TRABAJO_ID_TRABAJO = "ID_TRABAJO";
    private static final String DB_TRABAJO_NOMBRE_TRABAJO = "NOMBRE_TRABAJO";

    //////////////////////////////////////////////////
    // MÉTODOS DE CONEXIÓN A LA BASE DE DATOS
    //////////////////////////////////////////////////
    /**
     * Intenta cargar el JDBC driver.
     *
     * @return true si pudo cargar el driver, false en caso contrario
     */
    public static boolean loadDriver() {
        try {
            //System.out.print("Cargando Driver...");
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //System.out.println("OK!");
            return true;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Intenta conectar con la base de datos.
     *
     * @return true si pudo conectarse, false en caso contrario
     */
    public static boolean connect() {
        try {
            //System.out.print("Conectando a la base de datos...");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            //System.out.println("OK!");
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Comprueba la conexión y muestra su estado por pantalla
     *
     * @return true si la conexión existe y es válida, false en caso contrario
     */
    public static boolean isConnected() {
        // Comprobamos estado de la conexión
        try {
            if (conn != null && conn.isValid(0)) {
                //System.out.println(DB_MSQ_CONN_OK);
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(DB_MSQ_CONN_NO);
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Cierra la conexión con la base de datos
     */
    public static void close() {
        try {
            System.out.print("Cerrando la conexión...");
            conn.close();
            //System.out.println("OK!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static boolean validarNifONie(String nifONie) {

        Pattern p = Pattern.compile("([\\dXxYyZz])(\\d{7})([a-zA-Z])");
        Matcher m = p.matcher(nifONie);
        String letrasDni = "TRWAGMYFPDXBNJZSQVHLCKE";
        boolean correcto = false;
        String dniEquivalente;
        int dni;

        if (m.matches()) {
            //convertir primera letra de Nie a número
            switch (m.group(1).charAt(0)) {
                case 'x':
                case 'X':
                    dniEquivalente = "0" + m.group(2);
                    break;
                case 'y':
                case 'Y':
                    dniEquivalente = "1" + m.group(2);
                    break;
                case 'z':
                case 'Z':
                    dniEquivalente = "2" + m.group(2);
                    break;
                default:
                    dniEquivalente = m.group(1) + m.group(2);
            }
            // Aquí tenemos en dni una cadena con el nº de DNI o equivalente
            dni = Integer.parseInt(dniEquivalente);
            // Si el carácter al final del DNI/NIF en mayúsculas coincide con el que obtenemos de la cadena letrasDni
            if (letrasDni.charAt(dni % 23) == m.group(3).toUpperCase().charAt(0)) {
                correcto = true; //solo se llega aquí si el patrón coincide y si la letra final es correcta
            }
        }
        return correcto;
    } //fin validarNifONie

    public static void transaccion(String sql) {
        try {

            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            conn.setAutoCommit(false); ////// ----->> Desactivamos auto commit

            Statement st = conn.createStatement();

            // Crear un registro de envíos si se cumple una determinada condición
            if (st.executeUpdate(sql) != 0) {
                JOptionPane.showMessageDialog(null, "Transacción Correcta");
                conn.commit();  ///// ---->> reflejar las operaciones en la base de datos

            } else {
                JOptionPane.showMessageDialog(null, "Error, desacemos los cambios");
                conn.rollback(); ///// -----> Deshacer operaciones
            }
        } catch (SQLException e) {  //Si se produce una Excepción deshacemos las operaciones

            //System.out.println(e.toString());
            if (conn != null) {
                try {
                    JOptionPane.showMessageDialog(null, "Error, desacemos los cambios");
                    conn.rollback();///// -----> Deshacer operaciones
                } catch (SQLException ex) {
                    //System.out.println(ex.toString());
                    JOptionPane.showMessageDialog(null, "Error, desacemos los cambios");
                }
            }

        } 
    }

    //////////////////////////////////////////////////
    // MÉTODOS DE TABLA ANIMALS
    //////////////////////////////////////////////////
    // Los argumentos indican el tipo de ResultSet deseado
    /**
     * Obtiene toda la tabla animals de la base de datos
     *
     * @param resultSetType Tipo de ResultSet
     * @param resultSetConcurrency Concurrencia del ResultSet
     * @return ResultSet (del tipo indicado) con la tabla, null en caso de error
     */
    public static ResultSet getTablaAnimals(int resultSetType, int resultSetConcurrency) {
        try {
            Statement stmt = conn.createStatement(resultSetType, resultSetConcurrency);
            ResultSet rs = stmt.executeQuery(DB_ANIMALS_SELECT);
            //stmt.close();
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public static ResultSet getTablaAnimalsEspecies(int resultSetType, int resultSetConcurrency, String especie) {
        try {
            Statement stmt = conn.createStatement(resultSetType, resultSetConcurrency);
            ResultSet rs = stmt.executeQuery("SELECT * FROM animals WHERE especie = '" + especie + "'");
            //stmt.close();
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /**
     * Solicita a la BD el animal con id indicado
     *
     * @param id id del animal
     * @return ResultSet con el resultado de la consulta, null en caso de error
     */
    //devuelve id del animal al pasarle un nombre
    public static int getIdAnimal(String nombre) {
        try {
            // Realizamos la consulta SQL
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT id_animal FROM `animals` WHERE NOMBRE" + "='" + nombre + "';";
            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);

            //stmt.close();
            // Si no hay primer registro entonces no existe el cliente
            if (!rs.first()) {
                return -2;
            }

            // Todo bien, devolvemos el cliente
            int animalID = rs.getInt(DB_ANIMALS_ID);
            return animalID;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return -2;
        }
    }

    //devuelve id del animal al pasarle un nombre
    public static String getEspecieAnimal(int id) {
        try {
            // Realizamos la consulta SQL
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT especie FROM `animals` WHERE id_animal" + "='" + id + "';";
            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);

            //stmt.close();
            // Si no hay primer registro entonces no existe el cliente
            if (!rs.first()) {
                return null;
            }

            // Todo bien, devolvemos el cliente
            String especie = rs.getString(DB_ANIMALS_ESPE);
            return especie;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static int getPeligroAnimal(String especie) {
        try {
            // Realizamos la consulta SQL
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT peligrosidad FROM `especie` WHERE nombre_especie" + "='" + especie + "';";

            ResultSet rs = stmt.executeQuery(sql);

            //stmt.close();
            // Si no hay primer registro entonces no existe el cliente
            if (!rs.first()) {
                return -2;
            }

            // Todo bien, devolvemos el cliente
            int peligro = rs.getInt(DB_ANIMALS_PELIGROSIDAD);
            return peligro;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return -2;
        }
    }

    //recupera solo el nombre de un animal pasando el id
    public static String getAnimalName(int id) {
        try {
            // Realizamos la consulta SQL
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT nombre FROM `animals` WHERE ID_ANIMAL" + "='" + id + "';";
            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);

            //stmt.close();
            // Si no hay primer registro entonces no existe el cliente
            if (!rs.first()) {
                return null;
            }

            // Todo bien, devolvemos el cliente
            String animalName = rs.getString(DB_ANIMALS_NOM);
            return animalName;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //recupera todo de un animal pasando el id
    public static ResultSet getAnimal(int id) {
        try {
            // Realizamos la consulta SQL
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = DB_ANIMALS_SELECT + " WHERE " + DB_ANIMALS_ID + "='" + id + "';";
            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            //stmt.close();

            // Si no hay primer registro entonces no existe el cliente
            if (!rs.first()) {
                return null;
            }

            // Todo bien, devolvemos el cliente
            return rs;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //devuelve la fila seleccionada para meter los datos en una array
    public static String[] editarAnimalSeleccionado(int id) {
        try {
            // Realizamos la consulta SQL
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM ANIMALS WHERE ID ='" + id + "'";
            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);

            //stmt.close();
            // Si no hay primer registro entonces no existe el cliente
            if (!rs.next()) {
                return null;
            }

            // Todo bien, devolvemos el cliente
            String nombre = rs.getString(DB_ANIMALS_NOM);
            String especie = rs.getString(DB_ANIMALS_ESPE);
            String color = rs.getString(DB_ANIMALS_PELIGROSIDAD);
            String peso = rs.getString(DB_ANIMALS_PESO);

            String arrayEditarAnimal[] = {nombre, especie, color, peso};

            return arrayEditarAnimal;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void borrarTodos(String sql) {
        try {

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DBManagerZoo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //////////////////////////////////////////////////
    // MÉTODOS DE TABLA CAREGIVERS
    //////////////////////////////////////////////////
    public static boolean completarTarea(int id) {

        try {
            // Obtenemos el cliente
            ResultSet rs = DBManagerZoo.getTablaTasksid(id);

            // Si no existe el Resultset
            if (rs == null) {
                System.out.println("Error. ResultSet null.");
                return false;
            }

            // Si tiene un primer registro, lo modificamos
            if (rs.first()) {
                rs.updateBoolean("TAREA_COMPLETADA", true);

                rs.updateRow();
                rs.close();
                JOptionPane.showMessageDialog(null, "Tarea Marcada como Realizada correctamente", "Tarea realizada", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                System.out.println("ERROR. ResultSet vacío.");
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    //recupera toda la tabla de la bd caregivers
    public static ResultSet getTablaCaregivers(int resultSetType, int resultSetConcurrency) {
        try {
            Statement stmt = conn.createStatement(resultSetType, resultSetConcurrency);
            ResultSet rs = stmt.executeQuery(DB_CAREGIVERS_SELECT);
            //stmt.close();
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public static ResultSet getTablaCaregiversEmpleados(int resultSetType, int resultSetConcurrency) {
        try {
            Statement stmt = conn.createStatement(resultSetType, resultSetConcurrency);
            ResultSet rs = stmt.executeQuery("SELECT * FROM caregivers WHERE CARGO = 'empleado'");
            //stmt.close();
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    //devuelve id del cuidador al pasarle un nombre
    public static int getIdCaregivers(String dni) {
        try {
            // Realizamos la consulta SQL
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT id_caregiver FROM CAREGIVERS WHERE DNI ='" + dni + "';";
            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            //stmt.close();

            // Si no hay primer registro entonces no existe el cliente
            if (!rs.first()) {
                return 0;
            }

            // Todo bien, devolvemos el cliente
            int caregiverId = rs.getInt(DB_CAREGIVERS_ID_CAREGIVER);
            return caregiverId;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    //devuelve nombre de cuidador al pasar un id
    public static String getCaregiverName(String dni) {
        try {
            // Realizamos la consulta SQL
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT nombre FROM `caregivers` WHERE DNI" + "='" + dni + "';";
            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            //stmt.close();

            // Si no hay primer registro entonces no existe el cliente
            if (!rs.first()) {
                return null;
            }

            // Todo bien, devolvemos el cliente
            String caregiverName = rs.getString(DB_CAREGIVERS_NOM);
            return caregiverName;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void actualizarCargoVisitante(int id) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "UPDATE `login` SET `TIPO_USUARIO` = 'Empleado' WHERE `login`.`ID_LOGIN` = '" + id + "'";
        } catch (SQLException ex) {
            Logger.getLogger(DBManagerZoo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //devuelve nombre de cuidador al pasar un id
    public static ResultSet getCaregiver(String dni) {
        try {
            // Realizamos la consulta SQL
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM caregivers WHERE DNI ='" + dni + "';";
            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            //stmt.close();

            // Si no hay primer registro entonces no existe el cliente
            if (!rs.first()) {
                return null;
            }

            // Todo bien, devolvemos el cliente
            return rs;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //dvuelve dni
    public static String getDNICaregivers(String nombre) {
        try {
            // Realizamos la consulta SQL
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT dni FROM CAREGIVERS WHERE nombre ='" + nombre + "';";
            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            //stmt.close();

            // Si no hay primer registro entonces no existe el cliente
            if (!rs.first()) {
                return null;
            }

            // Todo bien, devolvemos el cliente
            String caregiverDni = rs.getString(DB_CAREGIVERS_DNI);
            return caregiverDni;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //devuelve especialidad al pasar dni
    public static String getEspecialidadCaregivers(String dni) {
        try {
            // Realizamos la consulta SQL
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT especialidad FROM CAREGIVERS WHERE dni ='" + dni + "';";
            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            //stmt.close();

            // Si no hay primer registro entonces no existe el cliente
            if (!rs.first()) {
                return null;
            }

            // Todo bien, devolvemos el cliente
            String especialidad = rs.getString(DB_CAREGIVERS_ESPE);
            return especialidad;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //devuelve salario base al pasar dni
    public static float getSalarioCaregivers(String dni) {
        try {
            // Realizamos la consulta SQL
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT salario_base FROM CAREGIVERS WHERE dni ='" + dni + "';";
            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            //stmt.close();

            // Si no hay primer registro entonces no existe el cliente
            if (!rs.first()) {
                return -1;
            }

            // Todo bien, devolvemos el cliente
            float salario = rs.getFloat(DB_CAREGIVERS_SALARIO);
            return salario;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    //////////////////////////////////////////////////
    // MÉTODOS DE TABLA LOGIN
    //////////////////////////////////////////////////
    public static ResultSet getTablaLogin(int resultSetType, int resultSetConcurrency) {
        try {
            Statement stmt = conn.createStatement(resultSetType, resultSetConcurrency);
            ResultSet rs = stmt.executeQuery(DB_LOGIN_SELECT);
            //stmt.close();
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public static ResultSet getLogin(String dni, String passMd5) {
        try {
            // Realizamos la consulta SQL
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM LOGIN WHERE DNI ='" + dni + "' AND PASS = '" + passMd5 + "'";
            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            //stmt.close();

            // Si no hay primer registro entonces no existe el cliente
            if (!rs.first()) {
                return null;
            }

            // Todo bien, devolvemos el cliente
            return rs;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String getNameLogin(String dni) {
        try {
            // Realizamos la consulta SQL
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT nombre FROM LOGIN WHERE DNI ='" + dni + "'";
            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            //stmt.close();

            // Si no hay primer registro entonces no existe el cliente
            if (!rs.first()) {
                return null;
            }

            // Todo bien, devolvemos el cliente
            String name = rs.getString("Nombre");
            return name;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static ResultSet getLogin(String dni) {
        try {
            // Realizamos la consulta SQL
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM LOGIN WHERE DNI ='" + dni + "'";
            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            //stmt.close();

            // Si no hay primer registro entonces no existe el cliente
            if (!rs.first()) {
                return null;
            }

            // Todo bien, devolvemos el cliente
            return rs;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static boolean updateLogin(String dni, String cargo) {
        try {
            // Obtenemos el cliente

            ResultSet rs = DBManagerZoo.getLogin(dni);

            // Si no existe el Resultset
            if (rs == null) {
                System.out.println("Error. ResultSet null.");
                return false;
            }

            // Si tiene un primer registro, lo modificamos
            if (rs.first()) {
                rs.updateString("TIPO_USUARIO", cargo);

                rs.updateRow();
                rs.close();
                return true;
            } else {
                System.out.println("ERROR. ResultSet vacío.");
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static int getIdLogin(String dni) {
        try {
            // Realizamos la consulta SQL
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT id_login FROM LOGIN WHERE DNI ='" + dni + "'";
            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            //stmt.close();

            // Si no hay primer registro entonces no existe el cliente
            if (!rs.first()) {
                return -1;
            }

            // Todo bien, devolvemos el cliente
            int id = rs.getInt("id_login");
            return id;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public static String getCargoLogin(String dni, String pass) {
        try {
            // Realizamos la consulta SQL
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT TIPO_USUARIO FROM LOGIN WHERE DNI ='" + dni + "' AND " + DB_LOGIN_PASS + "= '" + pass + "';";
            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            //stmt.close();

            // Si no hay primer registro entonces no existe el cliente
            if (!rs.first()) {
                return null;
            }

            // Todo bien, devolvemos el cliente
            String cargo = rs.getString(DB_LOGIN_TIPO_USUARIO);
            return cargo;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //////////////////////////////////////////////////
    // MÉTODOS DE TABLA TASKS
    //////////////////////////////////////////////////
    public static void borrarTodasTareas() {
        try {
            Statement stmt;
            stmt = conn.createStatement();
            stmt.executeUpdate("TRUNCATE TABLE tasks");
        } catch (SQLException ex) {
            Logger.getLogger(DBManagerZoo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ResultSet getTablaTasks(int resultSetType, int resultSetConcurrency) {
        try {
            Statement stmt = conn.createStatement(resultSetType, resultSetConcurrency);
            ResultSet rs = stmt.executeQuery(DB_TASKS_SELECT);
            //stmt.close();
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public static ResultSet getTablaTasksFecha(int resultSetType, int resultSetConcurrency, String fecha) {
        try {
            Statement stmt = conn.createStatement(resultSetType, resultSetConcurrency);
            ResultSet rs = stmt.executeQuery("SELECT * FROM tasks WHERE FECHA_TAREA LIKE '" + fecha + "%'");
            //stmt.close();
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public static ResultSet getTablaTasksEmpleado(String dni) {
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT * FROM tasks WHERE cuidador = '" + dni + "'");
            //stmt.close();
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public static ResultSet getTablaTasksid(int id) {
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT * FROM tasks WHERE id_tarea = '" + id + "'");
            //stmt.close();
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public static boolean comprobarTareaRealizada(int id) {
        try {
            // Realizamos la consulta SQL
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT tarea_realizada FROM tasks WHERE id_tarea ='" + id + "';";
            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            //stmt.close();

            // Si no hay primer registro entonces no existe el cliente
            if (!rs.first()) {
                return false;
            }

            // Todo bien, devolvemos el cliente)
            Boolean resultado = rs.getBoolean("tarea_realizada");
            return resultado;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    //devuelve plus salario por especialidad
    public static float getPlusSalario(String especialidad) {
        try {
            // Realizamos la consulta SQL
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT plus_salario FROM especialidad WHERE nombre_especialidad ='" + especialidad + "';";
            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            //stmt.close();

            // Si no hay primer registro entonces no existe el cliente
            if (!rs.first()) {
                return -1;
            }

            // Todo bien, devolvemos el cliente
            float salario = rs.getFloat(DB_ESPECIALIDAD_PLUS_SALARIO);
            return salario;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    //devuelve plus salario por especialidad
    public static boolean comprobarTarea(String dateTime) {
        try {
            // Realizamos la consulta SQL
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM tasks WHERE fecha_tarea ='" + dateTime + "';";
            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            //stmt.close();

            // Si no hay primer registro entonces no existe el cliente
            if (!rs.first()) {
                return false;
            }

            // Todo bien, devolvemos el cliente)
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    //////////////////////////////////////////////////
    // MÉTODOS DE TABLA TRABAJO
    //////////////////////////////////////////////////
    public static ResultSet getTablaTrabajo(int resultSetType, int resultSetConcurrency) {
        try {
            Statement stmt = conn.createStatement(resultSetType, resultSetConcurrency);
            ResultSet rs = stmt.executeQuery(DB_TRABAJO_SELECT);
            //stmt.close();
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    //////////////////////////////////////////////////
    // MÉTODOS DE TABLA ESPECIALIDAD
    //////////////////////////////////////////////////
    public static ResultSet getTablaEspecialidad(int resultSetType, int resultSetConcurrency) {
        try {
            Statement stmt = conn.createStatement(resultSetType, resultSetConcurrency);
            ResultSet rs = stmt.executeQuery(DB_ESPECIALIDAD_SELECT);
            //stmt.close();
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    //devuelve plus salario por especialidad
    public static float getPlusSalarioTarea(String dni) {
        try {
            // Realizamos la consulta SQL
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT SUM(plus_salario) SumValor FROM tasks WHERE cuidador ='" + dni + "';";
            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            //stmt.close();

            // Si no hay primer registro entonces no existe el cliente
            if (!rs.first()) {
                return 0;
            }

            // Todo bien, devolvemos el cliente
            float Salario = rs.getFloat("SumValor");
            return Salario;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    //////////////////////////////////////////////////
    // MÉTODOS DE TABLA ESPECIE
    //////////////////////////////////////////////////
    public static ResultSet getTablaEspecie(int resultSetType, int resultSetConcurrency) {
        try {
            Statement stmt = conn.createStatement(resultSetType, resultSetConcurrency);
            ResultSet rs = stmt.executeQuery(DB_ESPECIE_SELECT);
            //stmt.close();
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    //////////////////////////////////////////////////
    // INSERTAR DATOS EN TABLAS
    //////////////////////////////////////////////////
    public static boolean insertAnimal(int id, String nombre, String especie, float peso) {
        try {
            // Obtenemos la tabla clientes
            System.out.print("Insertando nuevo Animal " + nombre + "...");
            ResultSet rs = getTablaAnimals(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

            // Insertamos el nuevo registro
            rs.moveToInsertRow();
            //rs.updateInt(DB_ANIMALS_ID, id);
            rs.updateString(DB_ANIMALS_NOM, nombre);
            rs.updateString(DB_ANIMALS_ESPE, especie);
            rs.updateFloat(DB_ANIMALS_PESO, peso);
            rs.insertRow();

            // Todo bien, cerramos ResultSet y devolvemos true
            rs.close();
            System.out.println("OK!");
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean insertLogin(String nombre, String apellidos, String dni, String pass) {
        try {
            // Obtenemos la tabla clientes
            System.out.print("Insertando nuevo usuario " + dni + "...");
            ResultSet rs = getTablaLogin(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            // Insertamos el nuevo registro
            rs.moveToInsertRow();
            //rs.updateInt(DB_ANIMALS_ID, id);
            rs.updateString(DB_LOGIN_NOMBRE, nombre);
            rs.updateString(DB_LOGIN_APELLIDOS, apellidos);
            rs.updateString(DB_LOGIN_DNI, dni);
            rs.updateString(DB_LOGIN_PASS, pass);

            rs.insertRow();

            // Todo bien, cerramos ResultSet y devolvemos true
            rs.close();
            System.out.println("OK!");
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean insertLoginCaregivers(String nombre, String apellidos, String dni, String pass, String cargo) {
        try {
            // Obtenemos la tabla clientes
            System.out.print("Insertando nuevo usuario " + dni + "...");
            ResultSet rs = getTablaLogin(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            // Insertamos el nuevo registro
            rs.moveToInsertRow();
            //rs.updateInt(DB_ANIMALS_ID, id);
            rs.updateString(DB_LOGIN_NOMBRE, nombre);
            rs.updateString(DB_LOGIN_APELLIDOS, apellidos);
            rs.updateString(DB_LOGIN_DNI, dni);
            rs.updateString(DB_LOGIN_PASS, pass);
            rs.updateString(DB_LOGIN_TIPO_USUARIO, cargo);

            rs.insertRow();

            // Todo bien, cerramos ResultSet y devolvemos true
            rs.close();
            System.out.println("OK!");
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean insertCaregivers(String nombre, String apellidos, String dni_cuidador, String pass, String especialidad, String cargo, float salario_base) {
        try {
            // Obtenemos la tabla clientes
            System.out.print("Insertando nuevo Cuidador " + nombre + "" + apellidos + "...");
            ResultSet rs = getTablaCaregivers(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

            // Insertamos el nuevo registro
            rs.moveToInsertRow();
            rs.updateString(DB_CAREGIVERS_NOM, nombre);
            rs.updateString(DB_CAREGIVERS_APE, apellidos);
            rs.updateString(DB_CAREGIVERS_DNI, dni_cuidador);
            rs.updateString(DB_CAREGIVERS_PASS, pass);
            rs.updateString(DB_CAREGIVERS_ESPE, especialidad);
            rs.updateString(DB_CAREGIVERS_CARGO, cargo);
            rs.updateFloat(DB_CAREGIVERS_SALARIO, salario_base);
            rs.insertRow();

            // Todo bien, cerramos ResultSet y devolvemos true
            rs.close();
            System.out.println("OK!");
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean insertTasks(String tarea, int animal, String cuidador, float plus_salario, String date) {
        try {
            // Obtenemos la tabla clientes
            System.out.print("Insertando nueva tarea " + tarea + "...");
            ResultSet rs = getTablaTasks(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

            // Insertamos el nuevo registro
            rs.moveToInsertRow();
            rs.updateString(DB_TASKS_TAREA, tarea);
            rs.updateInt(DB_TASKS_ANIMAL, animal);
            rs.updateString(DB_TASKS_CUIDADOR, cuidador);
            rs.updateFloat(DB_TASKS_PLUS_SALARIO, plus_salario);
            rs.updateString(DB_TASKS_FECHA, date);

            rs.insertRow();

            // Todo bien, cerramos ResultSet y devolvemos true
            rs.close();
            System.out.println("OK!");
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean insertEspecie(String nombre_especie, int peligrosidad) {
        try {
            // Obtenemos la tabla clientes
            System.out.print("Insertando nueva especie " + nombre_especie + "...");
            ResultSet rs = getTablaEspecie(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

            // Insertamos el nuevo registro
            rs.moveToInsertRow();
            rs.updateString(DB_ESPECIE_NOMBRE_ESPECIE, nombre_especie);
            rs.updateInt(DB_ESPECIE_PELIGROSIDAD, peligrosidad);

            rs.insertRow();

            // Todo bien, cerramos ResultSet y devolvemos true
            rs.close();
            System.out.println("OK!");
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean insertEspecialidad(String nombre_especialidad, float plus_salario) {
        try {
            // Obtenemos la tabla clientes
            System.out.print("Insertando nueva especialidad " + nombre_especialidad + "...");
            ResultSet rs = getTablaEspecialidad(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

            // Insertamos el nuevo registro
            rs.moveToInsertRow();
            rs.updateString(DB_ESPECIALIDAD_NOMBRE_ESPECIALIDAD, nombre_especialidad);
            rs.updateFloat(DB_ESPECIALIDAD_PLUS_SALARIO, plus_salario);

            rs.insertRow();

            // Todo bien, cerramos ResultSet y devolvemos true
            rs.close();
            JOptionPane.showMessageDialog(null, "OK!");
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean insertTrabajo(String nombre_trabajo) {
        try {
            // Obtenemos la tabla clientes
            System.out.print("Insertando tipo de trabajo " + nombre_trabajo + "...");
            ResultSet rs = getTablaTrabajo(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

            // Insertamos el nuevo registro
            rs.moveToInsertRow();
            rs.updateString(DB_TRABAJO_NOMBRE_TRABAJO, nombre_trabajo);

            rs.insertRow();

            // Todo bien, cerramos ResultSet y devolvemos true
            rs.close();
            System.out.println("OK!");
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

//    public static String getComercialesKpiTabla(String kpi, String usuario) {
//        try {
//            // Realizamos la consulta SQL
//            Statement stmt = conn.createStatement();
//            String sql = "SELECT CANTIDAD FROM proyecto_nieves.objetivos WHERE KPI = '"+kpi+"' AND USUARIO = '"+usuario+"';";
//            //System.out.println(sql);
//            ResultSet rs = stmt.executeQuery(sql);
//            //stmt.close();
//
//            // Si no hay primer registro entonces no existe el cliente
//            if (!rs.next()) {
//                return null;
//            }
//
//            // Todo bien, devolvemos el cliente
//            String cantidad = rs.getString("CANTIDAD");
//            return cantidad;
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//    }
    //listar datos (para los jComboBox)
//    public static String getRDTNuevoComercial(String tienda) {
//        try {
//            // Realizamos la consulta SQL
//            Statement stmt = conn.createStatement();
//            String sql = "SELECT RDT FROM TIENDAS WHERE NOMBRE ='" + tienda + "'";
//            //System.out.println(sql);
//            ResultSet rs = stmt.executeQuery(sql);
//            
//            //stmt.close();
//
//            // Si no hay primer registro entonces no existe el cliente
//            if (!rs.next()) {
//                return null;
//            }
//
//            // Todo bien, devolvemos el cliente
//            String rdtNuevoComercial = rs.getString(DB_TIENDA_RDT);
//            return rdtNuevoComercial;
//            
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//    }
//    //devuelve consulta de un dato q se le pasa
//    public static ResultSet getTienda(String NombreTienda) {
//        try {
//            // Realizamos la consulta SQL
//            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//            String sql = DB_TIENDA_SELECT + " WHERE " + DB_TIENDA_NOMBRE + "='" + NombreTienda + "';";
//            //System.out.println(sql);
//            ResultSet rs = stmt.executeQuery(sql);
//            //stmt.close();
//
//            // Si no hay primer registro entonces no existe el cliente
//            if (!rs.first()) {
//                return null;
//            }
//
//            // Todo bien, devolvemos el cliente
//            return rs;
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//    }
    /**
     * Comprueba si en la BD existe el cliente con id indicado
     *
     * @param id id del cliente
     * @return verdadero si existe, false en caso contrario
     */
//    public static boolean existsTarea(String usuario, String mes) {
//        try {
//            // Obtenemos el cliente
//            ResultSet rs = getObjetivosAsignadosComprobar(usuario, mes);
//
//            // Si rs es null, se ha producido un error
//            if (rs == null) {
//                return false;
//            }
//
//            // Si no existe primer registro
//            if (!rs.first()) {
//                rs.close();
//                return false;
//            }
//
//            // Todo bien, existe el cliente
//            rs.close();
//            return true;
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            return false;
//        }
//    }
}
