/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Paneles_Adicionales;

import bbdd_Zoo_Interfaz.DBManagerZoo;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.MouseInfo;
import java.awt.Point;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ismael
 */
public class jFrame_Historico_tareas_Empleados extends javax.swing.JFrame {

    private int x;
    private int y;

    /**
     * Creates new form jFrame_Historico_tareas_Empleados
     */
    public jFrame_Historico_tareas_Empleados(String dni) {
        initComponents();

        UIManager Ui = new UIManager();
        Ui.put("nimbusBlueGrey", new ColorUIResource(103, 0, 3));

        jTablehistoricoEmpleados.getTableHeader().setForeground(Color.white);
        //UIManager.put("nimbusBlueGrey",Color.blue);
        jLabelSesionht.setText(dni);
        
        vaciarTablaHistoricoEmpleados();
        rellenarTablaHistorico(dni);
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

    public void rellenarTablaHistorico(String dni) {
        try {
            ResultSet rs = DBManagerZoo.getTablaTasksEmpleado(dni);
            while (rs.next()) {
                int animalId = rs.getInt(DB_TASKS_ANIMAL);
                String animalName = DBManagerZoo.getAnimalName(animalId);
                String tarea = rs.getString(DB_TASKS_TAREA);
                String fecha = rs.getString(DB_TASKS_FECHA);
                Boolean realizada = rs.getBoolean(DB_TASKS_REALIZADO);

                DefaultTableModel model = (DefaultTableModel) jTablehistoricoEmpleados.getModel();
                Object[] row = {animalName, tarea, fecha, realizada};
                model.addRow(row);
            }
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void rellenarTablaHistoricoFecha(String dni, String fechasel) {
        try {
            ResultSet rs = DBManagerZoo.getTablaTasksFechaDni(dni,fechasel);
            while (rs.next()) {
                int animalId = rs.getInt(DB_TASKS_ANIMAL);
                String animalName = DBManagerZoo.getAnimalName(animalId);
                String tarea = rs.getString(DB_TASKS_TAREA);
                String fecha = rs.getString(DB_TASKS_FECHA);
                Boolean realizada = rs.getBoolean(DB_TASKS_REALIZADO);

                DefaultTableModel model = (DefaultTableModel) jTablehistoricoEmpleados.getModel();
                Object[] row = {animalName, tarea, fecha, realizada};
                model.addRow(row);
            }
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void vaciarTablaHistoricoEmpleados() {

        //vaciamos la tabla recorriendola con un bucle for
        DefaultTableModel tb = (DefaultTableModel) jTablehistoricoEmpleados.getModel();
        int a = jTablehistoricoEmpleados.getRowCount() - 1; //contamos las filas
        //bucle para borrar todas las filas
        for (int i = a; i >= 0; i--) {
            tb.removeRow(tb.getRowCount() - 1);
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

        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        datePickerHistorico = new com.github.lgooddatepicker.components.DatePicker();
        jButtonMostrarFecha = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTablehistoricoEmpleados = new javax.swing.JTable();
        jButtonAtrasHistorico = new javax.swing.JButton();
        jLabelSesionht = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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
        jLabel2.setText("22Historico tareas 22");
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

        jTablehistoricoEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Animal", "Tarea", "Fecha", "Realizada"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablehistoricoEmpleados.setBackground(new java.awt.Color(255, 255, 255));
        jTablehistoricoEmpleados.setForeground(new java.awt.Color(0, 0, 0));
        jScrollPane3.setViewportView(jTablehistoricoEmpleados);

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

        jLabelSesionht.setText("f");

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
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAtrasHistorico)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelSesionht, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonAtrasHistorico)
                    .addComponent(jLabelSesionht))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(datePickerHistorico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonMostrarFecha))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
        
        String fecha = datePickerHistorico.getDate().toString();
        String dni = jLabelSesionht.getText();

        try {

            vaciarTablaHistoricoEmpleados();
            rellenarTablaHistoricoFecha(dni,fecha);

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "No puede dejar el campo fecha vacío");
        }

    }//GEN-LAST:event_jButtonMostrarFechaActionPerformed

    private void jButtonAtrasHistoricoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasHistoricoMouseEntered
        jButtonAtrasHistorico.setCursor(new Cursor(HAND_CURSOR));
        jButtonAtrasHistorico.setBackground(new Color(217, 165, 9));
        jButtonAtrasHistorico.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonAtrasHistoricoMouseEntered

    private void jButtonAtrasHistoricoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasHistoricoMouseExited
        jButtonAtrasHistorico.setBackground(new Color(103, 0, 3));
        jButtonAtrasHistorico.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonAtrasHistoricoMouseExited

    private void jButtonAtrasHistoricoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasHistoricoMouseReleased
        jButtonAtrasHistorico.setBackground(new Color(103, 0, 3));
        jButtonAtrasHistorico.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonAtrasHistoricoMouseReleased

    private void jButtonAtrasHistoricoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtrasHistoricoActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonAtrasHistoricoActionPerformed

    private void jPanel4MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        setLocation(point.x - x, point.y - y);
    }//GEN-LAST:event_jPanel4MouseDragged

    private void jPanel4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jPanel4MousePressed

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
//            java.util.logging.Logger.getLogger(jFrame_Historico_tareas_Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(jFrame_Historico_tareas_Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(jFrame_Historico_tareas_Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(jFrame_Historico_tareas_Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new jFrame_Historico_tareas_Empleados().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.github.lgooddatepicker.components.DatePicker datePickerHistorico;
    private javax.swing.JButton jButtonAtrasHistorico;
    private javax.swing.JButton jButtonMostrarFecha;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelSesionht;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTablehistoricoEmpleados;
    // End of variables declaration//GEN-END:variables
}
