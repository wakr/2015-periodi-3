/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
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
    private BufferedImage alkuPerainenKuva;
    private Queue<Integer> saatuPolku;

    public Ikkuna() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelPolkuMask = new javax.swing.JLabel();
        jLabelKuva = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jYlaMenu = new javax.swing.JMenuBar();
        jTiedostoMenu = new javax.swing.JMenu();
        jMenuAvaa = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuPoistu = new javax.swing.JMenuItem();
        jAsetusMenu = new javax.swing.JMenu();
        jMenuAStar = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuResetoiKuva = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(900, 900));
        setResizable(false);

        jLabelPolkuMask.setOpaque(true);
        jLabelPolkuMask.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelPolkuMaskMouseClicked(evt);
            }
        });

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
        jAsetusMenu.add(jSeparator3);

        jMenuResetoiKuva.setText("Resetoi");
        jMenuResetoiKuva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuResetoiKuvaActionPerformed(evt);
            }
        });
        jAsetusMenu.add(jMenuResetoiKuva);

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
            .addGroup(layout.createSequentialGroup()
                .addGap(358, 358, 358)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabelPolkuMask, javax.swing.GroupLayout.DEFAULT_SIZE, 888, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jLabelKuva, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(71, Short.MAX_VALUE)
                    .addComponent(jLabelPolkuMask, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuAvaaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAvaaActionPerformed
        JFileChooser kuvanAvaus = new JFileChooser(System.getProperty("user.dir"));
        BufferedImage kuva;
        if (kuvanAvaus.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                kuva = ImageIO.read(kuvanAvaus.getSelectedFile());
                alkuPerainenKuva = ImageIO.read(kuvanAvaus.getSelectedFile());
                karttaKuvana = new Kuva(kuva, jLabelKuva.getHeight(), jLabelKuva.getWidth());
                jLabelKuva.setIcon(new ImageIcon(karttaKuvana.getKuva()));

            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }


    }//GEN-LAST:event_jMenuAvaaActionPerformed

    private void jMenuPoistuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuPoistuActionPerformed
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_jMenuPoistuActionPerformed

    private void jMenuAStarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAStarActionPerformed

        karttaKuvana.convertTo2DWithoutUsingGetRGB(karttaKuvana.getBufferoituKuva());

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                ajaAStarAlgoritmi();

            }
        });
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                piirraPolkuKarttaan();

            }
        });
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                liikutaKohtiMaaliin();

            }
        });

        //karttaKuvana = new Kuva(karttaKuvana.getBufferoituKuva(), jLabelKuva.getHeight(), jLabelKuva.getWidth());
        //karttaKuvana.setKuva(karttaKuvana.getBufferoituKuva(), jLabelKuva.getHeight(), jLabelKuva.getWidth());
        //jLabelKuva.setIcon(new ImageIcon(karttaKuvana.getKuva()));

    }//GEN-LAST:event_jMenuAStarActionPerformed

    private void jLabelPolkuMaskMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelPolkuMaskMouseClicked
        if (alkuPerainenKuva != null) {
            int[] muutetutKoordinaatit = muutaKoordinaatitPitkasta(evt.getY(), evt.getX());
            karttaKuvana.getBufferoituKuva().setRGB(muutetutKoordinaatit[1], muutetutKoordinaatit[0], Color.BLACK.getRGB());
            karttaKuvana.setKuva(karttaKuvana.getBufferoituKuva(), jLabelKuva.getHeight(), jLabelKuva.getWidth());
            jLabelKuva.setIcon(new ImageIcon(karttaKuvana.getKuva()));
        }
        // resetoi A*
        aStar.resetoiAlgoritmi();
       // jMenuResetoiKuvaActionPerformed(new ActionEvent(evt, WIDTH, null));
    }//GEN-LAST:event_jLabelPolkuMaskMouseClicked

    private void jMenuResetoiKuvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuResetoiKuvaActionPerformed
//        BufferedImage copyOfImage
//                = new BufferedImage(alkuPerainenKuva.getWidth(), alkuPerainenKuva.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
//        Graphics g = copyOfImage.createGraphics();
//        g.drawImage(alkuPerainenKuva, 0, 0, null);
//
//        karttaKuvana.setKuva(copyOfImage, jLabelKuva.getHeight(), jLabelKuva.getWidth());
//        jLabelKuva.setIcon(new ImageIcon(karttaKuvana.getKuva()));
        jLabelPolkuMask.repaint();

    }//GEN-LAST:event_jMenuResetoiKuvaActionPerformed

    private void liikutaKohtiMaaliin() {

        Thread.yield();

        for (int saatuPolku1 : saatuPolku) {
            int x = Analysoija.getSarake(saatuPolku1, karttaKuvana.getLeveys());
            int y = Analysoija.getRivi(saatuPolku1, karttaKuvana.getLeveys());

            // karttaKuvana.getBufferoituKuva().setRGB(x, y, Color.ORANGE.getRGB());
            int[] muutetutKoordinaatit = muutaKoordinaatitLyhyesta(y, x);
            
            
        }
    }

    private int[] muutaKoordinaatitPitkasta(int y, int x) {

        double korkeusKerroin = (double) jLabelKuva.getHeight() / (double) alkuPerainenKuva.getHeight();
        double leveysKerroin = (double) jLabelKuva.getWidth() / (double) alkuPerainenKuva.getWidth();

        double offSetYD = y / korkeusKerroin;
        double offSetXD = x / leveysKerroin;

        int offSetY = (int) offSetYD;
        int offSetX = (int) offSetXD;

        return new int[]{offSetY, offSetX};
    }

    private int[] muutaKoordinaatitLyhyesta(int y, int x) {

        double korkeusKerroin = (double) jLabelKuva.getHeight() / (double) alkuPerainenKuva.getHeight();
        double leveysKerroin = (double) jLabelKuva.getWidth() / (double) alkuPerainenKuva.getWidth();

        double offSetYD = y * korkeusKerroin;
        double offSetXD = x * leveysKerroin;

        int offSetY = (int) offSetYD;
        int offSetX = (int) offSetXD;

        return new int[]{offSetY, offSetX};

    }

    private void ajaAStarAlgoritmi() {
        aStar = new AStar(karttaKuvana.getRGPArvot());
        aStar.suoritaReitinHaku();
        aStar.luoPolku(aStar.getMaali());
        saatuPolku = aStar.getPolku();
    }

    private void piirraPolkuKarttaan() {
        Graphics g = jLabelPolkuMask.getGraphics();

        for (int kayty : aStar.getAnalysoidut()) {
            int x = Analysoija.getSarake(kayty, karttaKuvana.getLeveys());
            int y = Analysoija.getRivi(kayty, karttaKuvana.getLeveys());
            int[] muutetutKoordinaatit = muutaKoordinaatitLyhyesta(y, x);
            //karttaKuvana.getBufferoituKuva().setRGB(x, y, Color.MAGENTA.getRGB());

            g.setColor(Color.MAGENTA);
            g.drawRect(muutetutKoordinaatit[1], muutetutKoordinaatit[0], 1, 1);

            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Ikkuna.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        g.setColor(Color.CYAN);

        for (int saatuPolku1 : saatuPolku) {
            int x = Analysoija.getSarake(saatuPolku1, karttaKuvana.getLeveys());
            int y = Analysoija.getRivi(saatuPolku1, karttaKuvana.getLeveys());

            // karttaKuvana.getBufferoituKuva().setRGB(x, y, Color.ORANGE.getRGB());
            int[] muutetutKoordinaatit = muutaKoordinaatitLyhyesta(y, x);
            g.drawRect(muutetutKoordinaatit[1], muutetutKoordinaatit[0], 5, 5);
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Ikkuna.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        g.dispose();
    }

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
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
//            java.util.logging.Logger.getLogger(Ikkuna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Ikkuna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Ikkuna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Ikkuna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Ikkuna().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jAsetusMenu;
    private javax.swing.JLabel jLabelKuva;
    private javax.swing.JLabel jLabelPolkuMask;
    private javax.swing.JMenuItem jMenuAStar;
    private javax.swing.JMenuItem jMenuAvaa;
    private javax.swing.JMenuItem jMenuPoistu;
    private javax.swing.JMenuItem jMenuResetoiKuva;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JMenu jTiedostoMenu;
    private javax.swing.JMenuBar jYlaMenu;
    // End of variables declaration//GEN-END:variables

}
