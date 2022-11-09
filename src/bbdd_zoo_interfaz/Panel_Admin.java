/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package bbdd_Zoo_Interfaz;

import static bbdd_Zoo_Interfaz.Panel_Empleados.updateTareaRealizada;
import static bbdd_Zoo_Interfaz.frame_Animal.updateAnimal;
import java.awt.Color;
import java.awt.Cursor;
import static java.awt.Frame.DEFAULT_CURSOR;
import java.awt.MouseInfo;
import java.awt.Point;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ismael
 */
public class Panel_Admin extends javax.swing.JFrame {

    private int x;
    private int y;
    private int a;
    private int s;

    /**
     * Creates new form Panel_Admin
     */
    public Panel_Admin(String dni) {
        initComponents();

        UIManager Ui = new UIManager();
        Ui.put("nimbusBlueGrey", new ColorUIResource(103, 0, 3));
        jTablePrincipal.getTableHeader().setForeground(Color.white);
        jTablehistorico.getTableHeader().setForeground(Color.white);

        // Cargamos driver y conectamos con la BD
        DBManagerZoo.loadDriver();
        DBManagerZoo.connect();

        rellenarTablaPrincipal();
        listAnimals();
        listCaregivers();
        listTrabajos();
        String name = DBManagerZoo.getNameLogin(dni);
        jLabelSesionUser.setText(dni + " - " + name);

    }

    // Configuración de la tabla Tasks
    private static final String DB_TASKS = "TASKS";
    private static final String DB_TASKS_SELECT = "SELECT * FROM " + DB_TASKS;
    private static final String DB_TASKS_ID_TAREA = "ID_TAREA";
    private static final String DB_TASKS_TAREA = "TAREA";
    private static final String DB_TASKS_ANIMAL = "ANIMAL";
    private static final String DB_TASKS_CUIDADOR = "CUIDADOR";
    private static final String DB_TASKS_PLUS_SALARIO = "PLUS_SALARIO";
    private static final String DB_TASKS_FECHA = "FECHA_TAREA";
    private static final String DB_TASKS_REALIZADO = "TAREA_REALIZADA";

    //////////////////////////////////////////////////
    // MÉTODOS PARA JTABLES
    //////////////////////////////////////////////////
    public void listAnimals() {

        try {
            ResultSet rs = DBManagerZoo.getTablaAnimals(DEFAULT_CURSOR, DISPOSE_ON_CLOSE);
            while (rs.next()) {

                String nombre = rs.getString("NOMBRE");
                jcEditarAnimal.addItem(nombre);

            }
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void listCaregivers() {

        try {
            ResultSet rs = DBManagerZoo.getTablaCaregiversEmpleados(DEFAULT_CURSOR, DISPOSE_ON_CLOSE);
            while (rs.next()) {

                String nombre = rs.getString("NOMBRE");
                //String dni = rs.getString(DB_CAREGIVERS_DNI);
                //String apellido = rs.getString(DB_CAREGIVERS_APE);
                jcEditarCuidador.addItem(nombre);//+" "+apellido);

            }
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void listTrabajos() {

        try {
            ResultSet rs = DBManagerZoo.getTablaTrabajo(DEFAULT_CURSOR, DISPOSE_ON_CLOSE);
            while (rs.next()) {

                String nombre = rs.getString("NOMBRE_TRABAJO");
                jcEditarTarea.addItem(nombre);

            }
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void vaciarTablaPrincipal() {

        //vaciamos la tabla recorriendola con un bucle for
        DefaultTableModel tb = (DefaultTableModel) jTablePrincipal.getModel();
        int a = jTablePrincipal.getRowCount() - 1; //contamos las filas
        //bucle para borrar todas las filas
        for (int i = a; i >= 0; i--) {
            tb.removeRow(tb.getRowCount() - 1);
        }

    }

    public void vaciarTablaHistorico() {

        //vaciamos la tabla recorriendola con un bucle for
        DefaultTableModel tb = (DefaultTableModel) jTablehistorico.getModel();
        int a = jTablehistorico.getRowCount() - 1; //contamos las filas
        //bucle para borrar todas las filas
        for (int i = a; i >= 0; i--) {
            tb.removeRow(tb.getRowCount() - 1);
        }

    }

    public void rellenarTablaPrincipal() {

        try {
            ResultSet rs = DBManagerZoo.getTablaTasksSinCompletar(DEFAULT_CURSOR, DISPOSE_ON_CLOSE);
            while (rs.next()) {
                int id = rs.getInt(DB_TASKS_ID_TAREA);
                int animalId = rs.getInt(DB_TASKS_ANIMAL);
                String animalName = DBManagerZoo.getAnimalName(animalId);
                String cuidadorId = rs.getString(DB_TASKS_CUIDADOR);
                String cuidadorName = DBManagerZoo.getCaregiverName(cuidadorId);
                String tarea = rs.getString(DB_TASKS_TAREA);
                String fecha = rs.getString(DB_TASKS_FECHA);
                Boolean realizada = rs.getBoolean(DB_TASKS_REALIZADO);
                Boolean completada = rs.getBoolean("tarea_completada");

                DefaultTableModel model = (DefaultTableModel) jTablePrincipal.getModel();
                Object[] row = {id, animalName, cuidadorName, tarea, fecha, realizada, completada};
                model.addRow(row);
            }
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void rellenarTablaHistorico() {

        try {
            ResultSet rs = DBManagerZoo.getTablaTasks(DEFAULT_CURSOR, DISPOSE_ON_CLOSE);
            while (rs.next()) {
                int animalId = rs.getInt(DB_TASKS_ANIMAL);
                String animalName = DBManagerZoo.getAnimalName(animalId);
                String cuidadorId = rs.getString(DB_TASKS_CUIDADOR);
                String cuidadorName = DBManagerZoo.getCaregiverName(cuidadorId);
                String tarea = rs.getString(DB_TASKS_TAREA);
                String fecha = rs.getString(DB_TASKS_FECHA);
                Boolean realizada = rs.getBoolean(DB_TASKS_REALIZADO);

                DefaultTableModel model = (DefaultTableModel) jTablehistorico.getModel();
                Object[] row = {animalName, cuidadorName, tarea, fecha, realizada};
                model.addRow(row);
            }
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void rellenarTablaHistoricoFecha(String dni) {
        String fecha = datePickerHistorico.getDate().toString();
        try {
            ResultSet rs = DBManagerZoo.getTablaTasksFecha(DEFAULT_CURSOR, DISPOSE_ON_CLOSE, fecha, dni);
            while (rs.next()) {
                int animalId = rs.getInt(DB_TASKS_ANIMAL);
                String animalName = DBManagerZoo.getAnimalName(animalId);
                String cuidadorId = rs.getString(DB_TASKS_CUIDADOR);
                String cuidadorName = DBManagerZoo.getCaregiverName(cuidadorId);
                String tarea = rs.getString(DB_TASKS_TAREA);
                String fecha1 = rs.getString(DB_TASKS_FECHA);
                Boolean realizada = rs.getBoolean(DB_TASKS_REALIZADO);

                DefaultTableModel model = (DefaultTableModel) jTablehistorico.getModel();
                Object[] row = {animalName, cuidadorName, tarea, fecha1, realizada};
                model.addRow(row);
            }
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void listPlusSalario() {

        int animalId = DBManagerZoo.getIdAnimal(jcEditarAnimal.getSelectedItem().toString());
        //System.out.println(extraerId);
        //System.out.println(id+"hola");
        String especie = DBManagerZoo.getEspecieAnimal(animalId);
        int nPeligro = DBManagerZoo.getPeligroAnimal(especie);

        switch (nPeligro) {
            case 0:
                jtEditarPlusPeligroAltaTarea.setText("0");
                break;
            case 1:
                jtEditarPlusPeligroAltaTarea.setText("100");
                break;
            case 2:
                jtEditarPlusPeligroAltaTarea.setText("200");
                break;
            case 3:
                jtEditarPlusPeligroAltaTarea.setText("300");
                break;
            case 4:
                jtEditarPlusPeligroAltaTarea.setText("400");
                break;
            case 5:
                jtEditarPlusPeligroAltaTarea.setText("500");
                break;
            default:
                throw new AssertionError();
        }

    }

    public static boolean updateTarea(String tarea, int animal, String cuidador, Float plus_salario, String fecha) {
        try {
            // Obtenemos el cliente
            ResultSet rs = DBManagerZoo.getTablaTasksEmpleado(cuidador);

            // Si no existe el Resultset
            if (rs == null) {
                System.out.println("Error. ResultSet null.");
                return false;
            }

            // Si tiene un primer registro, lo modificamos
            if (rs.first()) {
                rs.updateString("TAREA", tarea);
                rs.updateInt("ANIMAL", animal);
                rs.updateString("CUIDADOR", cuidador);
                rs.updateFloat("PLUS_SALARIO", plus_salario);
                rs.updateString("FECHA_TAREA", fecha);

                rs.updateRow();
                rs.close();
                JOptionPane.showMessageDialog(null, "Animal editado correctamente", "Editar RDT", JOptionPane.INFORMATION_MESSAGE);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrameEditarTarea = new javax.swing.JFrame();
        capa5 = new javax.swing.JPanel();
        capa_Titulo5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jButtonAtrasEditarTarea = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jbModificarAnimal = new javax.swing.JButton();
        jcEditarTarea = new javax.swing.JComboBox<>();
        jcEditarCuidador = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jcEditarAnimal = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jtEditarPlusPeligroAltaTarea = new javax.swing.JTextField();
        dateTimePickerEditar = new com.github.lgooddatepicker.components.DateTimePicker();
        jFrameSuperAdmin = new javax.swing.JFrame();
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jButtonBorrarAnimales = new javax.swing.JButton();
        jbuttonBorrarEmpleados = new javax.swing.JButton();
        jButtonBorrarTareas = new javax.swing.JButton();
        jButtonEmpezarCero = new javax.swing.JButton();
        jbuttonBorrarUnaTarea = new javax.swing.JButton();
        jbuttonBorrarEspecies = new javax.swing.JButton();
        jbuttonBorrarTrabajos = new javax.swing.JButton();
        jButtonAtrasPanico = new javax.swing.JButton();
        jFrameHistoricoTareas = new javax.swing.JFrame();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        datePickerHistorico = new com.github.lgooddatepicker.components.DatePicker();
        jButtonMostrarFecha = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTablehistorico = new javax.swing.JTable();
        jButtonAtrasHistorico = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePrincipal = new javax.swing.JTable();
        jButtonAnimal = new javax.swing.JButton();
        jButtonEmpleados = new javax.swing.JButton();
        jButtonTareas = new javax.swing.JButton();
        jButtonEspecies = new javax.swing.JButton();
        jButtonEspecialidad = new javax.swing.JButton();
        jButtonTrabajos = new javax.swing.JButton();
        jButtonModificarTarea = new javax.swing.JButton();
        jButtonCerrarSesion = new javax.swing.JButton();
        jButtonSalir = new javax.swing.JButton();
        jLabelSesionUser = new javax.swing.JLabel();
        jButtonSuperAdmin = new javax.swing.JButton();
        jButtonHistoricoTareas = new javax.swing.JButton();
        jButtonCompletarTarea = new javax.swing.JButton();

        jFrameEditarTarea.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jFrameEditarTarea.setLocationByPlatform(true);
        jFrameEditarTarea.setMinimumSize(new java.awt.Dimension(492, 501));
        jFrameEditarTarea.setUndecorated(true);
        jFrameEditarTarea.setResizable(false);
        jFrameEditarTarea.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jFrameEditarTareaMouseDragged(evt);
            }
        });
        jFrameEditarTarea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jFrameEditarTareaMousePressed(evt);
            }
        });

        capa5.setBackground(new java.awt.Color(255, 255, 255));
        capa5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        capa_Titulo5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Editar  Tarea");
        jLabel7.setFont(new java.awt.Font("Jurassic Park", 1, 48)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jButtonAtrasEditarTarea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Login/Imagen/hacia-atras (1).png"))); // NOI18N
        jButtonAtrasEditarTarea.setBackground(new java.awt.Color(103, 0, 3));
        jButtonAtrasEditarTarea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonAtrasEditarTareaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonAtrasEditarTareaMouseExited(evt);
            }
        });
        jButtonAtrasEditarTarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAtrasEditarTareaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout capa_Titulo5Layout = new javax.swing.GroupLayout(capa_Titulo5);
        capa_Titulo5.setLayout(capa_Titulo5Layout);
        capa_Titulo5Layout.setHorizontalGroup(
            capa_Titulo5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
            .addGroup(capa_Titulo5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAtrasEditarTarea)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        capa_Titulo5Layout.setVerticalGroup(
            capa_Titulo5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, capa_Titulo5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAtrasEditarTarea)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addContainerGap())
        );

        jLabel8.setText("Tarea");
        jLabel8.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));

        jLabel9.setText("Plus peligro");
        jLabel9.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));

        jLabel10.setText("Cuidador");
        jLabel10.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jbModificarAnimal.setText("Modificar");
        jbModificarAnimal.setBackground(new java.awt.Color(113, 0, 3));
        jbModificarAnimal.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jbModificarAnimal.setForeground(new java.awt.Color(255, 255, 255));
        jbModificarAnimal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbModificarAnimalMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbModificarAnimalMouseExited(evt);
            }
        });
        jbModificarAnimal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbModificarAnimalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(193, 193, 193)
                .addComponent(jbModificarAnimal)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jbModificarAnimal)
                .addContainerGap())
        );

        jcEditarTarea.setBackground(new java.awt.Color(255, 255, 255));
        jcEditarTarea.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jcEditarTarea.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jcEditarTarea.setForeground(new java.awt.Color(0, 0, 0));

        jcEditarCuidador.setBackground(new java.awt.Color(255, 255, 255));
        jcEditarCuidador.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jcEditarCuidador.setForeground(new java.awt.Color(0, 0, 0));

        jLabel11.setText("Animal");
        jLabel11.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));

        jcEditarAnimal.setBackground(new java.awt.Color(255, 255, 255));
        jcEditarAnimal.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jcEditarAnimal.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jcEditarAnimal.setForeground(new java.awt.Color(0, 0, 0));
        jcEditarAnimal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcEditarAnimalItemStateChanged(evt);
            }
        });

        jLabel12.setText("Fecha y Hora");
        jLabel12.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));

        jtEditarPlusPeligroAltaTarea.setEditable(false);

        javax.swing.GroupLayout capa5Layout = new javax.swing.GroupLayout(capa5);
        capa5.setLayout(capa5Layout);
        capa5Layout.setHorizontalGroup(
            capa5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(capa_Titulo5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(capa5Layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(capa5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(36, 36, 36)
                .addGroup(capa5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jcEditarTarea, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtEditarPlusPeligroAltaTarea)
                    .addComponent(jcEditarCuidador, 0, 265, Short.MAX_VALUE)
                    .addComponent(dateTimePickerEditar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jcEditarAnimal, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        capa5Layout.setVerticalGroup(
            capa5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(capa5Layout.createSequentialGroup()
                .addComponent(capa_Titulo5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(capa5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jcEditarTarea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(30, 30, 30)
                .addGroup(capa5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jcEditarAnimal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(capa5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jcEditarCuidador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(capa5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jtEditarPlusPeligroAltaTarea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(capa5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(dateTimePickerEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 49, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout jFrameEditarTareaLayout = new javax.swing.GroupLayout(jFrameEditarTarea.getContentPane());
        jFrameEditarTarea.getContentPane().setLayout(jFrameEditarTareaLayout);
        jFrameEditarTareaLayout.setHorizontalGroup(
            jFrameEditarTareaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(capa5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jFrameEditarTareaLayout.setVerticalGroup(
            jFrameEditarTareaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(capa5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jFrameSuperAdmin.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jFrameSuperAdmin.setLocation(new java.awt.Point(400, 300));
        jFrameSuperAdmin.setLocationByPlatform(true);
        jFrameSuperAdmin.setMinimumSize(new java.awt.Dimension(400, 400));
        jFrameSuperAdmin.setUndecorated(true);
        jFrameSuperAdmin.setResizable(false);
        jFrameSuperAdmin.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jFrameSuperAdminMouseDragged(evt);
            }
        });
        jFrameSuperAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jFrameSuperAdminMousePressed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Panel del Panico");
        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Jurassic Park", 1, 48)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));

        jButtonBorrarAnimales.setText("Borrar Animales");
        jButtonBorrarAnimales.setBackground(new java.awt.Color(103, 0, 3));
        jButtonBorrarAnimales.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButtonBorrarAnimales.setForeground(new java.awt.Color(255, 255, 255));
        jButtonBorrarAnimales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonBorrarAnimalesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonBorrarAnimalesMouseExited(evt);
            }
        });
        jButtonBorrarAnimales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBorrarAnimalesActionPerformed(evt);
            }
        });

        jbuttonBorrarEmpleados.setText("Borrar Empleados");
        jbuttonBorrarEmpleados.setBackground(new java.awt.Color(103, 0, 3));
        jbuttonBorrarEmpleados.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jbuttonBorrarEmpleados.setForeground(new java.awt.Color(255, 255, 255));
        jbuttonBorrarEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbuttonBorrarEmpleadosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbuttonBorrarEmpleadosMouseExited(evt);
            }
        });
        jbuttonBorrarEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbuttonBorrarEmpleadosActionPerformed(evt);
            }
        });

        jButtonBorrarTareas.setText("Borrar Tareas");
        jButtonBorrarTareas.setBackground(new java.awt.Color(103, 0, 3));
        jButtonBorrarTareas.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButtonBorrarTareas.setForeground(new java.awt.Color(255, 255, 255));
        jButtonBorrarTareas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonBorrarTareasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonBorrarTareasMouseExited(evt);
            }
        });
        jButtonBorrarTareas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBorrarTareasActionPerformed(evt);
            }
        });

        jButtonEmpezarCero.setText("A la mierda TODO");
        jButtonEmpezarCero.setBackground(new java.awt.Color(103, 0, 3));
        jButtonEmpezarCero.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jButtonEmpezarCero.setForeground(new java.awt.Color(255, 255, 255));
        jButtonEmpezarCero.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonEmpezarCeroMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonEmpezarCeroMouseExited(evt);
            }
        });
        jButtonEmpezarCero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEmpezarCeroActionPerformed(evt);
            }
        });

        jbuttonBorrarUnaTarea.setText("Borrar una tarea");
        jbuttonBorrarUnaTarea.setBackground(new java.awt.Color(103, 0, 3));
        jbuttonBorrarUnaTarea.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jbuttonBorrarUnaTarea.setForeground(new java.awt.Color(255, 255, 255));
        jbuttonBorrarUnaTarea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbuttonBorrarUnaTareaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbuttonBorrarUnaTareaMouseExited(evt);
            }
        });
        jbuttonBorrarUnaTarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbuttonBorrarUnaTareaActionPerformed(evt);
            }
        });

        jbuttonBorrarEspecies.setText("Borrar Especies");
        jbuttonBorrarEspecies.setBackground(new java.awt.Color(103, 0, 3));
        jbuttonBorrarEspecies.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jbuttonBorrarEspecies.setForeground(new java.awt.Color(255, 255, 255));
        jbuttonBorrarEspecies.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbuttonBorrarEspeciesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbuttonBorrarEspeciesMouseExited(evt);
            }
        });
        jbuttonBorrarEspecies.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbuttonBorrarEspeciesActionPerformed(evt);
            }
        });

        jbuttonBorrarTrabajos.setText("Borrar Trabajos");
        jbuttonBorrarTrabajos.setBackground(new java.awt.Color(103, 0, 3));
        jbuttonBorrarTrabajos.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jbuttonBorrarTrabajos.setForeground(new java.awt.Color(255, 255, 255));
        jbuttonBorrarTrabajos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbuttonBorrarTrabajosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbuttonBorrarTrabajosMouseExited(evt);
            }
        });
        jbuttonBorrarTrabajos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbuttonBorrarTrabajosActionPerformed(evt);
            }
        });

        jButtonAtrasPanico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Login/Imagen/hacia-atras (1).png"))); // NOI18N
        jButtonAtrasPanico.setBackground(new java.awt.Color(103, 0, 3));
        jButtonAtrasPanico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonAtrasPanicoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonAtrasPanicoMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonAtrasPanicoMouseReleased(evt);
            }
        });
        jButtonAtrasPanico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAtrasPanicoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonEmpezarCero)
                .addGap(85, 85, 85))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jbuttonBorrarEspecies)
                            .addComponent(jButtonBorrarTareas)
                            .addComponent(jbuttonBorrarEmpleados))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonBorrarAnimales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbuttonBorrarUnaTarea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbuttonBorrarTrabajos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButtonAtrasPanico)))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButtonBorrarAnimales, jButtonBorrarTareas, jbuttonBorrarEmpleados, jbuttonBorrarEspecies, jbuttonBorrarUnaTarea});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAtrasPanico)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonBorrarTareas)
                    .addComponent(jbuttonBorrarUnaTarea))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbuttonBorrarEmpleados)
                    .addComponent(jButtonBorrarAnimales, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbuttonBorrarEspecies)
                    .addComponent(jbuttonBorrarTrabajos))
                .addGap(30, 30, 30)
                .addComponent(jButtonEmpezarCero, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButtonBorrarTareas, jbuttonBorrarEmpleados, jbuttonBorrarEspecies});

        javax.swing.GroupLayout jFrameSuperAdminLayout = new javax.swing.GroupLayout(jFrameSuperAdmin.getContentPane());
        jFrameSuperAdmin.getContentPane().setLayout(jFrameSuperAdminLayout);
        jFrameSuperAdminLayout.setHorizontalGroup(
            jFrameSuperAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jFrameSuperAdminLayout.setVerticalGroup(
            jFrameSuperAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jFrameHistoricoTareas.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jFrameHistoricoTareas.setLocation(new java.awt.Point(300, 300));
        jFrameHistoricoTareas.setMinimumSize(new java.awt.Dimension(754, 498));
        jFrameHistoricoTareas.setUndecorated(true);
        jFrameHistoricoTareas.setResizable(false);
        jFrameHistoricoTareas.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jFrameHistoricoTareasMouseDragged(evt);
            }
        });
        jFrameHistoricoTareas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jFrameHistoricoTareasMousePressed(evt);
            }
        });
        jFrameHistoricoTareas.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                jFrameHistoricoTareasWindowActivated(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel4.setForeground(new java.awt.Color(0, 0, 0));
        jPanel4.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel4MouseDragged(evt);
            }
        });
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel4MousePressed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText(" Historico tareas ");
        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Jurassic Park", 1, 70)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));

        datePickerHistorico.setBackground(new java.awt.Color(255, 255, 255));
        datePickerHistorico.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        datePickerHistorico.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        jButtonMostrarFecha.setText("Mostrar");
        jButtonMostrarFecha.setBackground(new java.awt.Color(103, 0, 3));
        jButtonMostrarFecha.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButtonMostrarFecha.setForeground(new java.awt.Color(255, 255, 255));
        jButtonMostrarFecha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonMostrarFechaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonMostrarFechaMouseExited(evt);
            }
        });
        jButtonMostrarFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMostrarFechaActionPerformed(evt);
            }
        });

        jTablehistorico.setBackground(new java.awt.Color(255, 255, 255));
        jTablehistorico.setForeground(new java.awt.Color(0, 0, 0));
        jTablehistorico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Animal", "Cuidador", "Tarea", "Fecha", "Realizada"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTablehistorico);
        if (jTablehistorico.getColumnModel().getColumnCount() > 0) {
            jTablehistorico.getColumnModel().getColumn(0).setResizable(false);
            jTablehistorico.getColumnModel().getColumn(1).setResizable(false);
            jTablehistorico.getColumnModel().getColumn(2).setResizable(false);
            jTablehistorico.getColumnModel().getColumn(3).setResizable(false);
        }

        jButtonAtrasHistorico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Login/Imagen/hacia-atras (1).png"))); // NOI18N
        jButtonAtrasHistorico.setBackground(new java.awt.Color(103, 0, 3));
        jButtonAtrasHistorico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonAtrasHistoricoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonAtrasHistoricoMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonAtrasHistoricoMouseReleased(evt);
            }
        });
        jButtonAtrasHistorico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAtrasHistoricoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonMostrarFecha)
                .addGap(18, 18, 18)
                .addComponent(datePickerHistorico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAtrasHistorico)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAtrasHistorico)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(datePickerHistorico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonMostrarFecha))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {datePickerHistorico, jButtonMostrarFecha});

        javax.swing.GroupLayout jFrameHistoricoTareasLayout = new javax.swing.GroupLayout(jFrameHistoricoTareas.getContentPane());
        jFrameHistoricoTareas.getContentPane().setLayout(jFrameHistoricoTareasLayout);
        jFrameHistoricoTareasLayout.setHorizontalGroup(
            jFrameHistoricoTareasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jFrameHistoricoTareasLayout.setVerticalGroup(
            jFrameHistoricoTareasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("*  Jurassic  Zoo  *");
        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Jurassic Park", 1, 80)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));

        jTablePrincipal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Animal", "Cuidador", "Tarea", "Fecha", "Realizada", "Completada"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablePrincipal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePrincipalMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablePrincipal);
        if (jTablePrincipal.getColumnModel().getColumnCount() > 0) {
            jTablePrincipal.getColumnModel().getColumn(1).setResizable(false);
            jTablePrincipal.getColumnModel().getColumn(2).setResizable(false);
            jTablePrincipal.getColumnModel().getColumn(3).setResizable(false);
            jTablePrincipal.getColumnModel().getColumn(4).setResizable(false);
            jTablePrincipal.getColumnModel().getColumn(5).setResizable(false);
            jTablePrincipal.getColumnModel().getColumn(6).setResizable(false);
        }

        jButtonAnimal.setText("Animales");
        jButtonAnimal.setBackground(new java.awt.Color(103, 0, 3));
        jButtonAnimal.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jButtonAnimal.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAnimal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonAnimalMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonAnimalMouseExited(evt);
            }
        });
        jButtonAnimal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAnimalActionPerformed(evt);
            }
        });

        jButtonEmpleados.setText("Empleados");
        jButtonEmpleados.setBackground(new java.awt.Color(103, 0, 3));
        jButtonEmpleados.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jButtonEmpleados.setForeground(new java.awt.Color(255, 255, 255));
        jButtonEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonEmpleadosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonEmpleadosMouseExited(evt);
            }
        });
        jButtonEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEmpleadosActionPerformed(evt);
            }
        });

        jButtonTareas.setText("Tareas");
        jButtonTareas.setBackground(new java.awt.Color(103, 0, 3));
        jButtonTareas.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jButtonTareas.setForeground(new java.awt.Color(255, 255, 255));
        jButtonTareas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonTareasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonTareasMouseExited(evt);
            }
        });
        jButtonTareas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTareasActionPerformed(evt);
            }
        });

        jButtonEspecies.setText("Especies");
        jButtonEspecies.setBackground(new java.awt.Color(103, 0, 3));
        jButtonEspecies.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jButtonEspecies.setForeground(new java.awt.Color(255, 255, 255));
        jButtonEspecies.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonEspeciesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonEspeciesMouseExited(evt);
            }
        });
        jButtonEspecies.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEspeciesActionPerformed(evt);
            }
        });

        jButtonEspecialidad.setText("Especialidad");
        jButtonEspecialidad.setBackground(new java.awt.Color(103, 0, 3));
        jButtonEspecialidad.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jButtonEspecialidad.setForeground(new java.awt.Color(255, 255, 255));
        jButtonEspecialidad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonEspecialidadMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonEspecialidadMouseExited(evt);
            }
        });
        jButtonEspecialidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEspecialidadActionPerformed(evt);
            }
        });

        jButtonTrabajos.setText("Trabajos");
        jButtonTrabajos.setBackground(new java.awt.Color(103, 0, 3));
        jButtonTrabajos.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jButtonTrabajos.setForeground(new java.awt.Color(255, 255, 255));
        jButtonTrabajos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonTrabajosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonTrabajosMouseExited(evt);
            }
        });
        jButtonTrabajos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTrabajosActionPerformed(evt);
            }
        });

        jButtonModificarTarea.setText("Modificar Tarea");
        jButtonModificarTarea.setBackground(new java.awt.Color(103, 0, 3));
        jButtonModificarTarea.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jButtonModificarTarea.setForeground(new java.awt.Color(255, 255, 255));
        jButtonModificarTarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarTareaActionPerformed(evt);
            }
        });

        jButtonCerrarSesion.setText("Cerrar Sesión");
        jButtonCerrarSesion.setBackground(new java.awt.Color(103, 0, 3));
        jButtonCerrarSesion.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        jButtonCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));
        jButtonCerrarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonCerrarSesionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonCerrarSesionMouseExited(evt);
            }
        });
        jButtonCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCerrarSesionActionPerformed(evt);
            }
        });

        jButtonSalir.setText("Salir");
        jButtonSalir.setBackground(new java.awt.Color(103, 0, 3));
        jButtonSalir.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        jButtonSalir.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonSalirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonSalirMouseExited(evt);
            }
        });
        jButtonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalirActionPerformed(evt);
            }
        });

        jLabelSesionUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelSesionUser.setText("f");
        jLabelSesionUser.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSesionUser.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabelSesionUser.setForeground(new java.awt.Color(0, 0, 0));

        jButtonSuperAdmin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Login/Imagen/Settings_30027.png"))); // NOI18N
        jButtonSuperAdmin.setBackground(new java.awt.Color(103, 0, 3));
        jButtonSuperAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonSuperAdminMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonSuperAdminMouseExited(evt);
            }
        });
        jButtonSuperAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSuperAdminActionPerformed(evt);
            }
        });

        jButtonHistoricoTareas.setText("Histórico Tareas");
        jButtonHistoricoTareas.setBackground(new java.awt.Color(103, 0, 3));
        jButtonHistoricoTareas.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jButtonHistoricoTareas.setForeground(new java.awt.Color(255, 255, 255));
        jButtonHistoricoTareas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonHistoricoTareasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonHistoricoTareasMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonHistoricoTareasMousePressed(evt);
            }
        });
        jButtonHistoricoTareas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHistoricoTareasActionPerformed(evt);
            }
        });

        jButtonCompletarTarea.setText("Completar Tarea");
        jButtonCompletarTarea.setBackground(new java.awt.Color(103, 0, 3));
        jButtonCompletarTarea.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jButtonCompletarTarea.setForeground(new java.awt.Color(255, 255, 255));
        jButtonCompletarTarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCompletarTareaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(596, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButtonCerrarSesion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonSalir))
                            .addComponent(jLabelSesionUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSuperAdmin)
                        .addGap(12, 12, 12))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButtonAnimal)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonEmpleados))
                            .addComponent(jButtonModificarTarea))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButtonTareas)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonEspecies))
                            .addComponent(jButtonHistoricoTareas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButtonEspecialidad)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonTrabajos))
                            .addComponent(jButtonCompletarTarea))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonSuperAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelSesionUser)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonSalir)
                            .addComponent(jButtonCerrarSesion))))
                .addGap(24, 24, 24)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAnimal)
                    .addComponent(jButtonEmpleados)
                    .addComponent(jButtonTareas)
                    .addComponent(jButtonEspecies)
                    .addComponent(jButtonEspecialidad)
                    .addComponent(jButtonTrabajos))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonModificarTarea)
                    .addComponent(jButtonHistoricoTareas)
                    .addComponent(jButtonCompletarTarea))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAnimalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnimalActionPerformed

        frame_Animal open = new frame_Animal();
        open.setVisible(true);
    }//GEN-LAST:event_jButtonAnimalActionPerformed

    private void jButtonEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEmpleadosActionPerformed

        frame_Cuidadores open = new frame_Cuidadores();
        open.setVisible(true);
        open.listEspecie();
    }//GEN-LAST:event_jButtonEmpleadosActionPerformed

    private void jButtonTareasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTareasActionPerformed

        frame_Tasks open = new frame_Tasks();
        open.setVisible(true);
        open.listTrabajos();
    }//GEN-LAST:event_jButtonTareasActionPerformed

    private void jButtonEspeciesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEspeciesActionPerformed

        frame_especie_nueva open = new frame_especie_nueva();
        open.setVisible(true);
    }//GEN-LAST:event_jButtonEspeciesActionPerformed

    private void jButtonEspecialidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEspecialidadActionPerformed

        frame_especialidad_nueva open = new frame_especialidad_nueva();
        open.setVisible(true);
    }//GEN-LAST:event_jButtonEspecialidadActionPerformed

    private void jButtonTrabajosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTrabajosActionPerformed

        frame_Trabajos open = new frame_Trabajos();
        open.setVisible(true);
    }//GEN-LAST:event_jButtonTrabajosActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        vaciarTablaPrincipal();
        rellenarTablaPrincipal();
        int fila = jTablePrincipal.getSelectedRow();
        if (fila < 0) {
            jButtonModificarTarea.setBackground(new Color(103, 0, 3));
            jButtonModificarTarea.setForeground(Color.WHITE);
            jButtonCompletarTarea.setBackground(new Color(103, 0, 3));
            jButtonCompletarTarea.setForeground(Color.WHITE);
        }
    }//GEN-LAST:event_formWindowGainedFocus

    private void jButtonModificarTareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarTareaActionPerformed

        //Guardamos en un entero la fila seleccionada.
        int filaseleccionada = jTablePrincipal.getSelectedRow();

        if (filaseleccionada == -1) {
            JOptionPane.showMessageDialog(null, "No ha seleccionado ninguna fila.");
        } else {

            jFrameHistoricoTareas.setVisible(true);
            jFrameHistoricoTareas.isFocusableWindow();

            listAnimals();
            //String ayuda = tabla.getValueAt(filaseleccionada, num_columna).toString());
            String animal = (String) jTablePrincipal.getValueAt(filaseleccionada, 0);
            String cuidador = (String) jTablePrincipal.getValueAt(filaseleccionada, 1);
            String tarea = (String) jTablePrincipal.getValueAt(filaseleccionada, 2);
            String fecha = (String) jTablePrincipal.getValueAt(filaseleccionada, 3);

            String date = fecha.substring(0, 10);
            String time = fecha.substring(12, 16);
            String dateMes = null;
            String year = date.substring(0, 4);
            String mes = date.substring(5, 7);
            String dia = date.substring(8, 10);

//            System.out.println(year);
//            System.out.println(mes);
//            System.out.println(dia);
            switch (mes) {
                case "01":
                    dateMes = "enero";
                    break;
                case "02":
                    dateMes = "febrero";
                    break;
                case "03":
                    dateMes = "marzo";
                    break;
                case "04":
                    dateMes = "abril";
                    break;
                case "05":
                    dateMes = "mayo";
                    break;
                case "06":
                    dateMes = "junio";
                    break;
                case "07":
                    dateMes = "julio";
                    break;
                case "08":
                    dateMes = "agosto";
                    break;
                case "09":
                    dateMes = "septiembre";
                    break;
                case "10":
                    dateMes = "octubre";
                    break;
                case "11":
                    dateMes = "noviembre";
                    break;
                case "12":
                    dateMes = "diciembre";
                    break;

                default:
                    throw new AssertionError();
            }
            jcEditarTarea.setSelectedItem(tarea);
            jcEditarCuidador.setSelectedItem(cuidador);
            jcEditarAnimal.setSelectedItem(animal);
            dateTimePickerEditar.datePicker.setText(dia + " de " + dateMes + " de " + year);
            dateTimePickerEditar.timePicker.setText(time);

            listPlusSalario();
            //listPlusSalario();

        }
    }//GEN-LAST:event_jButtonModificarTareaActionPerformed

    private void jbModificarAnimalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbModificarAnimalActionPerformed

        if (dateTimePickerEditar.getDatePicker().equals("") || dateTimePickerEditar.getTimePicker().equals("")) {
            JOptionPane.showMessageDialog(null, "No puede dejar ningún campo vacío");
        } else {

            String nuevaTarea = jcEditarTarea.getSelectedItem().toString();
            int animalId = DBManagerZoo.getIdAnimal(jcEditarAnimal.getSelectedItem().toString());
            String Cuidador = DBManagerZoo.getDNICaregivers(jcEditarCuidador.getSelectedItem().toString());
            float plus_salario = Float.parseFloat(jtEditarPlusPeligroAltaTarea.getText());
            String date = dateTimePickerEditar.getDatePicker().toString();
            String hora = dateTimePickerEditar.getTimePicker().toString();
            String dateTime = "" + date + " " + hora;

            //System.out.println(nuevaEspecie);
            updateTarea(nuevaTarea, animalId, Cuidador, plus_salario, dateTime);
            vaciarTablaPrincipal();
            rellenarTablaPrincipal();
            jFrameEditarTarea.dispose();
        }

    }//GEN-LAST:event_jbModificarAnimalActionPerformed

    private void jcEditarAnimalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcEditarAnimalItemStateChanged
        listPlusSalario();
    }//GEN-LAST:event_jcEditarAnimalItemStateChanged

    private void jButtonCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCerrarSesionActionPerformed
        this.dispose();
        Login.LoginUser open = new Login.LoginUser();
        open.setVisible(true);
    }//GEN-LAST:event_jButtonCerrarSesionActionPerformed

    private void jButtonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalirActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonSalirActionPerformed

    private void jButtonSuperAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSuperAdminActionPerformed
        jFrameSuperAdmin.setVisible(true);
    }//GEN-LAST:event_jButtonSuperAdminActionPerformed

    private void jButtonBorrarTareasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBorrarTareasActionPerformed
        int borrar = JOptionPane.showConfirmDialog(null, "Seguro que quieres borrar TODAS LAS TAREAS, el cambio es irreversible");

        if (JOptionPane.OK_OPTION == borrar) {
            String sql = "DELETE FROM tasks";
            DBManagerZoo.borrarTodos(sql);
            JOptionPane.showMessageDialog(null, "Se han borrado todas las Tareas");
            jFrameSuperAdmin.dispose();
            vaciarTablaPrincipal();
            rellenarTablaPrincipal();
        }

    }//GEN-LAST:event_jButtonBorrarTareasActionPerformed

    private void jButtonBorrarAnimalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBorrarAnimalesActionPerformed
        int borrar = JOptionPane.showConfirmDialog(null, "Seguro que quieres borrar TODAS LOS ANIMALES, el cambio es irreversible");

        if (JOptionPane.OK_OPTION == borrar) {
            String sql = "DELETE FROM animals";
            DBManagerZoo.borrarTodos(sql);
            JOptionPane.showMessageDialog(null, "Se han borrado todas los animales");
            jFrameSuperAdmin.dispose();
            vaciarTablaPrincipal();
            rellenarTablaPrincipal();
        }
    }//GEN-LAST:event_jButtonBorrarAnimalesActionPerformed

    private void jbuttonBorrarEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbuttonBorrarEmpleadosActionPerformed
        int borrar = JOptionPane.showConfirmDialog(null, "Seguro que quieres borrar TODAS LOS EMPLEADOS, el cambio es irreversible");

        if (JOptionPane.OK_OPTION == borrar) {
            String sql = "DELETE FROM caregivers";
            DBManagerZoo.borrarTodos(sql);
            JOptionPane.showMessageDialog(null, "Se han borrado todas los empleados, los administradores no pueden borrarse");
            jFrameSuperAdmin.dispose();
            vaciarTablaPrincipal();
            rellenarTablaPrincipal();
        }
    }//GEN-LAST:event_jbuttonBorrarEmpleadosActionPerformed

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        setLocation(point.x - x, point.y - y);
    }//GEN-LAST:event_formMouseDragged

    private void jbuttonBorrarUnaTareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbuttonBorrarUnaTareaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbuttonBorrarUnaTareaActionPerformed

    private void jbuttonBorrarEspeciesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbuttonBorrarEspeciesActionPerformed
        int borrar = JOptionPane.showConfirmDialog(null, "Seguro que quieres borrar TODAS LAS ESPECIES, el cambio es irreversible");

        if (JOptionPane.OK_OPTION == borrar) {
            String sql = "DELETE FROM especie";
            DBManagerZoo.borrarTodos(sql);
            JOptionPane.showMessageDialog(null, "Se han borrado todas las especies");
            jFrameSuperAdmin.dispose();
            vaciarTablaPrincipal();
            rellenarTablaPrincipal();
        }
    }//GEN-LAST:event_jbuttonBorrarEspeciesActionPerformed

    private void jbuttonBorrarTrabajosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbuttonBorrarTrabajosActionPerformed
        int borrar = JOptionPane.showConfirmDialog(null, "Seguro que quieres borrar TODOS LOS TRABAJOS, el cambio es irreversible");

        if (JOptionPane.OK_OPTION == borrar) {
            String sql = "DELETE FROM trabajo";
            DBManagerZoo.borrarTodos(sql);
            JOptionPane.showMessageDialog(null, "Se han borrado todas los trabajos");
            jFrameSuperAdmin.dispose();
            vaciarTablaPrincipal();
            rellenarTablaPrincipal();
        }
    }//GEN-LAST:event_jbuttonBorrarTrabajosActionPerformed

    private void jButtonEmpezarCeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEmpezarCeroActionPerformed
        int borrar = JOptionPane.showConfirmDialog(null, "Seguro que quieres borrar TODAS LAS TAREAS, el cambio es irreversible");

        if (JOptionPane.OK_OPTION == borrar) {
            String sql = "DELETE FROM animals";
            String sql1 = "DELETE FROM caregivers where cargo = 'Empleado'";
            String sql2 = "DELETE FROM especialidad";
            String sql3 = "DELETE FROM especie";
            String sql4 = "DELETE FROM login";
            String sql5 = "DELETE FROM tasks";
            String sql6 = "DELETE FROM trabajo";

            DBManagerZoo.borrarTodos(sql);
            DBManagerZoo.borrarTodos(sql1);
            DBManagerZoo.borrarTodos(sql2);
            DBManagerZoo.borrarTodos(sql3);
            DBManagerZoo.borrarTodos(sql4);
            DBManagerZoo.borrarTodos(sql5);
            DBManagerZoo.borrarTodos(sql6);

            JOptionPane.showMessageDialog(null, "Se han borrado todas las Tablas");
            jFrameSuperAdmin.dispose();
            vaciarTablaPrincipal();
            rellenarTablaPrincipal();
        }
    }//GEN-LAST:event_jButtonEmpezarCeroActionPerformed

    private void jButtonHistoricoTareasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHistoricoTareasActionPerformed
        jFrameHistoricoTareas.setVisible(true);
        vaciarTablaHistorico();
        rellenarTablaHistorico();
    }//GEN-LAST:event_jButtonHistoricoTareasActionPerformed

    private void jButtonMostrarFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMostrarFechaActionPerformed
        
        String dni =jLabelSesionUser.getText();
        try {

            vaciarTablaHistorico();
            rellenarTablaHistoricoFecha(dni);

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "No puede dejar el campo fecha vacío");
            rellenarTablaHistorico();
        }


    }//GEN-LAST:event_jButtonMostrarFechaActionPerformed

    private void jButtonAtrasPanicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtrasPanicoActionPerformed
        jFrameSuperAdmin.dispose();
    }//GEN-LAST:event_jButtonAtrasPanicoActionPerformed

    private void jButtonAtrasEditarTareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtrasEditarTareaActionPerformed
        jFrameEditarTarea.dispose();
    }//GEN-LAST:event_jButtonAtrasEditarTareaActionPerformed

    private void jButtonAtrasHistoricoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtrasHistoricoActionPerformed
        jFrameHistoricoTareas.dispose();
    }//GEN-LAST:event_jButtonAtrasHistoricoActionPerformed

    private void jFrameEditarTareaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFrameEditarTareaMousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jFrameEditarTareaMousePressed

    private void jFrameEditarTareaMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFrameEditarTareaMouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        setLocation(point.x - x, point.y - y);
    }//GEN-LAST:event_jFrameEditarTareaMouseDragged

    private void jFrameSuperAdminMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFrameSuperAdminMouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        setLocation(point.x - x, point.y - y);

    }//GEN-LAST:event_jFrameSuperAdminMouseDragged

    private void jFrameHistoricoTareasMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFrameHistoricoTareasMouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        setLocation(point.x - x, point.y - y);
    }//GEN-LAST:event_jFrameHistoricoTareasMouseDragged

    private void jFrameSuperAdminMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFrameSuperAdminMousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jFrameSuperAdminMousePressed

    private void jFrameHistoricoTareasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFrameHistoricoTareasMousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jFrameHistoricoTareasMousePressed

    private void jButtonAnimalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAnimalMouseEntered
        jButtonAnimal.setBackground(new Color(217, 165, 9));
        jButtonAnimal.setForeground(Color.BLACK);
        jButtonAnimal.setCursor(new Cursor(HAND_CURSOR));
    }//GEN-LAST:event_jButtonAnimalMouseEntered

    private void jButtonEmpleadosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonEmpleadosMouseEntered
        jButtonEmpleados.setBackground(new Color(217, 165, 9));
        jButtonEmpleados.setForeground(Color.BLACK);
        jButtonEmpleados.setCursor(new Cursor(HAND_CURSOR));
    }//GEN-LAST:event_jButtonEmpleadosMouseEntered

    private void jButtonTareasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonTareasMouseEntered
        jButtonTareas.setBackground(new Color(217, 165, 9));
        jButtonTareas.setForeground(Color.BLACK);
        jButtonTareas.setCursor(new Cursor(HAND_CURSOR));
    }//GEN-LAST:event_jButtonTareasMouseEntered

    private void jButtonEspeciesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonEspeciesMouseEntered
        jButtonEspecies.setBackground(new Color(217, 165, 9));
        jButtonEspecies.setForeground(Color.BLACK);
        jButtonEspecies.setCursor(new Cursor(HAND_CURSOR));
    }//GEN-LAST:event_jButtonEspeciesMouseEntered

    private void jButtonEspecialidadMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonEspecialidadMouseEntered
        jButtonEspecialidad.setBackground(new Color(217, 165, 9));
        jButtonEspecialidad.setForeground(Color.BLACK);
        jButtonEspecialidad.setCursor(new Cursor(HAND_CURSOR));
    }//GEN-LAST:event_jButtonEspecialidadMouseEntered

    private void jButtonTrabajosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonTrabajosMouseEntered
        jButtonTrabajos.setBackground(new Color(217, 165, 9));
        jButtonTrabajos.setForeground(Color.BLACK);
        jButtonTrabajos.setCursor(new Cursor(HAND_CURSOR));
    }//GEN-LAST:event_jButtonTrabajosMouseEntered

    private void jButtonHistoricoTareasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonHistoricoTareasMouseEntered
        jButtonHistoricoTareas.setBackground(new Color(217, 165, 9));
        jButtonHistoricoTareas.setForeground(Color.BLACK);
        jButtonHistoricoTareas.setCursor(new Cursor(HAND_CURSOR));
    }//GEN-LAST:event_jButtonHistoricoTareasMouseEntered

    private void jButtonCerrarSesionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonCerrarSesionMouseEntered
        jButtonCerrarSesion.setBackground(new Color(217, 165, 9));
        jButtonCerrarSesion.setForeground(Color.BLACK);
        jButtonCerrarSesion.setCursor(new Cursor(HAND_CURSOR));
    }//GEN-LAST:event_jButtonCerrarSesionMouseEntered

    private void jButtonSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSalirMouseEntered
        jButtonSalir.setBackground(new Color(217, 165, 9));
        jButtonSalir.setForeground(Color.BLACK);
        jButtonSalir.setCursor(new Cursor(HAND_CURSOR));
    }//GEN-LAST:event_jButtonSalirMouseEntered

    private void jButtonAnimalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAnimalMouseExited
        jButtonAnimal.setBackground(new Color(103, 0, 3));
        jButtonAnimal.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonAnimalMouseExited

    private void jButtonEmpleadosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonEmpleadosMouseExited
        jButtonEmpleados.setBackground(new Color(103, 0, 3));
        jButtonEmpleados.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonEmpleadosMouseExited

    private void jButtonTareasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonTareasMouseExited
        jButtonTareas.setBackground(new Color(103, 0, 3));
        jButtonTareas.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonTareasMouseExited

    private void jButtonEspeciesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonEspeciesMouseExited
        jButtonEspecies.setBackground(new Color(103, 0, 3));
        jButtonEspecies.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonEspeciesMouseExited

    private void jButtonEspecialidadMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonEspecialidadMouseExited
        jButtonEspecialidad.setBackground(new Color(103, 0, 3));
        jButtonEspecialidad.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonEspecialidadMouseExited

    private void jButtonTrabajosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonTrabajosMouseExited
        jButtonTrabajos.setBackground(new Color(103, 0, 3));
        jButtonTrabajos.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonTrabajosMouseExited

    private void jButtonHistoricoTareasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonHistoricoTareasMouseExited
        jButtonHistoricoTareas.setBackground(new Color(103, 0, 3));
        jButtonHistoricoTareas.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonHistoricoTareasMouseExited

    private void jButtonCerrarSesionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonCerrarSesionMouseExited
        jButtonCerrarSesion.setBackground(new Color(103, 0, 3));
        jButtonCerrarSesion.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonCerrarSesionMouseExited

    private void jButtonSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSalirMouseExited
        jButtonSalir.setBackground(new Color(103, 0, 3));
        jButtonSalir.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonSalirMouseExited

    private void jbModificarAnimalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbModificarAnimalMouseEntered
        jbModificarAnimal.setBackground(new Color(217, 165, 9));
        jbModificarAnimal.setForeground(Color.BLACK);
        jbModificarAnimal.setCursor(new Cursor(HAND_CURSOR));
    }//GEN-LAST:event_jbModificarAnimalMouseEntered

    private void jbModificarAnimalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbModificarAnimalMouseExited
        jbModificarAnimal.setBackground(new Color(103, 0, 3));
        jbModificarAnimal.setForeground(Color.WHITE);
    }//GEN-LAST:event_jbModificarAnimalMouseExited

    private void jButtonMostrarFechaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonMostrarFechaMouseEntered
        jButtonMostrarFecha.setBackground(new Color(217, 165, 9));
        jButtonMostrarFecha.setForeground(Color.BLACK);
        jButtonMostrarFecha.setCursor(new Cursor(HAND_CURSOR));
    }//GEN-LAST:event_jButtonMostrarFechaMouseEntered

    private void jButtonMostrarFechaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonMostrarFechaMouseExited
        jButtonMostrarFecha.setBackground(new Color(103, 0, 3));
        jButtonMostrarFecha.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonMostrarFechaMouseExited

    private void jButtonSuperAdminMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSuperAdminMouseEntered
        jButtonSuperAdmin.setCursor(new Cursor(HAND_CURSOR));
        jButtonSuperAdmin.setBackground(new Color(217, 165, 9));
        jButtonSuperAdmin.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonSuperAdminMouseEntered

    private void jButtonAtrasHistoricoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasHistoricoMouseEntered
        jButtonAtrasHistorico.setCursor(new Cursor(HAND_CURSOR));
        jButtonAtrasHistorico.setBackground(new Color(217, 165, 9));
        jButtonAtrasHistorico.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonAtrasHistoricoMouseEntered

    private void jButtonAtrasHistoricoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasHistoricoMouseExited
        jButtonAtrasHistorico.setBackground(new Color(103, 0, 3));
        jButtonAtrasHistorico.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonAtrasHistoricoMouseExited

    private void jButtonAtrasPanicoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasPanicoMouseEntered
        jButtonAtrasPanico.setCursor(new Cursor(HAND_CURSOR));
        jButtonAtrasPanico.setBackground(new Color(217, 165, 9));
        jButtonAtrasPanico.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonAtrasPanicoMouseEntered

    private void jButtonAtrasPanicoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasPanicoMouseExited
        jButtonAtrasPanico.setBackground(new Color(103, 0, 3));
        jButtonAtrasPanico.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonAtrasPanicoMouseExited

    private void jButtonAtrasEditarTareaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasEditarTareaMouseExited
        jButtonAtrasEditarTarea.setBackground(new Color(103, 0, 3));
        jButtonAtrasEditarTarea.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonAtrasEditarTareaMouseExited

    private void jButtonAtrasEditarTareaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasEditarTareaMouseEntered
        jButtonAtrasEditarTarea.setCursor(new Cursor(HAND_CURSOR));
        jButtonAtrasEditarTarea.setBackground(new Color(217, 165, 9));
        jButtonAtrasEditarTarea.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonAtrasEditarTareaMouseEntered

    private void jButtonBorrarTareasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonBorrarTareasMouseEntered
        jButtonBorrarTareas.setCursor(new Cursor(HAND_CURSOR));
        jButtonBorrarTareas.setBackground(new Color(217, 165, 9));
        jButtonBorrarTareas.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonBorrarTareasMouseEntered

    private void jButtonBorrarTareasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonBorrarTareasMouseExited
        jButtonBorrarTareas.setBackground(new Color(103, 0, 3));
        jButtonBorrarTareas.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonBorrarTareasMouseExited

    private void jbuttonBorrarUnaTareaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbuttonBorrarUnaTareaMouseEntered
        jbuttonBorrarUnaTarea.setCursor(new Cursor(HAND_CURSOR));
        jbuttonBorrarUnaTarea.setBackground(new Color(217, 165, 9));
        jbuttonBorrarUnaTarea.setForeground(Color.BLACK);
    }//GEN-LAST:event_jbuttonBorrarUnaTareaMouseEntered

    private void jbuttonBorrarUnaTareaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbuttonBorrarUnaTareaMouseExited
        jbuttonBorrarUnaTarea.setBackground(new Color(103, 0, 3));
        jbuttonBorrarUnaTarea.setForeground(Color.WHITE);
    }//GEN-LAST:event_jbuttonBorrarUnaTareaMouseExited

    private void jbuttonBorrarEmpleadosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbuttonBorrarEmpleadosMouseEntered
        jbuttonBorrarEmpleados.setCursor(new Cursor(HAND_CURSOR));
        jbuttonBorrarEmpleados.setBackground(new Color(217, 165, 9));
        jbuttonBorrarEmpleados.setForeground(Color.BLACK);
    }//GEN-LAST:event_jbuttonBorrarEmpleadosMouseEntered

    private void jbuttonBorrarEmpleadosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbuttonBorrarEmpleadosMouseExited
        jbuttonBorrarEmpleados.setBackground(new Color(103, 0, 3));
        jbuttonBorrarEmpleados.setForeground(Color.WHITE);
    }//GEN-LAST:event_jbuttonBorrarEmpleadosMouseExited

    private void jButtonBorrarAnimalesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonBorrarAnimalesMouseEntered
        jButtonBorrarAnimales.setCursor(new Cursor(HAND_CURSOR));
        jButtonBorrarAnimales.setBackground(new Color(217, 165, 9));
        jButtonBorrarAnimales.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonBorrarAnimalesMouseEntered

    private void jButtonBorrarAnimalesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonBorrarAnimalesMouseExited
        jButtonBorrarAnimales.setBackground(new Color(103, 0, 3));
        jButtonBorrarAnimales.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonBorrarAnimalesMouseExited

    private void jbuttonBorrarEspeciesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbuttonBorrarEspeciesMouseEntered
        jbuttonBorrarEspecies.setCursor(new Cursor(HAND_CURSOR));
        jbuttonBorrarEspecies.setBackground(new Color(217, 165, 9));
        jbuttonBorrarEspecies.setForeground(Color.BLACK);
    }//GEN-LAST:event_jbuttonBorrarEspeciesMouseEntered

    private void jbuttonBorrarEspeciesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbuttonBorrarEspeciesMouseExited
        jbuttonBorrarEspecies.setBackground(new Color(103, 0, 3));
        jbuttonBorrarEspecies.setForeground(Color.WHITE);
    }//GEN-LAST:event_jbuttonBorrarEspeciesMouseExited

    private void jbuttonBorrarTrabajosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbuttonBorrarTrabajosMouseEntered
        jbuttonBorrarTrabajos.setCursor(new Cursor(HAND_CURSOR));
        jbuttonBorrarTrabajos.setBackground(new Color(217, 165, 9));
        jbuttonBorrarTrabajos.setForeground(Color.BLACK);
    }//GEN-LAST:event_jbuttonBorrarTrabajosMouseEntered

    private void jbuttonBorrarTrabajosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbuttonBorrarTrabajosMouseExited
        jbuttonBorrarTrabajos.setBackground(new Color(103, 0, 3));
        jbuttonBorrarTrabajos.setForeground(Color.WHITE);
    }//GEN-LAST:event_jbuttonBorrarTrabajosMouseExited

    private void jButtonEmpezarCeroMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonEmpezarCeroMouseEntered
        jButtonEmpezarCero.setCursor(new Cursor(HAND_CURSOR));
        jButtonEmpezarCero.setBackground(new Color(217, 165, 9));
        jButtonEmpezarCero.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonEmpezarCeroMouseEntered

    private void jButtonEmpezarCeroMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonEmpezarCeroMouseExited
        jButtonEmpezarCero.setBackground(new Color(103, 0, 3));
        jButtonEmpezarCero.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonEmpezarCeroMouseExited

    private void jButtonAtrasPanicoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasPanicoMouseReleased
        jButtonAtrasPanico.setBackground(new Color(103, 0, 3));
        jButtonAtrasPanico.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonAtrasPanicoMouseReleased

    private void jButtonSuperAdminMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSuperAdminMouseExited
        jButtonSuperAdmin.setBackground(new Color(103, 0, 3));
        jButtonSuperAdmin.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonSuperAdminMouseExited

    private void jButtonCompletarTareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCompletarTareaActionPerformed

        try {
            int fila = jTablePrincipal.getSelectedRow();
            int id = (int) jTablePrincipal.getValueAt(fila, 0);

            //hacer if para comprobar si ya esta marcada por el usuario, si no no puede completarla
            if (!DBManagerZoo.comprobarTareaRealizada(id)) {
                JOptionPane.showMessageDialog(null, "La tarea no puede ser completada hasta que el usuario no la marque como realizada");
            } else {
                DBManagerZoo.completarTarea(id);

                vaciarTablaPrincipal();
                rellenarTablaPrincipal();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "No ha seleccionado ninguna tarea");
        }


    }//GEN-LAST:event_jButtonCompletarTareaActionPerformed

    private void jButtonAtrasHistoricoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasHistoricoMouseReleased
        jButtonAtrasHistorico.setBackground(new Color(103, 0, 3));
        jButtonAtrasHistorico.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonAtrasHistoricoMouseReleased

    private void jButtonHistoricoTareasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonHistoricoTareasMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonHistoricoTareasMousePressed

    private void jFrameHistoricoTareasWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jFrameHistoricoTareasWindowActivated
        jFrameHistoricoTareas.isFocusable();
    }//GEN-LAST:event_jFrameHistoricoTareasWindowActivated

    private void jPanel4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jPanel4MousePressed

    private void jPanel4MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        setLocation(point.x - x, point.y - y);
    }//GEN-LAST:event_jPanel4MouseDragged

    private void jTablePrincipalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePrincipalMouseClicked
        int fila_seleccionada = jTablePrincipal.getSelectedRow();
        if (fila_seleccionada > -1) {
            //JOptionPane.showMessageDialog(null, "no");
            jButtonCompletarTarea.setCursor(new Cursor(HAND_CURSOR));
            jButtonCompletarTarea.setBackground(new Color(217, 165, 9));
            jButtonCompletarTarea.setForeground(Color.BLACK);

            jButtonModificarTarea.setCursor(new Cursor(HAND_CURSOR));
            jButtonModificarTarea.setBackground(new Color(217, 165, 9));
            jButtonModificarTarea.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_jTablePrincipalMouseClicked

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
//            java.util.logging.Logger.getLogger(Panel_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Panel_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Panel_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Panel_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Panel_Admin().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel capa5;
    private javax.swing.JPanel capa_Titulo5;
    private com.github.lgooddatepicker.components.DatePicker datePickerHistorico;
    private com.github.lgooddatepicker.components.DateTimePicker dateTimePickerEditar;
    private javax.swing.JButton jButtonAnimal;
    private javax.swing.JButton jButtonAtrasEditarTarea;
    private javax.swing.JButton jButtonAtrasHistorico;
    private javax.swing.JButton jButtonAtrasPanico;
    private javax.swing.JButton jButtonBorrarAnimales;
    private javax.swing.JButton jButtonBorrarTareas;
    private javax.swing.JButton jButtonCerrarSesion;
    private javax.swing.JButton jButtonCompletarTarea;
    private javax.swing.JButton jButtonEmpezarCero;
    private javax.swing.JButton jButtonEmpleados;
    private javax.swing.JButton jButtonEspecialidad;
    private javax.swing.JButton jButtonEspecies;
    private javax.swing.JButton jButtonHistoricoTareas;
    private javax.swing.JButton jButtonModificarTarea;
    private javax.swing.JButton jButtonMostrarFecha;
    private javax.swing.JButton jButtonSalir;
    private javax.swing.JButton jButtonSuperAdmin;
    private javax.swing.JButton jButtonTareas;
    private javax.swing.JButton jButtonTrabajos;
    private javax.swing.JFrame jFrameEditarTarea;
    private javax.swing.JFrame jFrameHistoricoTareas;
    private javax.swing.JFrame jFrameSuperAdmin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelSesionUser;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTablePrincipal;
    private javax.swing.JTable jTablehistorico;
    private javax.swing.JButton jbModificarAnimal;
    private javax.swing.JButton jbuttonBorrarEmpleados;
    private javax.swing.JButton jbuttonBorrarEspecies;
    private javax.swing.JButton jbuttonBorrarTrabajos;
    private javax.swing.JButton jbuttonBorrarUnaTarea;
    private javax.swing.JComboBox<String> jcEditarAnimal;
    private javax.swing.JComboBox<String> jcEditarCuidador;
    private javax.swing.JComboBox<String> jcEditarTarea;
    private javax.swing.JTextField jtEditarPlusPeligroAltaTarea;
    // End of variables declaration//GEN-END:variables
}
