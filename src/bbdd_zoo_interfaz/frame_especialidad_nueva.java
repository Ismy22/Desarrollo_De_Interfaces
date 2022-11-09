/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package bbdd_Zoo_Interfaz;

import Utilidades.TextPrompt;
import bbdd_Zoo_Interfaz.DBManagerZoo;
import java.awt.Color;
import java.awt.Cursor;
import static java.awt.Frame.HAND_CURSOR;
import java.awt.MouseInfo;
import java.awt.Point;
import javax.swing.JOptionPane;

/**
 *
 * @author Ismael
 */
public class frame_especialidad_nueva extends javax.swing.JFrame {

    private int x;
    private int y;
    /**
     * Creates new form frame_especialidad_nueva
     */
    public frame_especialidad_nueva() {
        initComponents();
        TextPrompt placeholcer = new TextPrompt("Domador de Velocirraptors", jTEspecialidad);
        TextPrompt placeholcer2 = new TextPrompt("600", jTPlusSalario);
    }
    
    public void insertEspecialidad() {

        String nombre = jTEspecialidad.getText();
        Float plus_Salario = Float.parseFloat(jTPlusSalario.getText());

        boolean comprobar = DBManagerZoo.insertEspecialidad(nombre, plus_Salario);
        
        if (comprobar) {
            dispose();
        }
        else{
            JOptionPane.showConfirmDialog(null, "no se ha podido guardar la especialidad");
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTEspecialidad = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButtonGuardarEspecialidad = new javax.swing.JButton();
        jTPlusSalario = new javax.swing.JTextField();
        jButtonAtrasNuevaEspecialidad = new javax.swing.JButton();

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

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel1.setFont(new java.awt.Font("Jurassic Park", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Nueva   Especialidad");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Especialidad");

        jTEspecialidad.setBackground(new java.awt.Color(255, 255, 255));
        jTEspecialidad.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jTEspecialidad.setForeground(new java.awt.Color(0, 0, 0));
        jTEspecialidad.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Plus Salario");

        jButtonGuardarEspecialidad.setBackground(new java.awt.Color(103, 0, 3));
        jButtonGuardarEspecialidad.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jButtonGuardarEspecialidad.setForeground(new java.awt.Color(255, 255, 255));
        jButtonGuardarEspecialidad.setText("Guardar");
        jButtonGuardarEspecialidad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonGuardarEspecialidadMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonGuardarEspecialidadMouseExited(evt);
            }
        });
        jButtonGuardarEspecialidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarEspecialidadActionPerformed(evt);
            }
        });

        jTPlusSalario.setBackground(new java.awt.Color(255, 255, 255));
        jTPlusSalario.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jTPlusSalario.setForeground(new java.awt.Color(0, 0, 0));
        jTPlusSalario.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jButtonAtrasNuevaEspecialidad.setBackground(new java.awt.Color(103, 0, 3));
        jButtonAtrasNuevaEspecialidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Login/Imagen/hacia-atras (1).png"))); // NOI18N
        jButtonAtrasNuevaEspecialidad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonAtrasNuevaEspecialidadMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonAtrasNuevaEspecialidadMouseExited(evt);
            }
        });
        jButtonAtrasNuevaEspecialidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAtrasNuevaEspecialidadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTPlusSalario, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addComponent(jButtonGuardarEspecialidad))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButtonAtrasNuevaEspecialidad)))
                .addContainerGap(8, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAtrasNuevaEspecialidad)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jTEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTPlusSalario, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jButtonGuardarEspecialidad)
                .addContainerGap(28, Short.MAX_VALUE))
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

    private void jButtonGuardarEspecialidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarEspecialidadActionPerformed

        if (jTEspecialidad.getText().equals("") || jTPlusSalario.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "No puede dejar ningún campo vacío");
        }
        else{
            insertEspecialidad();
        }
    }//GEN-LAST:event_jButtonGuardarEspecialidadActionPerformed

    private void jButtonAtrasNuevaEspecialidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtrasNuevaEspecialidadActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonAtrasNuevaEspecialidadActionPerformed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        setLocation(point.x - x, point.y - y);
    }//GEN-LAST:event_formMouseDragged

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void jButtonAtrasNuevaEspecialidadMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasNuevaEspecialidadMouseEntered
        jButtonAtrasNuevaEspecialidad.setCursor(new Cursor(HAND_CURSOR));
        jButtonAtrasNuevaEspecialidad.setBackground( new Color(217,165,9));
        jButtonAtrasNuevaEspecialidad.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonAtrasNuevaEspecialidadMouseEntered

    private void jButtonAtrasNuevaEspecialidadMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasNuevaEspecialidadMouseExited
        jButtonAtrasNuevaEspecialidad.setBackground( new Color(103,0,3));
        jButtonAtrasNuevaEspecialidad.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonAtrasNuevaEspecialidadMouseExited

    private void jButtonGuardarEspecialidadMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonGuardarEspecialidadMouseEntered
        jButtonGuardarEspecialidad.setCursor(new Cursor(HAND_CURSOR));
        jButtonGuardarEspecialidad.setBackground( new Color(217,165,9));
        jButtonGuardarEspecialidad.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonGuardarEspecialidadMouseEntered

    private void jButtonGuardarEspecialidadMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonGuardarEspecialidadMouseExited
        jButtonGuardarEspecialidad.setBackground( new Color(103,0,3));
        jButtonGuardarEspecialidad.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonGuardarEspecialidadMouseExited

    /**
     * @param args the command line arguments
//     */
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
//            java.util.logging.Logger.getLogger(frame_especialidad_nueva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(frame_especialidad_nueva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(frame_especialidad_nueva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(frame_especialidad_nueva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new frame_especialidad_nueva().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAtrasNuevaEspecialidad;
    private javax.swing.JButton jButtonGuardarEspecialidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTEspecialidad;
    private javax.swing.JTextField jTPlusSalario;
    // End of variables declaration//GEN-END:variables
}
