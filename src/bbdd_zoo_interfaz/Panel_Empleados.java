/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package bbdd_Zoo_Interfaz;

import bbdd_Zoo_Interfaz.DBManagerZoo;
import java.awt.Color;
import java.awt.Cursor;
import static java.awt.Frame.DEFAULT_CURSOR;
import static java.awt.Frame.HAND_CURSOR;
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
public class Panel_Empleados extends javax.swing.JFrame {

    private int x;
    private int y;

    /**
     * Creates new form Panel_Empleados
     */
    public Panel_Empleados(String arg) {
        initComponents();
        UIManager Ui = new UIManager();
        Ui.put("nimbusBlueGrey", new ColorUIResource(103, 0, 3));

        jTableLoginEmpleados.getTableHeader().setForeground(Color.white);
        //UIManager.put("nimbusBlueGrey",Color.blue);

        String dni = arg;
        rellenarTablaEmpleadosNoRealizadas(dni);

        jlabelSesiondni.setText(dni);
        jLabelBienvenidoNombre.setText(DBManagerZoo.getCaregiverName(dni));
        jLabelEspecialidadEmpleado.setText(DBManagerZoo.getEspecialidadCaregivers(dni));
        float plusEspecialidad = DBManagerZoo.getPlusSalario(DBManagerZoo.getEspecialidadCaregivers(dni));
        float salarioBase = DBManagerZoo.getSalarioCaregivers(dni);
        float salarioTarea = DBManagerZoo.getPlusSalarioTarea(dni);
        System.out.println(salarioTarea);
        float total = plusEspecialidad + salarioBase + salarioTarea;
        jLabelDineroTotalEmpleado.setText(Float.toString(total) + "€");

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

    public void vaciarTablaEmpleados() {

        //vaciamos la tabla recorriendola con un bucle for
        DefaultTableModel tb = (DefaultTableModel) jTableLoginEmpleados.getModel();
        int a = jTableLoginEmpleados.getRowCount() - 1; //contamos las filas
        //bucle para borrar todas las filas
        for (int i = a; i >= 0; i--) {
            tb.removeRow(tb.getRowCount() - 1);
        }

    }

    public static boolean updateTareaRealizada(int id) {

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
                rs.updateBoolean("TAREA_REALIZADA", true);

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

    public static boolean updateDsmarcarTarea(int id) {

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
                rs.updateBoolean("TAREA_REALIZADA", false);

                rs.updateRow();
                rs.close();
                JOptionPane.showMessageDialog(null, "Tarea desmarcada correctamente", "Tarea no realizada", JOptionPane.INFORMATION_MESSAGE);
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

    public void rellenarTablaEmpleadosFecha() {
        String dni = jlabelSesiondni.getText();
        String fecha = datePickerHistorico.getDate().toString();
        try {
            ResultSet rs = DBManagerZoo.getTablaTasksFecha(DEFAULT_CURSOR, DISPOSE_ON_CLOSE, fecha, dni);
            while (rs.next()) {
                int idtarea = rs.getInt(DB_TASKS_ID_TAREA);
                int animalId = rs.getInt(DB_TASKS_ANIMAL);
                String animalName = DBManagerZoo.getAnimalName(animalId);
                String plus = rs.getString(DB_TASKS_PLUS_SALARIO);
                String tarea = rs.getString(DB_TASKS_TAREA);
                String fecha1 = rs.getString(DB_TASKS_FECHA);
                Boolean realizada = rs.getBoolean(DB_TASKS_REALIZADO);

                DefaultTableModel model = (DefaultTableModel) jTableLoginEmpleados.getModel();
                Object[] row = {idtarea, tarea, animalName, tarea, fecha1, realizada};
                model.addRow(row);
            }
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void rellenarTablaEmpleadosNoRealizadas(String dni) {
        try {
            ResultSet rs = DBManagerZoo.getTablaTasksNoRealizada(dni);
            while (rs.next()) {
                int idtarea = rs.getInt(DB_TASKS_ID_TAREA);
                int animalId = rs.getInt(DB_TASKS_ANIMAL);
                String animalName = DBManagerZoo.getAnimalName(animalId);
                String plus = rs.getString(DB_TASKS_PLUS_SALARIO);
                String tarea = rs.getString(DB_TASKS_TAREA);
                String fecha = rs.getString(DB_TASKS_FECHA);
                Boolean realizada = rs.getBoolean(DB_TASKS_REALIZADO);

                DefaultTableModel model = (DefaultTableModel) jTableLoginEmpleados.getModel();
                Object[] row = {idtarea, tarea, animalName, tarea, fecha, realizada};
                model.addRow(row);
            }
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
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

        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableLoginEmpleados = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabelBienvenidoNombre = new javax.swing.JLabel();
        jLabelEspecialidadEmpleado = new javax.swing.JLabel();
        jLabelDineroTotalEmpleado = new javax.swing.JLabel();
        jButtonTareaRealizada = new javax.swing.JButton();
        jButtonCerrarVisitante = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jButtonSalirVisitante = new javax.swing.JButton();
        jButtonDesmarcarTarea = new javax.swing.JButton();
        jButtonMostrarFecha = new javax.swing.JButton();
        datePickerHistorico = new com.github.lgooddatepicker.components.DatePicker();
        jButtonDesmarcarTarea1 = new javax.swing.JButton();
        jlabelSesiondni = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel7.setForeground(new java.awt.Color(0, 0, 0));

        jTableLoginEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Tarea", "Animal", "Salario extra", "Fecha", "Realizada"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Boolean.class
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
        jTableLoginEmpleados.setForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(jTableLoginEmpleados);
        if (jTableLoginEmpleados.getColumnModel().getColumnCount() > 0) {
            jTableLoginEmpleados.getColumnModel().getColumn(0).setResizable(false);
            jTableLoginEmpleados.getColumnModel().getColumn(1).setResizable(false);
            jTableLoginEmpleados.getColumnModel().getColumn(2).setResizable(false);
            jTableLoginEmpleados.getColumnModel().getColumn(3).setResizable(false);
            jTableLoginEmpleados.getColumnModel().getColumn(4).setResizable(false);
            jTableLoginEmpleados.getColumnModel().getColumn(5).setResizable(false);
        }

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Salario Total");
        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Jurassic Park", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));

        jLabelBienvenidoNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelBienvenidoNombre.setText("Bienvenido nombre");
        jLabelBienvenidoNombre.setBackground(new java.awt.Color(255, 255, 255));
        jLabelBienvenidoNombre.setFont(new java.awt.Font("Jurassic Park", 1, 48)); // NOI18N
        jLabelBienvenidoNombre.setForeground(new java.awt.Color(0, 0, 0));

        jLabelEspecialidadEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelEspecialidadEmpleado.setText("Especialidad");
        jLabelEspecialidadEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        jLabelEspecialidadEmpleado.setFont(new java.awt.Font("Jurassic Park", 1, 36)); // NOI18N
        jLabelEspecialidadEmpleado.setForeground(new java.awt.Color(0, 0, 0));

        jLabelDineroTotalEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelDineroTotalEmpleado.setText("Dinero");
        jLabelDineroTotalEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        jLabelDineroTotalEmpleado.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabelDineroTotalEmpleado.setForeground(new java.awt.Color(0, 0, 0));

        jButtonTareaRealizada.setText("Tarea realizada");
        jButtonTareaRealizada.setBackground(new java.awt.Color(103, 0, 3));
        jButtonTareaRealizada.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButtonTareaRealizada.setForeground(new java.awt.Color(255, 255, 255));
        jButtonTareaRealizada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonTareaRealizadaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonTareaRealizadaMouseExited(evt);
            }
        });
        jButtonTareaRealizada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTareaRealizadaActionPerformed(evt);
            }
        });

        jButtonCerrarVisitante.setText("Cerrar Sesión");
        jButtonCerrarVisitante.setBackground(new java.awt.Color(103, 0, 3));
        jButtonCerrarVisitante.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        jButtonCerrarVisitante.setForeground(new java.awt.Color(255, 255, 255));
        jButtonCerrarVisitante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonCerrarVisitanteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonCerrarVisitanteMouseExited(evt);
            }
        });
        jButtonCerrarVisitante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCerrarVisitanteActionPerformed(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Login/Imagen/trabajador.png"))); // NOI18N
        jLabel6.setText("jLabel2");

        jButtonSalirVisitante.setText("Salir");
        jButtonSalirVisitante.setBackground(new java.awt.Color(103, 0, 3));
        jButtonSalirVisitante.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        jButtonSalirVisitante.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSalirVisitante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonSalirVisitanteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonSalirVisitanteMouseExited(evt);
            }
        });
        jButtonSalirVisitante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalirVisitanteActionPerformed(evt);
            }
        });

        jButtonDesmarcarTarea.setText("Desmarcar Tarea");
        jButtonDesmarcarTarea.setBackground(new java.awt.Color(103, 0, 3));
        jButtonDesmarcarTarea.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButtonDesmarcarTarea.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDesmarcarTarea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonDesmarcarTareaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonDesmarcarTareaMouseExited(evt);
            }
        });
        jButtonDesmarcarTarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDesmarcarTareaActionPerformed(evt);
            }
        });

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

        datePickerHistorico.setBackground(new java.awt.Color(255, 255, 255));
        datePickerHistorico.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        datePickerHistorico.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        jButtonDesmarcarTarea1.setBackground(new java.awt.Color(103, 0, 3));
        jButtonDesmarcarTarea1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButtonDesmarcarTarea1.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDesmarcarTarea1.setText("Histórico");
        jButtonDesmarcarTarea1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonDesmarcarTarea1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonDesmarcarTarea1MouseExited(evt);
            }
        });
        jButtonDesmarcarTarea1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDesmarcarTarea1ActionPerformed(evt);
            }
        });

        jlabelSesiondni.setBackground(new java.awt.Color(255, 255, 255));
        jlabelSesiondni.setForeground(new java.awt.Color(255, 255, 255));
        jlabelSesiondni.setText("f");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                        .addComponent(jButtonTareaRealizada)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                                .addComponent(jButtonDesmarcarTarea)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButtonDesmarcarTarea1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(datePickerHistorico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jButtonMostrarFecha, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addComponent(jLabelBienvenidoNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jButtonSalirVisitante)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButtonCerrarVisitante)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabelDineroTotalEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(176, 176, 176))))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jlabelSesiondni, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
                        .addComponent(jLabelEspecialidadEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE))
                    .addContainerGap(400, Short.MAX_VALUE)))
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButtonDesmarcarTarea, jButtonTareaRealizada});

        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSalirVisitante)
                    .addComponent(jButtonCerrarVisitante))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlabelSesiondni)
                .addGap(1, 1, 1)
                .addComponent(jLabelBienvenidoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(103, 103, 103)
                .addComponent(jLabelDineroTotalEmpleado)
                .addGap(4, 4, 4)
                .addComponent(jButtonMostrarFecha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(datePickerHistorico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonTareaRealizada, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonDesmarcarTarea)
                            .addComponent(jButtonDesmarcarTarea1))
                        .addContainerGap())))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel6))
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addGap(111, 111, 111)
                    .addComponent(jLabelEspecialidadEmpleado)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel1)
                    .addContainerGap(436, Short.MAX_VALUE)))
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButtonDesmarcarTarea, jButtonTareaRealizada});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        setLocation(point.x - x, point.y - y);
    }//GEN-LAST:event_formMouseDragged

    private void jButtonDesmarcarTareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDesmarcarTareaActionPerformed
        int fila = jTableLoginEmpleados.getSelectedRow();
        int id = (int) jTableLoginEmpleados.getValueAt(fila, 0);
        updateDsmarcarTarea(id);
        String dni = DBManagerZoo.getDNICaregivers(jLabelBienvenidoNombre.getText());
        vaciarTablaEmpleados();
        rellenarTablaEmpleadosNoRealizadas(dni);
    }//GEN-LAST:event_jButtonDesmarcarTareaActionPerformed

    private void jButtonSalirVisitanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalirVisitanteActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonSalirVisitanteActionPerformed

    private void jButtonCerrarVisitanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCerrarVisitanteActionPerformed
        this.dispose();
        Login.LoginUser open = new Login.LoginUser();
        open.setVisible(true);
    }//GEN-LAST:event_jButtonCerrarVisitanteActionPerformed

    private void jButtonTareaRealizadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTareaRealizadaActionPerformed
        int fila = jTableLoginEmpleados.getSelectedRow();
        int id = (int) jTableLoginEmpleados.getValueAt(fila, 0);
        updateTareaRealizada(id);
        String dni = DBManagerZoo.getDNICaregivers(jLabelBienvenidoNombre.getText());
        vaciarTablaEmpleados();
        rellenarTablaEmpleadosNoRealizadas(dni);
    }//GEN-LAST:event_jButtonTareaRealizadaActionPerformed

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void jButtonSalirVisitanteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSalirVisitanteMouseEntered
        jButtonSalirVisitante.setCursor(new Cursor(HAND_CURSOR));
        jButtonSalirVisitante.setBackground(new Color(217, 165, 9));
        jButtonSalirVisitante.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonSalirVisitanteMouseEntered

    private void jButtonSalirVisitanteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSalirVisitanteMouseExited
        jButtonSalirVisitante.setBackground(new Color(103, 0, 3));
        jButtonSalirVisitante.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonSalirVisitanteMouseExited

    private void jButtonCerrarVisitanteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonCerrarVisitanteMouseEntered
        jButtonCerrarVisitante.setCursor(new Cursor(HAND_CURSOR));
        jButtonCerrarVisitante.setBackground(new Color(217, 165, 9));
        jButtonCerrarVisitante.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonCerrarVisitanteMouseEntered

    private void jButtonCerrarVisitanteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonCerrarVisitanteMouseExited
        jButtonCerrarVisitante.setBackground(new Color(103, 0, 3));
        jButtonCerrarVisitante.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonCerrarVisitanteMouseExited

    private void jButtonDesmarcarTareaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonDesmarcarTareaMouseEntered
        jButtonDesmarcarTarea.setCursor(new Cursor(HAND_CURSOR));
        jButtonDesmarcarTarea.setBackground(new Color(217, 165, 9));
        jButtonDesmarcarTarea.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonDesmarcarTareaMouseEntered

    private void jButtonDesmarcarTareaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonDesmarcarTareaMouseExited
        jButtonDesmarcarTarea.setBackground(new Color(103, 0, 3));
        jButtonDesmarcarTarea.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonDesmarcarTareaMouseExited

    private void jButtonTareaRealizadaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonTareaRealizadaMouseEntered
        jButtonTareaRealizada.setCursor(new Cursor(HAND_CURSOR));
        jButtonTareaRealizada.setBackground(new Color(217, 165, 9));
        jButtonTareaRealizada.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonTareaRealizadaMouseEntered

    private void jButtonTareaRealizadaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonTareaRealizadaMouseExited
        jButtonTareaRealizada.setBackground(new Color(103, 0, 3));
        jButtonTareaRealizada.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonTareaRealizadaMouseExited

    private void jButtonMostrarFechaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonMostrarFechaMouseEntered
        jButtonMostrarFecha.setBackground(new Color(217, 165, 9));
        jButtonMostrarFecha.setForeground(Color.BLACK);
        jButtonMostrarFecha.setCursor(new Cursor(HAND_CURSOR));
    }//GEN-LAST:event_jButtonMostrarFechaMouseEntered

    private void jButtonMostrarFechaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonMostrarFechaMouseExited
        jButtonMostrarFecha.setBackground(new Color(103, 0, 3));
        jButtonMostrarFecha.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonMostrarFechaMouseExited

    private void jButtonMostrarFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMostrarFechaActionPerformed
        try {

            vaciarTablaEmpleados();
            rellenarTablaEmpleadosFecha();

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "No puede dejar el campo fecha vacío");
            String dni = jlabelSesiondni.getText();
            rellenarTablaEmpleadosNoRealizadas(dni);
        }

    }//GEN-LAST:event_jButtonMostrarFechaActionPerformed

    private void jButtonDesmarcarTarea1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonDesmarcarTarea1MouseEntered
        jButtonDesmarcarTarea1.setBackground(new Color(217, 165, 9));
        jButtonDesmarcarTarea1.setForeground(Color.BLACK);
        jButtonDesmarcarTarea1.setCursor(new Cursor(HAND_CURSOR));
    }//GEN-LAST:event_jButtonDesmarcarTarea1MouseEntered

    private void jButtonDesmarcarTarea1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonDesmarcarTarea1MouseExited
        jButtonDesmarcarTarea1.setBackground(new Color(103, 0, 3));
        jButtonDesmarcarTarea1.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonDesmarcarTarea1MouseExited

    private void jButtonDesmarcarTarea1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDesmarcarTarea1ActionPerformed
        String dni = jlabelSesiondni.getText();
        Paneles_Adicionales.jFrame_Historico_tareas_Empleados open = new Paneles_Adicionales.jFrame_Historico_tareas_Empleados(dni);
        open.setVisible(true);
    }//GEN-LAST:event_jButtonDesmarcarTarea1ActionPerformed

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
//            java.util.logging.Logger.getLogger(Panel_Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Panel_Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Panel_Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Panel_Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Panel_Empleados().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.github.lgooddatepicker.components.DatePicker datePickerHistorico;
    private javax.swing.JButton jButtonCerrarVisitante;
    private javax.swing.JButton jButtonDesmarcarTarea;
    private javax.swing.JButton jButtonDesmarcarTarea1;
    private javax.swing.JButton jButtonMostrarFecha;
    private javax.swing.JButton jButtonSalirVisitante;
    private javax.swing.JButton jButtonTareaRealizada;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelBienvenidoNombre;
    private javax.swing.JLabel jLabelDineroTotalEmpleado;
    private javax.swing.JLabel jLabelEspecialidadEmpleado;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableLoginEmpleados;
    private javax.swing.JLabel jlabelSesiondni;
    // End of variables declaration//GEN-END:variables
}
