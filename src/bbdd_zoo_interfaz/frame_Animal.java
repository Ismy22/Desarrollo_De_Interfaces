/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package bbdd_Zoo_Interfaz;

import Login.LoginUser;
import bbdd_Zoo_Interfaz.*;
import Utilidades.TextPrompt;
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
public class frame_Animal extends javax.swing.JFrame {

    private int x;
    private int y;

    /**
     * Creates new form frame_Animal
     */
    public frame_Animal() {
        initComponents();
        UIManager UI = new UIManager();
        UI.put("nimbusBlueGrey", new ColorUIResource(103, 0, 4));
        jTableAnimales.getTableHeader().setForeground(Color.white);
        //jTableAnimales.setBackground(Color.getHSBColor(255, 255, 255));

        listEspecie();
//        String nombreSonido = "C:\\Users\\Ismael\\Documents\\NetBeansProjects\\BBDD_Zoo_Interfaz\\src\\Login\\Imagen\\jPark.wav";
//        Login.LoginUser open = new LoginUser();
//        open.ReproducirSonido(nombreSonido);

        TextPrompt placeholcer = new TextPrompt("Tobby", jtNombreAltaAnimal);
        TextPrompt placeholcer2 = new TextPrompt("1800", jtPeso);
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

    // Configuración de la tabla Especie
    private static final String DB_ESPECIE = "ESPECIE";
    private static final String DB_ESPECIE_SELECT = "SELECT * FROM " + DB_ESPECIE;
    private static final String DB_ESPECIE_ID_ESPECIE = "ID_ESPECIE";
    private static final String DB_ESPECIE_NOMBRE_ESPECIE = "NOMBRE_ESPECIE";
    private static final String DB_ESPECIE_PELIGROSIDAD = "PELIGROSIDAD";

    //////////////////////////////////////////////////
    // MÉTODOS PARA ANIMALS
    //////////////////////////////////////////////////
    public void vaciarFrameAddAnimals() {

        jtNombreAltaAnimal.setText("");
        jtPeso.setText("");

    }

    //método para poner los nombres de los animales en un jComboBox
    public void listEspecie() {

        try {
            ResultSet rs = DBManagerZoo.getTablaEspecie(DEFAULT_CURSOR, DISPOSE_ON_CLOSE);
            while (rs.next()) {

                String nombre = rs.getString(DB_ESPECIE_NOMBRE_ESPECIE);
                jCbEspecie.addItem(nombre);
                jCbEspecieEditarAnimal.addItem(nombre);
                jCEspecies.addItem(nombre);

            }
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static boolean updateAnimal(int id, String nuevoNombre, String nuevaEspecie, Float nuevoPeso) {
        try {
            // Obtenemos el cliente

            ResultSet rs = DBManagerZoo.getAnimal(id);

            // Si no existe el Resultset
            if (rs == null) {
                System.out.println("Error. ResultSet null.");
                return false;
            }

            // Si tiene un primer registro, lo modificamos
            if (rs.first()) {
                rs.updateString(DB_ANIMALS_NOM, nuevoNombre);
                rs.updateString(DB_ANIMALS_ESPE, nuevaEspecie);
                rs.updateFloat(DB_ANIMALS_PESO, nuevoPeso);

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

    public void vaciarTablaAnimales() {

        //vaciamos la tabla recorriendola con un bucle for
        DefaultTableModel tb = (DefaultTableModel) jTableAnimales.getModel();
        int a = jTableAnimales.getRowCount() - 1; //contamos las filas
        //bucle para borrar todas las filas
        for (int i = a; i >= 0; i--) {
            tb.removeRow(tb.getRowCount() - 1);
        }

    }

    public void rellenarTablaAnimals() {

        try {
            ResultSet rs = DBManagerZoo.getTablaAnimals(DEFAULT_CURSOR, DISPOSE_ON_CLOSE);
            while (rs.next()) {
                int id = rs.getInt(DB_ANIMALS_ID);
                String nombre = rs.getString(DB_ANIMALS_NOM);
                String especie = rs.getString(DB_ANIMALS_ESPE);
                int peso = rs.getInt(DB_ANIMALS_PESO);

                DefaultTableModel model = (DefaultTableModel) jTableAnimales.getModel();
                Object[] row = {id, nombre, especie, peso};
                model.addRow(row);

            }
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void rellenarTablaAnimalsEspecies() {
        String especie2 = jCEspecies.getSelectedItem().toString();
        try {
            ResultSet rs = DBManagerZoo.getTablaAnimalsEspecies(DEFAULT_CURSOR, DISPOSE_ON_CLOSE, especie2);
            while (rs.next()) {
                int id = rs.getInt(DB_ANIMALS_ID);
                String nombre = rs.getString(DB_ANIMALS_NOM);
                String especie = rs.getString(DB_ANIMALS_ESPE);
                float peso = rs.getFloat(DB_ANIMALS_PESO);

                DefaultTableModel model = (DefaultTableModel) jTableAnimales.getModel();
                Object[] row = {id, nombre, especie, peso};
                model.addRow(row);

            }
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void insertAnimals() {

        int id = 0;
        String nombre = jtNombreAltaAnimal.getText();
        String especie = jCbEspecie.getSelectedItem().toString();
        float peso = Float.parseFloat(jtPeso.getText());

        DBManagerZoo.insertAnimal(id, nombre, especie, peso);

    }

//    public static void deleteTransaccion(String sql) {
//        
//        
//        DBManagerZoo.transaccion(sql);
//        
////        try {
////            // Obtenemos el cliente
////            ResultSet rs = DBManagerZoo.getAnimal(id);
////
////            // Si no existe el Resultset
////            if (rs == null) {
////                System.out.println("ERROR. ResultSet null.");
////                return false;
////            }
////
////            // Si existe y tiene primer registro, lo eliminamos
////            if (rs.first()) {
////                rs.deleteRow();
////                rs.close();
////                return true;
////            } else {
////                JOptionPane.showMessageDialog(null, "ERROR AL BORRAR EL ANIMAL");
////                return false;
////            }
////
////        } catch (SQLException ex) {
////            ex.printStackTrace();
////            return false;
////        }
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrameAnimales = new javax.swing.JFrame();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableAnimales = new javax.swing.JTable();
        jBModificarAnimal = new javax.swing.JButton();
        jBBorrarAnimal = new javax.swing.JButton();
        jButtonAtrasListarAnimales = new javax.swing.JButton();
        jButtonMostrarEspecies = new javax.swing.JButton();
        jCEspecies = new javax.swing.JComboBox<>();
        jFrameEditarAnimal = new javax.swing.JFrame();
        capa1 = new javax.swing.JPanel();
        capa_Titulo1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jButtonAtrasEditarAnimal = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jtNombreEditarAnimal = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jtPesoEditarAnimal = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jbEditarAnimal = new javax.swing.JButton();
        jCbEspecieEditarAnimal = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jtIdEditarAnimal = new javax.swing.JTextField();
        jCalendarBeanInfo1 = new com.toedter.calendar.JCalendarBeanInfo();
        capa0 = new javax.swing.JPanel();
        capa_Titulo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButtonAtrasAltaAnimal = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jtNombreAltaAnimal = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jtPeso = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jbAltaAnimal = new javax.swing.JButton();
        jbListarAnimales = new javax.swing.JButton();
        jCbEspecie = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();

        jFrameAnimales.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jFrameAnimales.setLocation(new java.awt.Point(300, 300));
        jFrameAnimales.setMinimumSize(new java.awt.Dimension(537, 418));
        jFrameAnimales.setUndecorated(true);
        jFrameAnimales.setResizable(false);
        jFrameAnimales.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jFrameAnimalesMouseDragged(evt);
            }
        });
        jFrameAnimales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jFrameAnimalesMousePressed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel6.setFont(new java.awt.Font("Jurassic Park", 1, 48)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Animales");

        jTableAnimales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Especie", "Peso"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableAnimales);

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

        jBBorrarAnimal.setBackground(new java.awt.Color(103, 0, 3));
        jBBorrarAnimal.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jBBorrarAnimal.setForeground(new java.awt.Color(255, 255, 255));
        jBBorrarAnimal.setText("Borrar");
        jBBorrarAnimal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBBorrarAnimalMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBBorrarAnimalMouseExited(evt);
            }
        });
        jBBorrarAnimal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBorrarAnimalActionPerformed(evt);
            }
        });

        jButtonAtrasListarAnimales.setBackground(new java.awt.Color(103, 0, 3));
        jButtonAtrasListarAnimales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Login/Imagen/hacia-atras (1).png"))); // NOI18N
        jButtonAtrasListarAnimales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonAtrasListarAnimalesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonAtrasListarAnimalesMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonAtrasListarAnimalesMouseReleased(evt);
            }
        });
        jButtonAtrasListarAnimales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAtrasListarAnimalesActionPerformed(evt);
            }
        });

        jButtonMostrarEspecies.setBackground(new java.awt.Color(103, 0, 3));
        jButtonMostrarEspecies.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButtonMostrarEspecies.setForeground(new java.awt.Color(255, 255, 255));
        jButtonMostrarEspecies.setText("Mostrar");
        jButtonMostrarEspecies.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonMostrarEspeciesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonMostrarEspeciesMouseExited(evt);
            }
        });
        jButtonMostrarEspecies.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMostrarEspeciesActionPerformed(evt);
            }
        });

        jCEspecies.setBackground(new java.awt.Color(255, 255, 255));
        jCEspecies.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jCEspecies.setForeground(new java.awt.Color(0, 0, 0));
        jCEspecies.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonMostrarEspecies)
                        .addGap(31, 31, 31)
                        .addComponent(jCEspecies, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(150, 150, 150)
                                .addComponent(jBModificarAnimal)
                                .addGap(18, 18, 18)
                                .addComponent(jBBorrarAnimal))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButtonAtrasListarAnimales)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBBorrarAnimal, jBModificarAnimal});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAtrasListarAnimales)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBModificarAnimal)
                    .addComponent(jBBorrarAnimal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonMostrarEspecies)
                    .addComponent(jCEspecies, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jFrameAnimalesLayout = new javax.swing.GroupLayout(jFrameAnimales.getContentPane());
        jFrameAnimales.getContentPane().setLayout(jFrameAnimalesLayout);
        jFrameAnimalesLayout.setHorizontalGroup(
            jFrameAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jFrameAnimalesLayout.setVerticalGroup(
            jFrameAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jFrameEditarAnimal.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jFrameEditarAnimal.setLocation(new java.awt.Point(300, 300));
        jFrameEditarAnimal.setMinimumSize(new java.awt.Dimension(537, 416));
        jFrameEditarAnimal.setUndecorated(true);
        jFrameEditarAnimal.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jFrameEditarAnimalMouseDragged(evt);
            }
        });
        jFrameEditarAnimal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jFrameEditarAnimalMousePressed(evt);
            }
        });

        capa1.setBackground(new java.awt.Color(255, 255, 255));
        capa1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        capa_Titulo1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Jurassic Park", 1, 48)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Editar Animal");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jButtonAtrasEditarAnimal.setBackground(new java.awt.Color(103, 0, 3));
        jButtonAtrasEditarAnimal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Login/Imagen/hacia-atras (1).png"))); // NOI18N
        jButtonAtrasEditarAnimal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonAtrasEditarAnimalMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonAtrasEditarAnimalMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonAtrasEditarAnimalMouseReleased(evt);
            }
        });
        jButtonAtrasEditarAnimal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAtrasEditarAnimalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout capa_Titulo1Layout = new javax.swing.GroupLayout(capa_Titulo1);
        capa_Titulo1.setLayout(capa_Titulo1Layout);
        capa_Titulo1Layout.setHorizontalGroup(
            capa_Titulo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(capa_Titulo1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(capa_Titulo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                    .addGroup(capa_Titulo1Layout.createSequentialGroup()
                        .addComponent(jButtonAtrasEditarAnimal)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        capa_Titulo1Layout.setVerticalGroup(
            capa_Titulo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, capa_Titulo1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAtrasEditarAnimal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addContainerGap())
        );

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Nombre");

        jtNombreEditarAnimal.setBackground(new java.awt.Color(255, 255, 255));
        jtNombreEditarAnimal.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jtNombreEditarAnimal.setForeground(new java.awt.Color(0, 0, 0));
        jtNombreEditarAnimal.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Especie");

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Peso Kg");

        jtPesoEditarAnimal.setBackground(new java.awt.Color(255, 255, 255));
        jtPesoEditarAnimal.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jtPesoEditarAnimal.setForeground(new java.awt.Color(0, 0, 0));
        jtPesoEditarAnimal.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jbEditarAnimal.setBackground(new java.awt.Color(113, 0, 3));
        jbEditarAnimal.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jbEditarAnimal.setForeground(new java.awt.Color(255, 255, 255));
        jbEditarAnimal.setText("Guardar Cambios");
        jbEditarAnimal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbEditarAnimalMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbEditarAnimalMouseExited(evt);
            }
        });
        jbEditarAnimal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEditarAnimal(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addComponent(jbEditarAnimal)
                .addContainerGap(140, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbEditarAnimal)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jCbEspecieEditarAnimal.setBackground(new java.awt.Color(255, 255, 255));
        jCbEspecieEditarAnimal.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jCbEspecieEditarAnimal.setForeground(new java.awt.Color(0, 0, 0));
        jCbEspecieEditarAnimal.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(0, 0, 0)));

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Id");

        jtIdEditarAnimal.setEditable(false);
        jtIdEditarAnimal.setBackground(new java.awt.Color(255, 255, 255));
        jtIdEditarAnimal.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jtIdEditarAnimal.setForeground(new java.awt.Color(0, 0, 0));
        jtIdEditarAnimal.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jtIdEditarAnimal.setDisabledTextColor(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout capa1Layout = new javax.swing.GroupLayout(capa1);
        capa1.setLayout(capa1Layout);
        capa1Layout.setHorizontalGroup(
            capa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(capa_Titulo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(capa1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(capa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel11)
                    .addComponent(jLabel9)
                    .addComponent(jLabel12))
                .addGap(35, 35, 35)
                .addGroup(capa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(capa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jtIdEditarAnimal, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtNombreEditarAnimal, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCbEspecieEditarAnimal, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(capa1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jtPesoEditarAnimal, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        capa1Layout.setVerticalGroup(
            capa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(capa1Layout.createSequentialGroup()
                .addComponent(capa_Titulo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(capa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jtIdEditarAnimal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(capa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jtNombreEditarAnimal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(capa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jCbEspecieEditarAnimal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(capa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jtPesoEditarAnimal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout jFrameEditarAnimalLayout = new javax.swing.GroupLayout(jFrameEditarAnimal.getContentPane());
        jFrameEditarAnimal.getContentPane().setLayout(jFrameEditarAnimalLayout);
        jFrameEditarAnimalLayout.setHorizontalGroup(
            jFrameEditarAnimalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(capa1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jFrameEditarAnimalLayout.setVerticalGroup(
            jFrameEditarAnimalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(capa1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

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

        jLabel1.setFont(new java.awt.Font("Jurassic Park", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Alta   Animal");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jButtonAtrasAltaAnimal.setBackground(new java.awt.Color(103, 0, 3));
        jButtonAtrasAltaAnimal.setForeground(new java.awt.Color(0, 0, 0));
        jButtonAtrasAltaAnimal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Login/Imagen/hacia-atras (1).png"))); // NOI18N
        jButtonAtrasAltaAnimal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonAtrasAltaAnimalMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonAtrasAltaAnimalMouseExited(evt);
            }
        });
        jButtonAtrasAltaAnimal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAtrasAltaAnimalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout capa_TituloLayout = new javax.swing.GroupLayout(capa_Titulo);
        capa_Titulo.setLayout(capa_TituloLayout);
        capa_TituloLayout.setHorizontalGroup(
            capa_TituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
            .addGroup(capa_TituloLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAtrasAltaAnimal)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        capa_TituloLayout.setVerticalGroup(
            capa_TituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(capa_TituloLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAtrasAltaAnimal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Nombre");

        jtNombreAltaAnimal.setBackground(new java.awt.Color(255, 255, 255));
        jtNombreAltaAnimal.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jtNombreAltaAnimal.setForeground(new java.awt.Color(0, 0, 0));
        jtNombreAltaAnimal.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Especie");

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Peso Kg");

        jtPeso.setBackground(new java.awt.Color(255, 255, 255));
        jtPeso.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jtPeso.setForeground(new java.awt.Color(0, 0, 0));
        jtPeso.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jbAltaAnimal.setBackground(new java.awt.Color(113, 0, 3));
        jbAltaAnimal.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jbAltaAnimal.setForeground(new java.awt.Color(255, 255, 255));
        jbAltaAnimal.setText("Alta Animal");
        jbAltaAnimal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbAltaAnimalMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbAltaAnimalMouseExited(evt);
            }
        });
        jbAltaAnimal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAltaAnimal(evt);
            }
        });

        jbListarAnimales.setBackground(new java.awt.Color(113, 0, 3));
        jbListarAnimales.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jbListarAnimales.setForeground(new java.awt.Color(255, 255, 255));
        jbListarAnimales.setText("Lista Animales");
        jbListarAnimales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbListarAnimalesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbListarAnimalesMouseExited(evt);
            }
        });
        jbListarAnimales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbListarAnimalesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jbAltaAnimal, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jbListarAnimales)
                .addGap(80, 80, 80))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(59, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbAltaAnimal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbListarAnimales, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jCbEspecie.setBackground(new java.awt.Color(255, 255, 255));
        jCbEspecie.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jCbEspecie.setForeground(new java.awt.Color(0, 0, 0));
        jCbEspecie.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(0, 0, 0)));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Login/Imagen/animal2.jpeg"))); // NOI18N
        jLabel13.setText("jLabel13");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel13)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout capa0Layout = new javax.swing.GroupLayout(capa0);
        capa0.setLayout(capa0Layout);
        capa0Layout.setHorizontalGroup(
            capa0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(capa0Layout.createSequentialGroup()
                .addGroup(capa0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(capa0Layout.createSequentialGroup()
                        .addGroup(capa0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(capa_Titulo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, capa0Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(capa0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(capa0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtNombreAltaAnimal, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                            .addComponent(jCbEspecie, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jtPeso))
                        .addGap(61, 61, 61)))
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        capa0Layout.setVerticalGroup(
            capa0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(capa0Layout.createSequentialGroup()
                .addComponent(capa_Titulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addGroup(capa0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(capa0Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(capa0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jCbEspecie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(capa0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jtPeso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(capa0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtNombreAltaAnimal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(capa0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(capa0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbAltaAnimal(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAltaAnimal
        if (Utilidades.Utilidades_Control.contieneSoloLetras(jtNombreAltaAnimal.getText()) == false) {
            JOptionPane.showMessageDialog(null, "Solo puede introducir letras");
            jtNombreAltaAnimal.setText("");
        }
        boolean comprobar = Utilidades.Utilidades_Control.contieneSoloNumeros(jtPeso.getText());
        if (!comprobar) {
            JOptionPane.showMessageDialog(null, "Solo puede introducir números");
            jtPeso.setText("");
        } else {
            if (jtNombreAltaAnimal.getText().equals("") || jtPeso.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "No puede dejar ningún campo vacío");
            } else {
                insertAnimals();
                JOptionPane.showMessageDialog(null, "Animal guardado correctamente");
                this.dispose();
            }
        }
    }//GEN-LAST:event_jbAltaAnimal

    private void jbListarAnimalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbListarAnimalesActionPerformed
        jFrameAnimales.setVisible(true);
        vaciarTablaAnimales();
        rellenarTablaAnimals();
    }//GEN-LAST:event_jbListarAnimalesActionPerformed

    private void jBModificarAnimalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBModificarAnimalActionPerformed

        int filaseleccionada;

        //Guardamos en un entero la fila seleccionada.
        filaseleccionada = jTableAnimales.getSelectedRow();

        if (filaseleccionada == -1) {
            JOptionPane.showMessageDialog(null, "No ha seleccionado ninguna fila.");
        } else {

            jFrameEditarAnimal.setVisible(true);

            //String ayuda = tabla.getValueAt(filaseleccionada, num_columna).toString());
            int id = (int) jTableAnimales.getValueAt(filaseleccionada, 0);
            String nombre = (String) jTableAnimales.getValueAt(filaseleccionada, 1);
            String especie = (String) jTableAnimales.getValueAt(filaseleccionada, 2);
            String peso = jTableAnimales.getValueAt(filaseleccionada, 3).toString();

            jtIdEditarAnimal.setText(Integer.toString(id));
            jtNombreEditarAnimal.setText(nombre);
            listEspecie();
            jCbEspecieEditarAnimal.setSelectedItem(especie);
            jtPesoEditarAnimal.setText(peso);

        }
    }//GEN-LAST:event_jBModificarAnimalActionPerformed

    private void jbEditarAnimal(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEditarAnimal

        if (Utilidades.Utilidades_Control.contieneSoloLetras(jtNombreEditarAnimal.getText()) == false) {
            JOptionPane.showMessageDialog(null, "solo puede introducir letras");
            jtNombreAltaAnimal.setText("");
        }
        String peso1 = jtPeso.getText();
        System.out.println(peso1);
        if (Utilidades.Utilidades_Control.contieneSoloNumeros(jtPesoEditarAnimal.getText()) == false) {
            JOptionPane.showMessageDialog(null, "solo puede introducir números");
            jtPeso.setText("");
        } else {
            if (jtNombreEditarAnimal.getText().equals("") || jtPesoEditarAnimal.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "no puede dejar ningún campo vacío");
            } else {
                int id = Integer.parseInt(jtIdEditarAnimal.getText());
                String nuevoNombre = jtNombreEditarAnimal.getText();
                String nuevaEspecie = jCbEspecieEditarAnimal.getSelectedItem().toString();
                //System.out.println(nuevaEspecie);
                Float nuevoPeso = Float.parseFloat(jtPesoEditarAnimal.getText());

                updateAnimal(id, nuevoNombre, nuevaEspecie, nuevoPeso);
                vaciarTablaAnimales();
                rellenarTablaAnimals();
                jFrameEditarAnimal.dispose();
            }
        }
    }//GEN-LAST:event_jbEditarAnimal

    private void jBBorrarAnimalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBorrarAnimalActionPerformed

        

        /////////////////////////////////////////////////////////////////  
        int filaseleccionada = jTableAnimales.getSelectedRow();

        if (filaseleccionada == -1) {
            JOptionPane.showMessageDialog(null, "No ha seleccionado ninguna fila.");
        } else {
            int id = (int) jTableAnimales.getValueAt(filaseleccionada, 0);
            String nombre_animal = (String) jTableAnimales.getValueAt(filaseleccionada, 1);
            int confirmado = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar a: " + nombre_animal + " ¡Y TODAS SUS TAREAS!?");

            if (JOptionPane.OK_OPTION == confirmado) {
                String sql = "DELETE FROM animals WHERE ID_ANIMAL = '"+id+"'";
                DBManagerZoo.transaccion(sql);
                //deleteTransaccion(sql);
                vaciarTablaAnimales();
                rellenarTablaAnimals();
                JOptionPane.showMessageDialog(null, "eliminado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "vale... no borro nada...");
            }
        }

    }//GEN-LAST:event_jBBorrarAnimalActionPerformed

    private void jButtonAtrasAltaAnimalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtrasAltaAnimalActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonAtrasAltaAnimalActionPerformed

    private void jButtonAtrasListarAnimalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtrasListarAnimalesActionPerformed
        jFrameAnimales.dispose();
    }//GEN-LAST:event_jButtonAtrasListarAnimalesActionPerformed

    private void jButtonAtrasEditarAnimalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtrasEditarAnimalActionPerformed
        jFrameEditarAnimal.dispose();
    }//GEN-LAST:event_jButtonAtrasEditarAnimalActionPerformed

    private void jFrameAnimalesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFrameAnimalesMousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jFrameAnimalesMousePressed

    private void jFrameEditarAnimalMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFrameEditarAnimalMousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jFrameEditarAnimalMousePressed

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void jFrameAnimalesMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFrameAnimalesMouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        setLocation(point.x - x, point.y - y);
    }//GEN-LAST:event_jFrameAnimalesMouseDragged

    private void jFrameEditarAnimalMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFrameEditarAnimalMouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        setLocation(point.x - x, point.y - y);
    }//GEN-LAST:event_jFrameEditarAnimalMouseDragged

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        setLocation(point.x - x, point.y - y);
    }//GEN-LAST:event_formMouseDragged

    private void jButtonAtrasAltaAnimalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasAltaAnimalMouseEntered
        jButtonAtrasAltaAnimal.setCursor(new Cursor(HAND_CURSOR));
        jButtonAtrasAltaAnimal.setBackground(new Color(217, 165, 9));
        jButtonAtrasAltaAnimal.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonAtrasAltaAnimalMouseEntered

    private void jButtonAtrasAltaAnimalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasAltaAnimalMouseExited
        jButtonAtrasAltaAnimal.setBackground(new Color(103, 0, 3));
        jButtonAtrasAltaAnimal.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonAtrasAltaAnimalMouseExited

    private void jbAltaAnimalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbAltaAnimalMouseEntered
        jbAltaAnimal.setCursor(new Cursor(HAND_CURSOR));
        jbAltaAnimal.setBackground(new Color(217, 165, 9));
        jbAltaAnimal.setForeground(Color.BLACK);
    }//GEN-LAST:event_jbAltaAnimalMouseEntered

    private void jbAltaAnimalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbAltaAnimalMouseExited
        jbAltaAnimal.setBackground(new Color(103, 0, 3));
        jbAltaAnimal.setForeground(Color.WHITE);
    }//GEN-LAST:event_jbAltaAnimalMouseExited

    private void jbListarAnimalesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbListarAnimalesMouseEntered
        jbListarAnimales.setCursor(new Cursor(HAND_CURSOR));
        jbListarAnimales.setBackground(new Color(217, 165, 9));
        jbListarAnimales.setForeground(Color.BLACK);
    }//GEN-LAST:event_jbListarAnimalesMouseEntered

    private void jbListarAnimalesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbListarAnimalesMouseExited
        jbListarAnimales.setBackground(new Color(103, 0, 3));
        jbListarAnimales.setForeground(Color.WHITE);
    }//GEN-LAST:event_jbListarAnimalesMouseExited

    private void jButtonAtrasListarAnimalesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasListarAnimalesMouseEntered
        jButtonAtrasListarAnimales.setCursor(new Cursor(HAND_CURSOR));
        jButtonAtrasListarAnimales.setBackground(new Color(217, 165, 9));
        jButtonAtrasListarAnimales.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonAtrasListarAnimalesMouseEntered

    private void jButtonAtrasListarAnimalesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasListarAnimalesMouseExited
        jButtonAtrasListarAnimales.setBackground(new Color(103, 0, 3));
        jButtonAtrasListarAnimales.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonAtrasListarAnimalesMouseExited

    private void jBModificarAnimalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBModificarAnimalMouseEntered
        jBModificarAnimal.setCursor(new Cursor(HAND_CURSOR));
        jBModificarAnimal.setBackground(new Color(217, 165, 9));
        jBModificarAnimal.setForeground(Color.BLACK);
    }//GEN-LAST:event_jBModificarAnimalMouseEntered

    private void jBModificarAnimalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBModificarAnimalMouseExited
        jBModificarAnimal.setBackground(new Color(103, 0, 3));
        jBModificarAnimal.setForeground(Color.WHITE);
    }//GEN-LAST:event_jBModificarAnimalMouseExited

    private void jBBorrarAnimalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBorrarAnimalMouseEntered
        jBBorrarAnimal.setCursor(new Cursor(HAND_CURSOR));
        jBBorrarAnimal.setBackground(new Color(217, 165, 9));
        jBBorrarAnimal.setForeground(Color.BLACK);
    }//GEN-LAST:event_jBBorrarAnimalMouseEntered

    private void jBBorrarAnimalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBorrarAnimalMouseExited
        jBBorrarAnimal.setBackground(new Color(103, 0, 3));
        jBBorrarAnimal.setForeground(Color.WHITE);
    }//GEN-LAST:event_jBBorrarAnimalMouseExited

    private void jButtonAtrasEditarAnimalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasEditarAnimalMouseEntered
        jButtonAtrasEditarAnimal.setCursor(new Cursor(HAND_CURSOR));
        jButtonAtrasEditarAnimal.setBackground(new Color(217, 165, 9));
        jButtonAtrasEditarAnimal.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonAtrasEditarAnimalMouseEntered

    private void jButtonAtrasEditarAnimalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasEditarAnimalMouseExited
        jButtonAtrasEditarAnimal.setBackground(new Color(103, 0, 3));
        jButtonAtrasEditarAnimal.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonAtrasEditarAnimalMouseExited

    private void jbEditarAnimalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbEditarAnimalMouseEntered
        jbEditarAnimal.setCursor(new Cursor(HAND_CURSOR));
        jbEditarAnimal.setBackground(new Color(217, 165, 9));
        jbEditarAnimal.setForeground(Color.BLACK);
    }//GEN-LAST:event_jbEditarAnimalMouseEntered

    private void jbEditarAnimalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbEditarAnimalMouseExited
        jbEditarAnimal.setBackground(new Color(103, 0, 3));
        jbEditarAnimal.setForeground(Color.WHITE);
    }//GEN-LAST:event_jbEditarAnimalMouseExited

    private void jButtonMostrarEspeciesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonMostrarEspeciesMouseEntered
        jButtonMostrarEspecies.setBackground(new Color(217, 165, 9));
        jButtonMostrarEspecies.setForeground(Color.BLACK);
        jButtonMostrarEspecies.setCursor(new Cursor(HAND_CURSOR));
    }//GEN-LAST:event_jButtonMostrarEspeciesMouseEntered

    private void jButtonMostrarEspeciesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonMostrarEspeciesMouseExited
        jButtonMostrarEspecies.setBackground(new Color(103, 0, 3));
        jButtonMostrarEspecies.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonMostrarEspeciesMouseExited

    private void jButtonMostrarEspeciesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMostrarEspeciesActionPerformed
        try {

            vaciarTablaAnimales();
            rellenarTablaAnimalsEspecies();

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "No puede dejar el campo fecha vacío");
        }

    }//GEN-LAST:event_jButtonMostrarEspeciesActionPerformed

    private void jButtonAtrasListarAnimalesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasListarAnimalesMouseReleased
        jButtonAtrasListarAnimales.setBackground(new Color(103, 0, 3));
        jButtonAtrasListarAnimales.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonAtrasListarAnimalesMouseReleased

    private void jButtonAtrasEditarAnimalMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAtrasEditarAnimalMouseReleased
        jButtonAtrasEditarAnimal.setBackground(new Color(103, 0, 3));
        jButtonAtrasEditarAnimal.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonAtrasEditarAnimalMouseReleased

//    /**
//     * @param args the command line arguments
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
//            java.util.logging.Logger.getLogger(frame_Animal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(frame_Animal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(frame_Animal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(frame_Animal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new frame_Animal().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel capa0;
    private javax.swing.JPanel capa1;
    private javax.swing.JPanel capa_Titulo;
    private javax.swing.JPanel capa_Titulo1;
    private javax.swing.JButton jBBorrarAnimal;
    private javax.swing.JButton jBModificarAnimal;
    private javax.swing.JButton jButtonAtrasAltaAnimal;
    private javax.swing.JButton jButtonAtrasEditarAnimal;
    private javax.swing.JButton jButtonAtrasListarAnimales;
    private javax.swing.JButton jButtonMostrarEspecies;
    private javax.swing.JComboBox<String> jCEspecies;
    private com.toedter.calendar.JCalendarBeanInfo jCalendarBeanInfo1;
    private javax.swing.JComboBox<String> jCbEspecie;
    private javax.swing.JComboBox<String> jCbEspecieEditarAnimal;
    private javax.swing.JFrame jFrameAnimales;
    private javax.swing.JFrame jFrameEditarAnimal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JTable jTableAnimales;
    private javax.swing.JButton jbAltaAnimal;
    private javax.swing.JButton jbEditarAnimal;
    private javax.swing.JButton jbListarAnimales;
    private javax.swing.JTextField jtIdEditarAnimal;
    private javax.swing.JTextField jtNombreAltaAnimal;
    private javax.swing.JTextField jtNombreEditarAnimal;
    private javax.swing.JTextField jtPeso;
    private javax.swing.JTextField jtPesoEditarAnimal;
    // End of variables declaration//GEN-END:variables
}
