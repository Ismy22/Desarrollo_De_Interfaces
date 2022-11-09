/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package bbdd_Zoo_Interfaz;

import Utilidades.TextPrompt;
import static bbdd_Zoo_Interfaz.frame_Animal.deleteTransaccion;
import static bbdd_Zoo_Interfaz.frame_Animal.updateAnimal;
import java.awt.Color;
import java.awt.Cursor;
import static java.awt.Frame.DEFAULT_CURSOR;
import static java.awt.Frame.HAND_CURSOR;
import java.awt.MouseInfo;
import java.awt.Point;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ismael
 */
public class frame_Cuidadores extends javax.swing.JFrame {

    private int x;
    private int y;

    /**
     * Creates new form frameCuidadores
     */
    public frame_Cuidadores() {
        initComponents();
        UIManager UI = new UIManager();
        UI.put("nimbusBlueGrey", new ColorUIResource(103, 0, 3));
        jTableEmpleados.getTableHeader().setForeground(Color.white);
        //jTableEmpleados.setBackground(Color.getHSBColor(255, 255, 255));
        TextPrompt placeholcer = new TextPrompt("Maria", jtNombreAltaCuidador);
        TextPrompt placeholcer2 = new TextPrompt("García García", jtApellidosAltaCuidador);
        TextPrompt placeholcer3 = new TextPrompt("12345678A", jtDNIAltaCuidador);
        TextPrompt placeholcer4 = new TextPrompt("Contraseña:", jTPassEmpleado);
        TextPrompt placeholcer5 = new TextPrompt("1200", jtAltaSalarioEmpleado);
    }

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

    // Configuración de la tabla Especialidad
    private static final String DB_ESPECIALIDAD = "ESPECIALIDAD";
    private static final String DB_ESPECIALIDAD_SELECT = "SELECT * FROM " + DB_ESPECIALIDAD;
    private static final String DB_ESPECIALIDAD_ID_ESPECIALIDAD = "ID_ESPECIE";
    private static final String DB_ESPECIALIDAD_NOMBRE_ESPECIALIDAD = "NOMBRE_ESPECIALIDAD";
    private static final String DB_ESPECIALIDAD_PLUS_SALARIO = "PLUS_SALARIO";

    //////////////////////////////////////////////////
    // MÉTODOS PARA CAREGIVERS
    //////////////////////////////////////////////////
    public void listEspecie() {

        try {
            ResultSet rs = DBManagerZoo.getTablaEspecialidad(DEFAULT_CURSOR, DISPOSE_ON_CLOSE);
            while (rs.next()) {

                String nombre = rs.getString(DB_ESPECIALIDAD_NOMBRE_ESPECIALIDAD);
                jcbEspecialidadAltaEmpleados.addItem(nombre);
                jcbEspecialidadEditarEmpleados1.addItem(nombre);

            }
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void vaciarFrameAddCaregivers() {

        jtNombreAltaCuidador.setText("");
        jtApellidosAltaCuidador.setText("");
        jtDNIAltaCuidador.setText("");
        jtAltaSalarioEmpleado.setText("");

    }

    public void vaciarCaregiversTable() {

        //vaciamos la tabla recorriendola con un bucle for
        DefaultTableModel tb = (DefaultTableModel) jTableEmpleados.getModel();
        int a = jTableEmpleados.getRowCount() - 1; //contamos las filas
        //bucle para borrar todas las filas
        for (int i = a; i >= 0; i--) {
            tb.removeRow(tb.getRowCount() - 1);
        }

    }

    public void rellenarTablaCaregivers() {

        try {
            ResultSet rs = DBManagerZoo.getTablaCaregivers(DEFAULT_CURSOR, DISPOSE_ON_CLOSE);
            while (rs.next()) {

                String nombre = rs.getString(DB_CAREGIVERS_NOM);
                String apellidos = rs.getString(DB_CAREGIVERS_APE);
                String dni = rs.getString(DB_CAREGIVERS_DNI);
                String especialidad = rs.getString(DB_CAREGIVERS_ESPE);
                String cargo = rs.getString(DB_CAREGIVERS_CARGO);
                int salario = rs.getInt(DB_CAREGIVERS_SALARIO);

                DefaultTableModel model = (DefaultTableModel) jTableEmpleados.getModel();
                Object[] row = {nombre, apellidos, dni, especialidad, cargo, salario};
                model.addRow(row);

            }
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    //arreglar id para que sea automático
    public void insertCaregivers() {

        String nombre = jtNombreAltaCuidador.getText();
        String apellidos = jtApellidosAltaCuidador.getText();
        String dni = jtDNIAltaCuidador.getText();
        Boolean comprobarDni = DBManagerZoo.validarNifONie(dni);
        String pass = jTPassEmpleado.getText();
        String passMd5 = Utilidades.Utilidades_Control.getMD5(pass);
        String especialidad = jcbEspecialidadAltaEmpleados.getSelectedItem().toString();
        String cargo = jCbCargo.getSelectedItem().toString();
        float salario = Float.parseFloat(jtAltaSalarioEmpleado.getText());

        DBManagerZoo.insertCaregivers(nombre, apellidos, dni, passMd5, especialidad, cargo, salario);

    }

    public void insertLoginCaregivers() {

        String nombre = jtNombreAltaCuidador.getText();
        String apellidos = jtApellidosAltaCuidador.getText();
        String dni = jtDNIAltaCuidador.getText();
        String pass = jTPassEmpleado.getText();
        String passMd5 = Utilidades.Utilidades_Control.getMD5(pass);
        String cargo = jCbCargo.getSelectedItem().toString();

        DBManagerZoo.insertLoginCaregivers(nombre, apellidos, dni, passMd5, cargo);

    }

    public static boolean deleteEmpleado(String dni) {
        try {
            // Obtenemos el cliente
            ResultSet rs = DBManagerZoo.getCaregiver(dni);

            // Si no existe el Resultset
            if (rs == null) {
                System.out.println("ERROR. ResultSet null.");
                return false;
            }

            // Si existe y tiene primer registro, lo eliminamos
            if (rs.first()) {
                rs.deleteRow();
                rs.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "ERROR AL BORRAR EL ANIMAL");
                return false;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean updateEmpleado(String nombre, String apellidos, String dni, String pass, String especialidad, String cargo, Float salario_base) {
        try {
            // Obtenemos el cliente

            ResultSet rs = DBManagerZoo.getCaregiver(dni);

            // Si no existe el Resultset
            if (rs == null) {
                System.out.println("Error. ResultSet null.");
                return false;
            }

            // Si tiene un primer registro, lo modificamos
            if (rs.first()) {
                rs.updateString(DB_CAREGIVERS_NOM, nombre);
                rs.updateString(DB_CAREGIVERS_APE, apellidos);
                rs.updateString(DB_CAREGIVERS_DNI, dni);
                rs.updateString(DB_CAREGIVERS_PASS, pass);
                rs.updateString(DB_CAREGIVERS_ESPE, especialidad);
                rs.updateString(DB_CAREGIVERS_CARGO, cargo);
                rs.updateFloat(DB_CAREGIVERS_SALARIO, salario_base);

                rs.updateRow();
                rs.close();
                JOptionPane.showMessageDialog(null, "Empleado editado correctamente", "Editar Empleado", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Empleado no editado", "Editar Empleado", JOptionPane.ERROR_MESSAGE);
                //System.out.println("ERROR. ResultSet vacío.");
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public static boolean updateEmpleadoSinPass(String nombre, String apellidos, String dni, String especialidad, String cargo, Float salario_base) {
        try {
            // Obtenemos el cliente

            ResultSet rs = DBManagerZoo.getCaregiver(dni);

            // Si no existe el Resultset
            if (rs == null) {
                System.out.println("Error. ResultSet null.");
                return false;
            }

            // Si tiene un primer registro, lo modificamos
            if (rs.first()) {
                rs.updateString(DB_CAREGIVERS_NOM, nombre);
                rs.updateString(DB_CAREGIVERS_APE, apellidos);
                rs.updateString(DB_CAREGIVERS_DNI, dni);
                rs.updateString(DB_CAREGIVERS_ESPE, especialidad);
                rs.updateString(DB_CAREGIVERS_CARGO, cargo);
                rs.updateFloat(DB_CAREGIVERS_SALARIO, salario_base);

                rs.updateRow();
                rs.close();
                JOptionPane.showMessageDialog(null, "Empleado editado correctamente", "Editar Empleado", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Empleado no editado", "Editar Empleado", JOptionPane.ERROR_MESSAGE);
                //System.out.println("ERROR. ResultSet vacío.");
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean updateLogin(String nombre, String apellidos, String dni, String pass, String tipo_ususario) {
        try {
            // Obtenemos el cliente

            ResultSet rs = DBManagerZoo.getLogin(dni);

            // Si no existe el Resultset
            if (rs == null) {
                return false;
            }

            // Si tiene un primer registro, lo modificamos
            if (rs.first()) {
                rs.updateString("nombre", nombre);
                rs.updateString("apellidos", apellidos);
                rs.updateString("dni", dni);
                rs.updateString("pass", pass);
                rs.updateString("tipo_usuario", tipo_ususario);

                rs.updateRow();
                rs.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Login no editado", "Editar Login", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public static boolean updateLoginSinPass(String nombre, String apellidos, String dni, String tipo_ususario) {
        try {
            // Obtenemos el cliente

            ResultSet rs = DBManagerZoo.getLogin(dni);

            // Si no existe el Resultset
            if (rs == null) {
                return false;
            }

            // Si tiene un primer registro, lo modificamos
            if (rs.first()) {
                rs.updateString("nombre", nombre);
                rs.updateString("apellidos", apellidos);
                rs.updateString("dni", dni);
                rs.updateString("tipo_usuario", tipo_ususario);

                rs.updateRow();
                rs.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Login no editado", "Editar Login", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrameEmpleados = new javax.swing.JFrame();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableEmpleados = new javax.swing.JTable();
        jBModificarAnimal = new javax.swing.JButton();
        jBBorrarEmpleado = new javax.swing.JButton();
        jButtonAtrasListarEmpleados = new javax.swing.JButton();
        jFrameEditarEmpleados = new javax.swing.JFrame();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jtNombreEditarCuidador1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jtApellidosEditarCuidador1 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jtDNIEditarCuidador1 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTPassEditarEmpleado1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jcbEspecialidadEditarEmpleados1 = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jCbEditarCargo1 = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jtEditarSalarioEmpleado = new javax.swing.JTextField();
        jButtonEditarEmpleado = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jButtonAtrasEditarEmpleado = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jtNombreAltaCuidador = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtApellidosAltaCuidador = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jtDNIAltaCuidador = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTPassEmpleado = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jtAltaSalarioEmpleado = new javax.swing.JTextField();
        jcbEspecialidadAltaEmpleados = new javax.swing.JComboBox<>();
        jCbCargo = new javax.swing.JComboBox<>();
        jButtonGuardarAltaEmpleado = new javax.swing.JButton();
        jButtonListarEmpleados = new javax.swing.JButton();
        jButtonAtrasAltaEmpleado = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();

        jFrameEmpleados.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jFrameEmpleados.setLocationByPlatform(true);
        jFrameEmpleados.setMinimumSize(new java.awt.Dimension(698, 435));
        jFrameEmpleados.setUndecorated(true);
        jFrameEmpleados.setResizable(false);
        jFrameEmpleados.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jFrameEmpleadosMouseDragged(evt);
            }
        });
        jFrameEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jFrameEmpleadosMousePressed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel2.setForeground(new java.awt.Color(0, 0, 0));

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Jurassic Park", 1, 36)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("  Empleados");

        jTableEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Apellidos", "Dni", "Especialidad", "Cargo", "Salario"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableEmpleados);
        if (jTableEmpleados.getColumnModel().getColumnCount() > 0) {
            jTableEmpleados.getColumnModel().getColumn(0).setResizable(false);
            jTableEmpleados.getColumnModel().getColumn(1).setResizable(false);
            jTableEmpleados.getColumnModel().getColumn(2).setResizable(false);
            jTableEmpleados.getColumnModel().getColumn(3).setResizable(false);
            jTableEmpleados.getColumnModel().getColumn(4).setResizable(false);
            jTableEmpleados.getColumnModel().getColumn(5).setResizable(false);
        }

        jBModificarAnimal.setBackground(new java.awt.Color(103, 0, 3));
        jBModificarAnimal.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jBModificarAnimal.setForeground(new java.awt.Color(255, 255, 255));
        jBModificarAnimal.setText("Modificar");
        jBModificarAnimal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBModificarAnimalMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBModificarAnimalMouseExited(evt);
            }
        });
        jBModificarAnimal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBModificarAnimalActionPerformed(evt);
            }
        });

        jBBorrarEmpleado.setBackground(new java.awt.Color(103, 0, 3));
        jBBorrarEmpleado.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jBBorrarEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        jBBorrarEmpleado.setText("Borrar");
        jBBorrarEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBBorrarEmpleadoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBBorrarEmpleadoMouseExited(evt);
            }
        });
        jBBorrarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBorrarEmpleadoActionPerformed(evt);
            }
        });

        jButtonAtrasListarEmpleados.setBackground(new java.awt.Color(103, 0, 3));
        jButtonAtrasListarEmpleados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Login/Imagen/hacia-atras (1).png"))); // NOI18N
        jButtonAtrasListarEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonAtrasListarEmpleadosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonAtrasListarEmpleadosMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonAtrasListarEmpleadosMouseReleased(evt);
            }
        });
        jButtonAtrasListarEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAtrasListarEmpleadosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(230, 230, 230)
                                .addComponent(jBModificarAnimal)
                                .addGap(18, 18, 18)
                                .addComponent(jBBorrarEmpleado))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButtonAtrasListarEmpleados)))
                        .addGap(0, 224, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jScrollPane1)
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBBorrarEmpleado, jBModificarAnimal});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAtrasListarEmpleados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBModificarAnimal)
                    .addComponent(jBBorrarEmpleado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jBBorrarEmpleado, jBModificarAnimal});

        javax.swing.GroupLayout jFrameEmpleadosLayout = new javax.swing.GroupLayout(jFrameEmpleados.getContentPane());
        jFrameEmpleados.getContentPane().setLayout(jFrameEmpleadosLayout);
        jFrameEmpleadosLayout.setHorizontalGroup(
            jFrameEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jFrameEmpleadosLayout.setVerticalGroup(
            jFrameEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jFrameEditarEmpleados.setLocation(new java.awt.Point(3, 0));
        jFrameEditarEmpleados.setLocationByPlatform(true);
        jFrameEditarEmpleados.setMinimumSize(new java.awt.Dimension(503, 513));
        jFrameEditarEmpleados.setUndecorated(true);
        jFrameEditarEmpleados.setResizable(false);
        jFrameEditarEmpleados.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jFrameEditarEmpleadosMouseDragged(evt);
            }
        });
        jFrameEditarEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jFrameEditarEmpleadosMousePressed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel4.setForeground(new java.awt.Color(0, 0, 0));
        jPanel4.setMinimumSize(new java.awt.Dimension(503, 463));

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Nombre");

        jtNombreEditarCuidador1.setBackground(new java.awt.Color(255, 255, 255));
        jtNombreEditarCuidador1.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jtNombreEditarCuidador1.setForeground(new java.awt.Color(0, 0, 0));
        jtNombreEditarCuidador1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Apellidos");

        jtApellidosEditarCuidador1.setBackground(new java.awt.Color(255, 255, 255));
        jtApellidosEditarCuidador1.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jtApellidosEditarCuidador1.setForeground(new java.awt.Color(0, 0, 0));
        jtApellidosEditarCuidador1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Dni");

        jtDNIEditarCuidador1.setBackground(new java.awt.Color(255, 255, 255));
        jtDNIEditarCuidador1.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jtDNIEditarCuidador1.setForeground(new java.awt.Color(0, 0, 0));
        jtDNIEditarCuidador1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Pass");

        jTPassEditarEmpleado1.setBackground(new java.awt.Color(255, 255, 255));
        jTPassEditarEmpleado1.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jTPassEditarEmpleado1.setForeground(new java.awt.Color(0, 0, 0));
        jTPassEditarEmpleado1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Especialidad");

        jcbEspecialidadEditarEmpleados1.setBackground(new java.awt.Color(255, 255, 255));
        jcbEspecialidadEditarEmpleados1.setForeground(new java.awt.Color(0, 0, 0));

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Cargo");

        jCbEditarCargo1.setBackground(new java.awt.Color(255, 255, 255));
        jCbEditarCargo1.setForeground(new java.awt.Color(0, 0, 0));
        jCbEditarCargo1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Empleado", "Visitante", "Administrador" }));

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Salario Base");

        jtEditarSalarioEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        jtEditarSalarioEmpleado.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jtEditarSalarioEmpleado.setForeground(new java.awt.Color(0, 0, 0));
        jtEditarSalarioEmpleado.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jButtonEditarEmpleado.setBackground(new java.awt.Color(103, 0, 3));
        jButtonEditarEmpleado.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jButtonEditarEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        jButtonEditarEmpleado.setText("Guardar");
        jButtonEditarEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonEditarEmpleadoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonEditarEmpleadoMouseExited(evt);
            }
        });
        jButtonEditarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarEmpleadoActionPerformed(evt);
            }
        });

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Jurassic Park", 1, 48)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Editar   Empleado");

        jButtonAtrasEditarEmpleado.setBackground(new java.awt.Color(103, 0, 3));
        jButtonAtrasEditarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Login/Imagen/hacia-atras (1).png"))); // NOI18N
        jButtonAtrasEditarEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonAtrasEditarEmpleadoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonAtrasEditarEmpleadoMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonAtrasEditarEmpleadoMouseReleased(evt);
            }
        });
        jButtonAtrasEditarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAtrasEditarEmpleadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(197, 197, 197)
                                .addComponent(jButtonEditarEmpleado))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButtonAtrasEditarEmpleado)))
                        .addGap(0, 204, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(28, 28, 28)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15)
                        .addComponent(jLabel17))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jtEditarSalarioEmpleado)
                        .addComponent(jTPassEditarEmpleado1)
                        .addComponent(jtDNIEditarCuidador1)
                        .addComponent(jtApellidosEditarCuidador1)
                        .addComponent(jtNombreEditarCuidador1)
                        .addComponent(jcbEspecialidadEditarEmpleados1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCbEditarCargo1, 0, 289, Short.MAX_VALUE))
                    .addGap(46, 46, 46)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAtrasEditarEmpleado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 321, Short.MAX_VALUE)
                .addComponent(jButtonEditarEmpleado)
                .addGap(70, 70, 70))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(92, 92, 92)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(jtNombreEditarCuidador1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(jtApellidosEditarCuidador1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(jtDNIEditarCuidador1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(jTPassEditarEmpleado1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(jcbEspecialidadEditarEmpleados1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(jCbEditarCargo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(jtEditarSalarioEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(157, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jFrameEditarEmpleadosLayout = new javax.swing.GroupLayout(jFrameEditarEmpleados.getContentPane());
        jFrameEditarEmpleados.getContentPane().setLayout(jFrameEditarEmpleadosLayout);
        jFrameEditarEmpleadosLayout.setHorizontalGroup(
            jFrameEditarEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrameEditarEmpleadosLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jFrameEditarEmpleadosLayout.setVerticalGroup(
            jFrameEditarEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(java.awt.Color.white);
        setUndecorated(true);
        setResizable(false);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel1.setForeground(new java.awt.Color(0, 0, 0));

        jtNombreAltaCuidador.setBackground(new java.awt.Color(255, 255, 255));
        jtNombreAltaCuidador.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jtNombreAltaCuidador.setForeground(new java.awt.Color(0, 0, 0));
        jtNombreAltaCuidador.setToolTipText("Introduzca el nombre del empleado:");
        jtNombreAltaCuidador.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Nombre");

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Jurassic Park", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Alta   Empleado");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Apellidos");

        jtApellidosAltaCuidador.setBackground(new java.awt.Color(255, 255, 255));
        jtApellidosAltaCuidador.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jtApellidosAltaCuidador.setForeground(new java.awt.Color(0, 0, 0));
        jtApellidosAltaCuidador.setToolTipText("");
        jtApellidosAltaCuidador.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Dni");

        jtDNIAltaCuidador.setBackground(new java.awt.Color(255, 255, 255));
        jtDNIAltaCuidador.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jtDNIAltaCuidador.setForeground(new java.awt.Color(0, 0, 0));
        jtDNIAltaCuidador.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Pass");

        jTPassEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        jTPassEmpleado.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jTPassEmpleado.setForeground(new java.awt.Color(0, 0, 0));
        jTPassEmpleado.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Especialidad");

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Cargo");

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Salario Base");

        jtAltaSalarioEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        jtAltaSalarioEmpleado.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jtAltaSalarioEmpleado.setForeground(new java.awt.Color(0, 0, 0));
        jtAltaSalarioEmpleado.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jcbEspecialidadAltaEmpleados.setBackground(new java.awt.Color(255, 255, 255));
        jcbEspecialidadAltaEmpleados.setForeground(new java.awt.Color(0, 0, 0));

        jCbCargo.setBackground(new java.awt.Color(255, 255, 255));
        jCbCargo.setForeground(new java.awt.Color(0, 0, 0));
        jCbCargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Empleado", "Visitante", "Administrador" }));

        jButtonGuardarAltaEmpleado.setBackground(new java.awt.Color(103, 0, 3));
        jButtonGuardarAltaEmpleado.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jButtonGuardarAltaEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        jButtonGuardarAltaEmpleado.setText("Guardar");
        jButtonGuardarAltaEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonGuardarAltaEmpleadoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonGuardarAltaEmpleadoMouseExited(evt);
            }
        });
        jButtonGuardarAltaEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarAltaEmpleadoActionPerformed(evt);
            }
        });

        jButtonListarEmpleados.setBackground(new java.awt.Color(103, 0, 3));
        jButtonListarEmpleados.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jButtonListarEmpleados.setForeground(new java.awt.Color(255, 255, 255));
        jButtonListarEmpleados.setText("Listar Empleados");
        jButtonListarEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonListarEmpleadosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonListarEmpleadosMouseExited(evt);
            }
        });
        jButtonListarEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonListarEmpleadosActionPerformed(evt);
            }
        });

        jButtonAtrasAltaEmpleado.setBackground(new java.awt.Color(103, 0, 3));
        jButtonAtrasAltaEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Login/Imagen/hacia-atras (1).png"))); // NOI18N
        jButtonAtrasAltaEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonAtrasAltaEmpleadoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonAtrasAltaEmpleadoMouseExited(evt);
            }
        });
        jButtonAtrasAltaEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAtrasAltaEmpleadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtAltaSalarioEmpleado)
                    .addComponent(jTPassEmpleado)
                    .addComponent(jtDNIAltaCuidador)
                    .addComponent(jtApellidosAltaCuidador)
                    .addComponent(jtNombreAltaCuidador)
                    .addComponent(jcbEspecialidadAltaEmpleados, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCbCargo, 0, 289, Short.MAX_VALUE))
                .addGap(39, 39, 39))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addComponent(jButtonGuardarAltaEmpleado)
                .addGap(92, 92, 92)
                .addComponent(jButtonListarEmpleados)
                .addGap(62, 62, 62))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAtrasAltaEmpleado)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAtrasAltaEmpleado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtNombreAltaCuidador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtApellidosAltaCuidador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtDNIAltaCuidador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTPassEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jcbEspecialidadAltaEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jCbCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jtAltaSalarioEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonGuardarAltaEmpleado)
                    .addComponent(jButtonListarEmpleados))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtNombreAltaCuidador.getAccessibleContext().setAccessibleName("");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Login/Imagen/empleados.jpg"))); // NOI18N
        jLabel10.setText("jLabel10");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonGuardarAltaEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarAltaEmpleadoActionPerformed

        if (Utilidades.Utilidades_Control.contieneSoloLetras(jtNombreAltaCuidador.getText()) == false) {
            JOptionPane.showMessageDialog(null, "solo puede introducir letras");
            jtNombreAltaCuidador.setText("");
        }
        if (Utilidades.Utilidades_Control.contieneSoloLetras(jtApellidosAltaCuidador.getText()) == false) {
            JOptionPane.showMessageDialog(null, "solo puede introducir letras");
            jtApellidosAltaCuidador.setText("");
        }

        boolean comprobar = Utilidades.Utilidades_Control.contieneSoloNumeros(jtAltaSalarioEmpleado.getText());
        if (!comprobar) {
            JOptionPane.showMessageDialog(null, "solo puede introducir números");
            jtAltaSalarioEmpleado.setText("");
        } else {

            if (jtNombreAltaCuidador.getText().equals("") || jtApellidosAltaCuidador.getText().equals("") || jtDNIAltaCuidador.getText().equals("") || jtAltaSalarioEmpleado.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "no puede dejar ningún campo vacío");

            } else {

                if (DBManagerZoo.validarNifONie(jtDNIAltaCuidador.getText())) {
                    DBManagerZoo.getCaregiver(jtDNIAltaCuidador.getText());
                    DBManagerZoo.getLogin(jtDNIAltaCuidador.getText());

                    if (DBManagerZoo.getCaregiver(jtDNIAltaCuidador.getText()) == null) {
                        insertCaregivers();
                        JOptionPane.showMessageDialog(null, "Correto! Empleado dado de alta.");

                    } else {
                        JOptionPane.showMessageDialog(null, "El dni ya está dado de alta, liste los empleados para comprobarlo");
                        dispose();
                    }

                    if (DBManagerZoo.getLogin(jtDNIAltaCuidador.getText()) == null) {
                        insertLoginCaregivers();
                        JOptionPane.showMessageDialog(null, "Se ha creado un login empleado para el usuario: " + jtDNIAltaCuidador.getText());
                    } else {

                        String dni = jtDNIAltaCuidador.getText();
                        String cargo = "Empleado";
                        DBManagerZoo.updateLogin(dni, cargo);
                        JOptionPane.showMessageDialog(null, "Se ha modificado el cargo de visitante a empleado");
                        dispose();
                    }
                } else {

                    JOptionPane.showMessageDialog(null, "El dni no es correcto, prueba de nuevo");
                    jtDNIAltaCuidador.setText("");

                }

            }
        }
    }//GEN-LAST:event_jButtonGuardarAltaEmpleadoActionPerformed

    private void jButtonListarEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonListarEmpleadosActionPerformed
        jFrameEmpleados.setVisible(true);
        vaciarCaregiversTable();
        rellenarTablaCaregivers();
    }//GEN-LAST:event_jButtonListarEmpleadosActionPerformed

    private void jBModificarAnimalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBModificarAnimalActionPerformed

        //jFrameEditarEmpleados.setVisible(true);
        int filaseleccionada;

        //Guardamos en un entero la fila seleccionada.
        filaseleccionada = jTableEmpleados.getSelectedRow();

        if (filaseleccionada == -1) {
            JOptionPane.showMessageDialog(null, "No ha seleccionado ninguna fila.");
        } else {

            jFrameEditarEmpleados.setVisible(true);

            //String ayuda = tabla.getValueAt(filaseleccionada, num_columna).toString());
            String nombre = (String) jTableEmpleados.getValueAt(filaseleccionada, 0);
            String apellidos = (String) jTableEmpleados.getValueAt(filaseleccionada, 1);
            String dni = (String) jTableEmpleados.getValueAt(filaseleccionada, 2);
            String especialidad = (String) jTableEmpleados.getValueAt(filaseleccionada, 3);
            String cargo = (String) jTableEmpleados.getValueAt(filaseleccionada, 4);
            int salario_base = (int) jTableEmpleados.getValueAt(filaseleccionada, 5);

            jtNombreEditarCuidador1.setText(nombre);
            jtApellidosEditarCuidador1.setText(apellidos);
            jtDNIEditarCuidador1.setText(dni);
            listEspecie();
            jcbEspecialidadEditarEmpleados1.setSelectedItem(especialidad);
            jCbEditarCargo1.setSelectedItem(cargo);
            jtEditarSalarioEmpleado.setText(String.valueOf(salario_base));

        }
    }//GEN-LAST:event_jBModificarAnimalActionPerformed

    private void jButtonEditarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarEmpleadoActionPerformed

        if (Utilidades.Utilidades_Control.contieneSoloLetras(jtNombreEditarCuidador1.getText()) == false) {
            JOptionPane.showMessageDialog(null, "solo puede introducir letras");
            jtNombreAltaCuidador.setText("");
        }
        if (Utilidades.Utilidades_Control.contieneSoloLetras(jtApellidosEditarCuidador1.getText()) == false) {
            JOptionPane.showMessageDialog(null, "solo puede introducir letras");
            jtApellidosAltaCuidador.setText("");
        }

        boolean comprobar = Utilidades.Utilidades_Control.contieneSoloNumeros(jtEditarSalarioEmpleado.getText());
        if (!comprobar) {
            JOptionPane.showMessageDialog(null, "solo puede introducir números");
            jtAltaSalarioEmpleado.setText("");
        } else {

            if (jtNombreEditarCuidador1.getText().equals("") || jtApellidosEditarCuidador1.getText().equals("") || jtDNIEditarCuidador1.getText().equals("") || jtEditarSalarioEmpleado.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "no puede dejar ningún campo vacío");
            } else {
                String nombre = jtNombreEditarCuidador1.getText();
                String apellidos = jtApellidosEditarCuidador1.getText();
                String dni = jtDNIEditarCuidador1.getText();
                String pass = jTPassEditarEmpleado1.getText();
                String especialidad = jcbEspecialidadEditarEmpleados1.getSelectedItem().toString();
                String cargo = jCbEditarCargo1.getSelectedItem().toString();
                Float salario_base = Float.parseFloat(jtEditarSalarioEmpleado.getText());
                //System.out.println(nuevaEspecie);
                if (pass.equals("")) {
                    updateEmpleadoSinPass(nombre, apellidos, dni, especialidad, cargo, salario_base);
                    updateLoginSinPass(nombre, apellidos, dni, cargo);
                    vaciarCaregiversTable();
                    rellenarTablaCaregivers();
                    jFrameEditarEmpleados.dispose();
                } else {
                    String passMd5 = Utilidades.Utilidades_Control.getMD5(pass);
                    updateEmpleado(nombre, apellidos, dni, passMd5, especialidad, cargo, salario_base);
                    updateLogin(nombre, apellidos, dni, passMd5, cargo);
                    vaciarCaregiversTable();
                    rellenarTablaCaregivers();
                    jFrameEditarEmpleados.dispose();
                }

            }
        }
    }//GEN-LAST:event_jButtonEditarEmpleadoActionPerformed

    private void jBBorrarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBorrarEmpleadoActionPerformed
        
        int filaseleccionada = jTableEmpleados.getSelectedRow();

        if (filaseleccionada == -1) {
            JOptionPane.showMessageDialog(null, "No ha seleccionado ninguna fila.");
        } else {
            String dni = (String) jTableEmpleados.getValueAt(filaseleccionada, 2);
            String nombre = (String) jTableEmpleados.getValueAt(filaseleccionada, 0);
            String apellidos = (String) jTableEmpleados.getValueAt(filaseleccionada, 1);
            int confirmado = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar a: " + nombre + " " + apellidos + " ¡Y TODAS SUS TAREAS!?");

            if (JOptionPane.OK_OPTION == confirmado) {

                String sql = "DELETE FROM caregivers WHERE dni = '"+dni+"'";
                deleteTransaccion(sql);
                vaciarCaregiversTable();
                rellenarTablaCaregivers();
                JOptionPane.showMessageDialog(null, "eliminado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "vale... no borro nada...");
            }
        }

    }//GEN-LAST:event_jBBorrarEmpleadoActionPerformed

    private void jButtonAtrasAltaEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtrasAltaEmpleadoActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonAtrasAltaEmpleadoActionPerformed

    private void jButtonAtrasListarEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtrasListarEmpleadosActionPerformed
        jFrameEmpleados.dispose();
    }//GEN-LAST:event_jButtonAtrasListarEmpleadosActionPerformed

    private void jButtonAtrasEditarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtrasEditarEmpleadoActionPerformed
        jFrameEditarEmpleados.dispose();
    }//GEN-LAST:event_jButtonAtrasEditarEmpleadoActionPerformed

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void jFrameEmpleadosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFrameEmpleadosMousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jFrameEmpleadosMousePressed

    private void jFrameEditarEmpleadosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFrameEditarEmpleadosMousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jFrameEditarEmpleadosMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        setLocation(point.x - x, point.y - y);
    }//GEN-LAST:event_formMouseDragged

    private void jFrameEditarEmpleadosMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFrameEditarEmpleadosMouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        setLocation(point.x - x, point.y - y);
    }//GEN-LAST:event_jFrameEditarEmpleadosMouseDragged

    private void jFrameEmpleadosMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFrameEmpleadosMouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        setLocation(point.x - x, point.y - y);
    }//GEN-LAST:event_jFrameEmpleadosMouseDragged

    private void jButtonAtrasAltaEmpleadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasAltaEmpleadoMouseEntered
        jButtonAtrasAltaEmpleado.setCursor(new Cursor(HAND_CURSOR));
        jButtonAtrasAltaEmpleado.setBackground(new Color(217, 165, 9));
        jButtonAtrasAltaEmpleado.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonAtrasAltaEmpleadoMouseEntered

    private void jButtonAtrasAltaEmpleadoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasAltaEmpleadoMouseExited
        jButtonAtrasAltaEmpleado.setBackground(new Color(103, 0, 3));
        jButtonAtrasAltaEmpleado.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonAtrasAltaEmpleadoMouseExited

    private void jButtonGuardarAltaEmpleadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonGuardarAltaEmpleadoMouseEntered
        jButtonGuardarAltaEmpleado.setCursor(new Cursor(HAND_CURSOR));
        jButtonGuardarAltaEmpleado.setBackground(new Color(217, 165, 9));
        jButtonGuardarAltaEmpleado.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonGuardarAltaEmpleadoMouseEntered

    private void jButtonGuardarAltaEmpleadoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonGuardarAltaEmpleadoMouseExited
        jButtonGuardarAltaEmpleado.setBackground(new Color(103, 0, 3));
        jButtonGuardarAltaEmpleado.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonGuardarAltaEmpleadoMouseExited

    private void jButtonListarEmpleadosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonListarEmpleadosMouseEntered
        jButtonListarEmpleados.setCursor(new Cursor(HAND_CURSOR));
        jButtonListarEmpleados.setBackground(new Color(217, 165, 9));
        jButtonListarEmpleados.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonListarEmpleadosMouseEntered

    private void jButtonListarEmpleadosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonListarEmpleadosMouseExited
        jButtonListarEmpleados.setBackground(new Color(103, 0, 3));
        jButtonListarEmpleados.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonListarEmpleadosMouseExited

    private void jButtonAtrasListarEmpleadosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasListarEmpleadosMouseEntered
        jButtonAtrasListarEmpleados.setCursor(new Cursor(HAND_CURSOR));
        jButtonAtrasListarEmpleados.setBackground(new Color(217, 165, 9));
        jButtonAtrasListarEmpleados.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonAtrasListarEmpleadosMouseEntered

    private void jButtonAtrasListarEmpleadosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasListarEmpleadosMouseExited
        jButtonAtrasListarEmpleados.setBackground(new Color(103, 0, 3));
        jButtonAtrasListarEmpleados.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonAtrasListarEmpleadosMouseExited

    private void jBModificarAnimalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBModificarAnimalMouseEntered
        jBModificarAnimal.setCursor(new Cursor(HAND_CURSOR));
        jBModificarAnimal.setBackground(new Color(217, 165, 9));
        jBModificarAnimal.setForeground(Color.BLACK);
    }//GEN-LAST:event_jBModificarAnimalMouseEntered

    private void jBModificarAnimalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBModificarAnimalMouseExited
        jBModificarAnimal.setBackground(new Color(103, 0, 3));
        jBModificarAnimal.setForeground(Color.WHITE);
    }//GEN-LAST:event_jBModificarAnimalMouseExited

    private void jBBorrarEmpleadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBorrarEmpleadoMouseEntered
        jBBorrarEmpleado.setCursor(new Cursor(HAND_CURSOR));
        jBBorrarEmpleado.setBackground(new Color(217, 165, 9));
        jBBorrarEmpleado.setForeground(Color.BLACK);
    }//GEN-LAST:event_jBBorrarEmpleadoMouseEntered

    private void jBBorrarEmpleadoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBorrarEmpleadoMouseExited
        jBBorrarEmpleado.setBackground(new Color(103, 0, 3));
        jBBorrarEmpleado.setForeground(Color.WHITE);
    }//GEN-LAST:event_jBBorrarEmpleadoMouseExited

    private void jButtonAtrasEditarEmpleadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasEditarEmpleadoMouseEntered
        jButtonAtrasEditarEmpleado.setCursor(new Cursor(HAND_CURSOR));
        jButtonAtrasEditarEmpleado.setBackground(new Color(217, 165, 9));
        jButtonAtrasEditarEmpleado.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonAtrasEditarEmpleadoMouseEntered

    private void jButtonAtrasEditarEmpleadoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasEditarEmpleadoMouseExited
        jButtonAtrasEditarEmpleado.setBackground(new Color(103, 0, 3));
        jButtonAtrasEditarEmpleado.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonAtrasEditarEmpleadoMouseExited

    private void jButtonEditarEmpleadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonEditarEmpleadoMouseEntered
        jButtonEditarEmpleado.setCursor(new Cursor(HAND_CURSOR));
        jButtonEditarEmpleado.setBackground(new Color(217, 165, 9));
        jButtonEditarEmpleado.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonEditarEmpleadoMouseEntered

    private void jButtonEditarEmpleadoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonEditarEmpleadoMouseExited
        jButtonEditarEmpleado.setBackground(new Color(103, 0, 3));
        jButtonEditarEmpleado.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonEditarEmpleadoMouseExited

    private void jButtonAtrasListarEmpleadosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasListarEmpleadosMouseReleased
        jButtonAtrasListarEmpleados.setBackground(new Color(103, 0, 3));
        jButtonAtrasListarEmpleados.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonAtrasListarEmpleadosMouseReleased

    private void jButtonAtrasEditarEmpleadoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasEditarEmpleadoMouseReleased
        jButtonAtrasEditarEmpleado.setBackground(new Color(103, 0, 3));
        jButtonAtrasEditarEmpleado.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonAtrasEditarEmpleadoMouseReleased

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(frame_Cuidadores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(frame_Cuidadores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(frame_Cuidadores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(frame_Cuidadores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new frame_Cuidadores().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBBorrarEmpleado;
    private javax.swing.JButton jBModificarAnimal;
    private javax.swing.JButton jButtonAtrasAltaEmpleado;
    private javax.swing.JButton jButtonAtrasEditarEmpleado;
    private javax.swing.JButton jButtonAtrasListarEmpleados;
    private javax.swing.JButton jButtonEditarEmpleado;
    private javax.swing.JButton jButtonGuardarAltaEmpleado;
    private javax.swing.JButton jButtonListarEmpleados;
    private javax.swing.JComboBox<String> jCbCargo;
    private javax.swing.JComboBox<String> jCbEditarCargo1;
    private javax.swing.JFrame jFrameEditarEmpleados;
    private javax.swing.JFrame jFrameEmpleados;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTPassEditarEmpleado1;
    private javax.swing.JTextField jTPassEmpleado;
    private javax.swing.JTable jTableEmpleados;
    private javax.swing.JComboBox<String> jcbEspecialidadAltaEmpleados;
    private javax.swing.JComboBox<String> jcbEspecialidadEditarEmpleados1;
    private javax.swing.JTextField jtAltaSalarioEmpleado;
    private javax.swing.JTextField jtApellidosAltaCuidador;
    private javax.swing.JTextField jtApellidosEditarCuidador1;
    private javax.swing.JTextField jtDNIAltaCuidador;
    private javax.swing.JTextField jtDNIEditarCuidador1;
    private javax.swing.JTextField jtEditarSalarioEmpleado;
    private javax.swing.JTextField jtNombreAltaCuidador;
    private javax.swing.JTextField jtNombreEditarCuidador1;
    // End of variables declaration//GEN-END:variables
}
