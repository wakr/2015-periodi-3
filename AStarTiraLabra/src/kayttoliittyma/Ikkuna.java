package kayttoliittyma;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import logiikka.AStar;
import logiikka.Analysoija;
import util.Kuva;
import util.Piste;

/**
 * Sovelluksen käyttöliittymä, joka visualisoi A*-algoritmin sekä siihen
 * liittyvän analysoinnin.
 *
 * @author kristianw
 */
public class Ikkuna extends javax.swing.JFrame {

    private Kuva karttaKuvana;
    private AStar aStar;
    private BufferedImage alkuPerainenKuva;
    private SwingWorker pathPainter;

    public Ikkuna() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelPolkuMask = new javax.swing.JLabel();
        jLabelKuva = new javax.swing.JLabel();
        jAStarProgressi = new javax.swing.JProgressBar();
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
        jLabelPolkuMask.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabelPolkuMaskMouseMoved(evt);
            }
        });
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
                .addComponent(jAStarProgressi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(jAStarProgressi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                karttaKuvana.konvertoi2DTaulukkoonRPGArvoina(karttaKuvana.getBufferoituKuva());
                Piirtaja p = new Piirtaja(this.jLabelKuva, this.karttaKuvana, this.alkuPerainenKuva);
                aStar = new AStar(karttaKuvana.getRGPArvot(), p);
                jAStarProgressi.setValue(0);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }


    }//GEN-LAST:event_jMenuAvaaActionPerformed

    private void jMenuPoistuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuPoistuActionPerformed
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_jMenuPoistuActionPerformed


    private void jMenuAStarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAStarActionPerformed

        luoTaustaProsessiPiirtamiselle();
        pathPainter.execute();


    }//GEN-LAST:event_jMenuAStarActionPerformed

    private void luoTaustaProsessiPiirtamiselle() {
        pathPainter = new SwingWorker<Void, Void>() {

            @Override
            protected void done() {
                super.done();
            }

            @Override
            protected Void doInBackground() throws Exception {
                ajaAStarAlgoritmi();
                jAStarProgressi.setValue(100);
                return null;
            }

        };
    }

    private void jLabelPolkuMaskMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelPolkuMaskMouseClicked

        if (onkoAlkuVaiheessa()) {
            muutaMaaliJaLahtoKlikkauksella(evt);
            aStar.ilmoitaMaalinMuutoksesta();

            jLabelKuva.repaint();

        }

    }//GEN-LAST:event_jLabelPolkuMaskMouseClicked

    private boolean onkoAlkuVaiheessa() {
        return alkuPerainenKuva != null && aStar != null && pathPainter != null && jAStarProgressi.getValue() != 100;
    }

    private void muutaMaaliJaLahtoKlikkauksella(MouseEvent evt) {
        Piste muutetutKoordinaatit = muutaKoordinaatitPitkasta(evt.getY(), evt.getX());
        karttaKuvana.getBufferoituKuva().setRGB(muutetutKoordinaatit.getX(), muutetutKoordinaatit.getY(), Color.RED.getRGB());
        karttaKuvana.getBufferoituKuva().setRGB(aStar.getMaaliPisteena().getX(), aStar.getMaaliPisteena().getY(), Color.WHITE.getRGB());
        aStar.asetaMaali(muutetutKoordinaatit.getX(), muutetutKoordinaatit.getY());
        int tY = Analysoija.getRivi(aStar.getNykyinenSolmu(), alkuPerainenKuva.getWidth());
        int tX = Analysoija.getSarake(aStar.getNykyinenSolmu(), alkuPerainenKuva.getWidth());
        aStar.asetaLahto(tX, tY);
    }

    private void jMenuResetoiKuvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuResetoiKuvaActionPerformed

        jAStarProgressi.setValue(0);
        pathPainter.cancel(false);
        aStar.keskeyta();
        jLabelPolkuMask.repaint();
        Piirtaja p = new Piirtaja(this.jLabelKuva, this.karttaKuvana, this.alkuPerainenKuva);
        aStar = new AStar(karttaKuvana.getRGPArvot(), p);

    }//GEN-LAST:event_jMenuResetoiKuvaActionPerformed

    private void jLabelPolkuMaskMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelPolkuMaskMouseMoved
        // TO-DO
    }//GEN-LAST:event_jLabelPolkuMaskMouseMoved

    /**
     * Muuttaa koordinaatin skaalatusta kuvasta oikean kuvan X ja Y
     * koordinaateiksi
     *
     * @param x X-koordinaatti
     * @param y Y-koordinaatti
     * @return Taulukko, jossa 0-paikalla Y ja 1-paikalla X koordinaatti
     * muutettuna
     */
    public Piste muutaKoordinaatitPitkasta(int y, int x) {

        double korkeusKerroin = (double) jLabelKuva.getHeight() / (double) alkuPerainenKuva.getHeight();
        double leveysKerroin = (double) jLabelKuva.getWidth() / (double) alkuPerainenKuva.getWidth();

        double offSetYD = y / korkeusKerroin;
        double offSetXD = x / leveysKerroin;

        int offSetY = (int) offSetYD;
        int offSetX = (int) offSetXD;

        return new Piste(offSetX, offSetY);
    }

    /**
     * @param y Oikean kuvan y-koordinaatti
     * @param x Oikean kuvan x-koordinaatti
     * @return Taulukko, jossa 0-paikalla Y ja 1-paikalla X koordinaatti
     * muutettuna
     */
    public Piste muutaKoordinaatitLyhyesta(int y, int x) {

        double korkeusKerroin = (double) jLabelKuva.getHeight() / (double) alkuPerainenKuva.getHeight();
        double leveysKerroin = (double) jLabelKuva.getWidth() / (double) alkuPerainenKuva.getWidth();

        double offSetYD = y * korkeusKerroin;
        double offSetXD = x * leveysKerroin;

        int offSetY = (int) offSetYD;
        int offSetX = (int) offSetXD;

        return new Piste(offSetX, offSetY);

    }

    private void ajaAStarAlgoritmi() {
        aStar.resetoiAlgoritmi();
        aStar.suoritaReitinHaku();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar jAStarProgressi;
    private javax.swing.JMenu jAsetusMenu;
    private javax.swing.JLabel jLabelKuva;
    private javax.swing.JLabel jLabelPolkuMask;
    private javax.swing.JMenuItem jMenuAStar;
    private javax.swing.JMenuItem jMenuAvaa;
    private javax.swing.JMenuItem jMenuPoistu;
    private javax.swing.JMenuItem jMenuResetoiKuva;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JMenu jTiedostoMenu;
    private javax.swing.JMenuBar jYlaMenu;
    // End of variables declaration//GEN-END:variables

}
