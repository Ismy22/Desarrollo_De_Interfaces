/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package bbdd_Zoo_Interfaz;

import java.awt.Color;
import java.awt.Cursor;
import static java.awt.Frame.DEFAULT_CURSOR;
import static java.awt.Frame.HAND_CURSOR;
import java.awt.MouseInfo;
import java.awt.Point;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ismael
 */
public class frame_Tasks extends javax.swing.JFrame {

    private int x;
    private int y;
    /**
     * Creates new form frame_Specialty
     */
    public frame_Tasks() {
        initComponents();
        listAnimals();
        listCaregivers();
        listPlusSalario();
    }

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
    private static final String DB_CAREGIVERS_PASS = "CONTRASEÑA";
    private static final String DB_CAREGIVERS_ESPE = "ESPECIALIDAD";
    private static final String DB_CAREGIVERS_CARGO = "CARGO";
    private static final String DB_CAREGIVERS_SALARIO = "SALARIO_BASE";

    // Configuración de la tabla Trabajo
    private static final String DB_TRABAJO = "TRABAJO";
    private static final String DB_TRABAJO_SELECT = "SELECT * FROM " + DB_TRABAJO;
    private static final String DB_TRABAJO_ID_TRABAJO = "ID_TRABAJO";
    private static final String DB_TRABAJO_NOMBRE_TRABAJO = "NOMBRE_TRABAJO";

    //////////////////////////////////////////////////
    // MÉTODOS PARA TABLA CAREGIVERS
    //////////////////////////////////////////////////
    //método para poner los nombres de los animales en un jComboBox
    public void listAnimals() {

        try {
            ResultSet rs = DBManagerZoo.getTablaAnimals(DEFAULT_CURSOR, DISPOSE_ON_CLOSE);
            while (rs.next()) {

                String nombre = rs.getString(DB_ANIMALS_NOM);
                jcAnimal.addItem(nombre);

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

                String nombre = rs.getString(DB_TRABAJO_NOMBRE_TRABAJO);
                jcTarea.addItem(nombre);

            }
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    //método para poner los nombres de los cuidadores en un jComboBox
    public void listCaregivers() {

        try {
            ResultSet rs = DBManagerZoo.getTablaCaregiversEmpleados(DEFAULT_CURSOR, DISPOSE_ON_CLOSE);
            while (rs.next()) {

                String nombre = rs.getString(DB_CAREGIVERS_NOM);
                //String dni = rs.getString(DB_CAREGIVERS_DNI);
                //String apellido = rs.getString(DB_CAREGIVERS_APE);
                jcCuidador.addItem(nombre);//+" "+apellido);

            }
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    //método para poner el plus salario
    public void listPlusSalario() {

        int animalId = DBManagerZoo.getIdAnimal(jcAnimal.getSelectedItem().toString());
        //System.out.println(extraerId);
        //System.out.println(id+"hola");
        String especie = DBManagerZoo.getEspecieAnimal(animalId);
        int nPeligro = DBManagerZoo.getPeligroAnimal(especie);

        switch (nPeligro) {
            case 0:
                jtPlusPeligroAltaTarea.setText("0");
                break;
            case 1:
                jtPlusPeligroAltaTarea.setText("100");
                break;
            case 2:
                jtPlusPeligroAltaTarea.setText("200");
                break;
            case 3:
                jtPlusPeligroAltaTarea.setText("300");
                break;
            case 4:
                jtPlusPeligroAltaTarea.setText("400");
                break;
            case 5:
                jtPlusPeligroAltaTarea.setText("500");
                break;
            default:
                throw new AssertionError();
        }

    }

    //recoge los datos introducidos y se los pasa al método insertTasks de la base de datos
    public void insertTasks() {

        String tarea = jcTarea.getSelectedItem().toString();
        //String animal = jcAnimal.getSelectedItem().toString();
        //System.out.println(animal);
        int animalId = DBManagerZoo.getIdAnimal(jcAnimal.getSelectedItem().toString());
        String cuidador = jcCuidador.getSelectedItem().toString();
        String cuidadorDni = DBManagerZoo.getDNICaregivers(cuidador);
        float plus_Salario = Float.parseFloat(jtPlusPeligroAltaTarea.getText());
        
        String date = dateTimePicker1.getDatePicker().toString();
        String hora = dateTimePicker1.getTimePicker().toString();
        String dateTime = "" + date + " " + hora;

        DBManagerZoo.insertTasks(tarea, animalId, cuidadorDni, plus_Salario, dateTime);

    }

//    public void vaciarFrameAddTasks() {
//
//        jtPeligroAltaTarea.setText("");
//
//    }
//    public String devolverFecha() {
//
//        Date fecha = new Date(Calendar.getInstance().getTimeInMillis());
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//        String fechaTexto = formatter.format(fecha);
//        System.out.println(fechaTexto);
//        return fechaTexto;
//
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        capa0 = new javax.swing.JPanel();
        capa_Titulo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButtonAtrasAltaTarea = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jbGuardarAltaTarea = new javax.swing.JButton();
        jcTarea = new javax.swing.JComboBox<>();
        jcCuidador = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jcAnimal = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jtPlusPeligroAltaTarea = new javax.swing.JTextField();
        dateTimePicker1 = new com.github.lgooddatepicker.components.DateTimePicker();

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

        capa0.setBackground(new java.awt.Color(255, 255, 255));
        capa0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        capa_Titulo.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Alta Tarea");
        jLabel1.setFont(new java.awt.Font("Jurassic Park", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jButtonAtrasAltaTarea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Login/Imagen/hacia-atras (1).png"))); // NOI18N
        jButtonAtrasAltaTarea.setBackground(new java.awt.Color(103, 0, 3));
        jButtonAtrasAltaTarea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonAtrasAltaTareaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonAtrasAltaTareaMouseExited(evt);
            }
        });
        jButtonAtrasAltaTarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAtrasAltaTareaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout capa_TituloLayout = new javax.swing.GroupLayout(capa_Titulo);
        capa_Titulo.setLayout(capa_TituloLayout);
        capa_TituloLayout.setHorizontalGroup(
            capa_TituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
            .addGroup(capa_TituloLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAtrasAltaTarea)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        capa_TituloLayout.setVerticalGroup(
            capa_TituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(capa_TituloLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAtrasAltaTarea)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jLabel2.setText("Tarea");
        jLabel2.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));

        jLabel4.setText("Plus peligro");
        jLabel4.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));

        jLabel5.setText("Cuidador");
        jLabel5.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jbGuardarAltaTarea.setText("Guardar");
        jbGuardarAltaTarea.setBackground(new java.awt.Color(113, 0, 3));
        jbGuardarAltaTarea.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jbGuardarAltaTarea.setForeground(new java.awt.Color(255, 255, 255));
        jbGuardarAltaTarea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbGuardarAltaTareaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbGuardarAltaTareaMouseExited(evt);
            }
        });
        jbGuardarAltaTarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGuardarAltaTareaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(205, 205, 205)
                .addComponent(jbGuardarAltaTarea)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(jbGuardarAltaTarea)
                .addContainerGap())
        );

        jcTarea.setBackground(new java.awt.Color(255, 255, 255));
        jcTarea.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jcTarea.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jcTarea.setForeground(new java.awt.Color(0, 0, 0));

        jcCuidador.setBackground(new java.awt.Color(255, 255, 255));
        jcCuidador.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jcCuidador.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jcCuidador.setForeground(new java.awt.Color(0, 0, 0));

        jLabel7.setText("Animal");
        jLabel7.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));

        jcAnimal.setBackground(new java.awt.Color(255, 255, 255));
        jcAnimal.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jcAnimal.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jcAnimal.setForeground(new java.awt.Color(0, 0, 0));
        jcAnimal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcAnimalItemStateChanged(evt);
            }
        });

        jLabel8.setText("Fecha y Hora");
        jLabel8.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));

        jtPlusPeligroAltaTarea.setEditable(false);
        jtPlusPeligroAltaTarea.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jtPlusPeligroAltaTarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtPlusPeligroAltaTareaActionPerformed(evt);
            }
        });

        dateTimePicker1.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N

        javax.swing.GroupLayout capa0Layout = new javax.swing.GroupLayout(capa0);
        capa0.setLayout(capa0Layout);
        capa0Layout.setHorizontalGroup(
            capa0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(capa_Titulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(capa0Layout.createSequentialGroup()
                .addGroup(capa0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, capa0Layout.createSequentialGroup()
                        .addGap(0, 108, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(36, 36, 36)
                        .addComponent(jcAnimal, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(capa0Layout.createSequentialGroup()
                        .addContainerGap(58, Short.MAX_VALUE)
                        .addGroup(capa0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(capa0Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(41, 41, 41)
                                .addComponent(jcTarea, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(capa0Layout.createSequentialGroup()
                                .addGroup(capa0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel8))
                                .addGap(36, 36, 36)
                                .addGroup(capa0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jtPlusPeligroAltaTarea, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jcCuidador, javax.swing.GroupLayout.Alignment.TRAILING, 0, 265, Short.MAX_VALUE)
                                    .addComponent(dateTimePicker1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        capa0Layout.setVerticalGroup(
            capa0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(capa0Layout.createSequentialGroup()
                .addComponent(capa_Titulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(capa0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jcTarea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(capa0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jcAnimal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(capa0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jcCuidador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(capa0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtPlusPeligroAltaTarea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(capa0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(dateTimePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(capa0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(capa0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbGuardarAltaTareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGuardarAltaTareaActionPerformed
        String date = dateTimePicker1.getDatePicker().toString();
        String cuidador = jcCuidador.getSelectedItem().toString();
        String hora = dateTimePicker1.getTimePicker().toString();
        //System.out.println(hora);
        String dateTime = "" + date + " " + hora;
        if (DBManagerZoo.comprobarTarea(dateTime)) {
            JOptionPane.showMessageDialog(null, "Ya hay una tarea a esa hora en esa fecha para el empleado "+ cuidador+"\nSeleccione una fecha/hora diferente.");
        }else{
        insertTasks();
        this.dispose();
        JOptionPane.showMessageDialog(null, "Tarea guardada correctamente");
        
        }

    }//GEN-LAST:event_jbGuardarAltaTareaActionPerformed

    private void jcAnimalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcAnimalItemStateChanged
        listPlusSalario();
    }//GEN-LAST:event_jcAnimalItemStateChanged

    private void jtPlusPeligroAltaTareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtPlusPeligroAltaTareaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtPlusPeligroAltaTareaActionPerformed

    private void jButtonAtrasAltaTareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtrasAltaTareaActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonAtrasAltaTareaActionPerformed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        setLocation(point.x - x, point.y - y);
    }//GEN-LAST:event_formMouseDragged

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void jButtonAtrasAltaTareaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasAltaTareaMouseEntered
        jButtonAtrasAltaTarea.setCursor(new Cursor(HAND_CURSOR));
        jButtonAtrasAltaTarea.setBackground( new Color(217,165,9));
        jButtonAtrasAltaTarea.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonAtrasAltaTareaMouseEntered

    private void jButtonAtrasAltaTareaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasAltaTareaMouseExited
        jButtonAtrasAltaTarea.setBackground( new Color(103,0,3));
        jButtonAtrasAltaTarea.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonAtrasAltaTareaMouseExited

    private void jbGuardarAltaTareaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbGuardarAltaTareaMouseEntered
        jbGuardarAltaTarea.setCursor(new Cursor(HAND_CURSOR));
        jbGuardarAltaTarea.setBackground( new Color(217,165,9));
        jbGuardarAltaTarea.setForeground(Color.BLACK);
    }//GEN-LAST:event_jbGuardarAltaTareaMouseEntered

    private void jbGuardarAltaTareaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbGuardarAltaTareaMouseExited
        jbGuardarAltaTarea.setBackground( new Color(103,0,3));
        jbGuardarAltaTarea.setForeground(Color.WHITE);
    }//GEN-LAST:event_jbGuardarAltaTareaMouseExited

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
//            java.util.logging.Logger.getLogger(frame_Tasks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(frame_Tasks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(frame_Tasks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(frame_Tasks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new frame_Tasks().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel capa0;
    private javax.swing.JPanel capa_Titulo;
    private com.github.lgooddatepicker.components.DateTimePicker dateTimePicker1;
    private javax.swing.JButton jButtonAtrasAltaTarea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbGuardarAltaTarea;
    private javax.swing.JComboBox<String> jcAnimal;
    private javax.swing.JComboBox<String> jcCuidador;
    private javax.swing.JComboBox<String> jcTarea;
    private javax.swing.JTextField jtPlusPeligroAltaTarea;
    // End of variables declaration//GEN-END:variables
}
