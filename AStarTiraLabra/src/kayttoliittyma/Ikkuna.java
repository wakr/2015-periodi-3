/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import java.awt.Color;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import logiikka.AStar;
import logiikka.Analysoija;
import util.Kuva;

/**
 *
 * @author kristianw
 */
public class Ikkuna extends javax.swing.JFrame {

    private Kuva karttaKuvana;
    private AStar aStar;

    public Ikkuna() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelKuva = new javax.swing.JLabel();
        jYlaMenu = new javax.swing.JMenuBar();
        jTiedostoMenu = new javax.swing.JMenu();
        jMenuAvaa = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuPoistu = new javax.swing.JMenuItem();
        jAsetusMenu = new javax.swing.JMenu();
        jMenuAStar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTiedostoMenu.setText("Tiedosto");

        jMenuAvaa.setText("Avaa");
        jMenuAvaa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuAvaaActionPerformed(evt);
            }
        });
        jTiedostoMenu.add(jMenuAvaa);
        jTiedostoMenu.add(jSeparator2);

        jMenuPoistu.setText("Poistu");
        jMenuPoistu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuPoistuActionPerformed(evt);
            }
        });
        jTiedostoMenu.add(jMenuPoistu);

        jYlaMenu.add(jTiedostoMenu);

        jAsetusMenu.setText("Asetukset");

        jMenuAStar.setText("A*");
        jMenuAStar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuAStarActionPerformed(evt);
            }
        });
        jAsetusMenu.add(jMenuAStar);

        jYlaMenu.add(jAsetusMenu);

        setJMenuBar(jYlaMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelKuva, javax.swing.GroupLayout.DEFAULT_SIZE, 888, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelKuva, javax.swing.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuAvaaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAvaaActionPerformed
        JFileChooser kuvanAvaus = new JFileChooser(System.getProperty("user.dir"));
        BufferedImage kuva;
        if (kuvanAvaus.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                kuva = ImageIO.read(kuvanAvaus.getSelectedFile());
                karttaKuvana = new Kuva(kuva, jLabelKuva.getHeight(), jLabelKuva.getWidth());
                jLabelKuva.setIcon(new ImageIcon(karttaKuvana.getKuva()));
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }

        karttaKuvana.convertTo2DWithoutUsingGetRGB(karttaKuvana.getBufferoituKuva());
    }//GEN-LAST:event_jMenuAvaaActionPerformed

    private void jMenuPoistuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuPoistuActionPerformed
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_jMenuPoistuActionPerformed

    private void jMenuAStarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAStarActionPerformed
        aStar = new AStar(karttaKuvana.getRGPArvot());
        aStar.suoritaReitinHaku();
        ArrayList<Integer> saatuPolku = aStar.polku(aStar.getMaali(), new ArrayList<Integer>());

        for (Integer kayty : aStar.getAnalysoidut()) {
            int x = Analysoija.getSarake(kayty, karttaKuvana.getLeveys());
            int y = Analysoija.getRivi(kayty, karttaKuvana.getLeveys());

            karttaKuvana.getBufferoituKuva().setRGB(x, y, Color.MAGENTA.getRGB());
        }

        for (Integer saatuPolku1 : saatuPolku) {
            int x = Analysoija.getSarake(saatuPolku1, karttaKuvana.getLeveys());
            int y = Analysoija.getRivi(saatuPolku1, karttaKuvana.getLeveys());

            karttaKuvana.getBufferoituKuva().setRGB(x, y, Color.ORANGE.getRGB());
        }

        karttaKuvana = new Kuva(karttaKuvana.getBufferoituKuva(), jLabelKuva.getHeight(), jLabelKuva.getWidth());
        jLabelKuva.setIcon(new ImageIcon(karttaKuvana.getKuva()));

    }//GEN-LAST:event_jMenuAStarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ikkuna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ikkuna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ikkuna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ikkuna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ikkuna().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jAsetusMenu;
    private javax.swing.JLabel jLabelKuva;
    private javax.swing.JMenuItem jMenuAStar;
    private javax.swing.JMenuItem jMenuAvaa;
    private javax.swing.JMenuItem jMenuPoistu;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JMenu jTiedostoMenu;
    private javax.swing.JMenuBar jYlaMenu;
    // End of variables declaration//GEN-END:variables
}
