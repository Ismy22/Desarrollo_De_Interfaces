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
public class frame_especie_nueva extends javax.swing.JFrame {

    private int x;
    private int y;
    /**
     * Creates new form frame_especie_nueva
     */
    public frame_especie_nueva() {
        initComponents();
        TextPrompt placeholcer = new TextPrompt("Megalodón", jTEspecie);
    }
    
    public void insertEspecie() {

        String nombre = jTEspecie.getText();
        int peligrosidad = Integer.parseInt(jCbPeligroEspecie.getSelectedItem().toString());

        DBManagerZoo.insertEspecie(nombre, peligrosidad);

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
        jTEspecie = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButtonGuardarEspecie = new javax.swing.JButton();
        jCbPeligroEspecie = new javax.swing.JComboBox<>();
        jButtonAltaNuevaEspecie = new javax.swing.JButton();

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
        jLabel1.setText("Nueva   Especie");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Especie");

        jTEspecie.setBackground(new java.awt.Color(255, 255, 255));
        jTEspecie.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jTEspecie.setForeground(new java.awt.Color(0, 0, 0));
        jTEspecie.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Peligrosidad");

        jButtonGuardarEspecie.setBackground(new java.awt.Color(103, 0, 3));
        jButtonGuardarEspecie.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jButtonGuardarEspecie.setForeground(new java.awt.Color(255, 255, 255));
        jButtonGuardarEspecie.setText("Guardar");
        jButtonGuardarEspecie.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonGuardarEspecieMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonGuardarEspecieMouseExited(evt);
            }
        });
        jButtonGuardarEspecie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarEspecieActionPerformed(evt);
            }
        });

        jCbPeligroEspecie.setBackground(new java.awt.Color(255, 255, 255));
        jCbPeligroEspecie.setForeground(new java.awt.Color(0, 0, 0));
        jCbPeligroEspecie.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5" }));
        jCbPeligroEspecie.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jButtonAltaNuevaEspecie.setBackground(new java.awt.Color(103, 0, 3));
        jButtonAltaNuevaEspecie.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Login/Imagen/hacia-atras (1).png"))); // NOI18N
        jButtonAltaNuevaEspecie.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonAltaNuevaEspecieMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonAltaNuevaEspecieMouseExited(evt);
            }
        });
        jButtonAltaNuevaEspecie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAltaNuevaEspecieActionPerformed(evt);
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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTEspecie)
                            .addComponent(jCbPeligroEspecie, 0, 187, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addComponent(jButtonGuardarEspecie))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButtonAltaNuevaEspecie)))
                .addContainerGap(9, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAltaNuevaEspecie)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jTEspecie, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jCbPeligroEspecie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(jButtonGuardarEspecie)
                .addContainerGap(19, Short.MAX_VALUE))
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

    private void jButtonGuardarEspecieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarEspecieActionPerformed
        
        if (jTEspecie.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "no puede dejar el campo especie vacío");
        } else {
            insertEspecie();
            dispose();
        }
    }//GEN-LAST:event_jButtonGuardarEspecieActionPerformed

    private void jButtonAltaNuevaEspecieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAltaNuevaEspecieActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonAltaNuevaEspecieActionPerformed

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        setLocation(point.x - x, point.y - y);
    }//GEN-LAST:event_formMouseDragged

    private void jButtonAltaNuevaEspecieMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAltaNuevaEspecieMouseEntered
        jButtonAltaNuevaEspecie.setCursor(new Cursor(HAND_CURSOR));
        jButtonAltaNuevaEspecie.setBackground( new Color(217,165,9));
        jButtonAltaNuevaEspecie.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonAltaNuevaEspecieMouseEntered

    private void jButtonAltaNuevaEspecieMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAltaNuevaEspecieMouseExited
        jButtonAltaNuevaEspecie.setBackground( new Color(103,0,3));
        jButtonAltaNuevaEspecie.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonAltaNuevaEspecieMouseExited

    private void jButtonGuardarEspecieMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonGuardarEspecieMouseEntered
        jButtonGuardarEspecie.setCursor(new Cursor(HAND_CURSOR));
        jButtonGuardarEspecie.setBackground( new Color(217,165,9));
        jButtonGuardarEspecie.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonGuardarEspecieMouseEntered

    private void jButtonGuardarEspecieMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonGuardarEspecieMouseExited
        jButtonGuardarEspecie.setBackground( new Color(103,0,3));
        jButtonGuardarEspecie.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonGuardarEspecieMouseExited

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
//            java.util.logging.Logger.getLogger(frame_especie_nueva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(frame_especie_nueva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(frame_especie_nueva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(frame_especie_nueva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new frame_especie_nueva().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAltaNuevaEspecie;
    private javax.swing.JButton jButtonGuardarEspecie;
    private javax.swing.JComboBox<String> jCbPeligroEspecie;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTEspecie;
    // End of variables declaration//GEN-END:variables
}
