package fi.wakr.kayttoliittyma;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;

import fi.wakr.logiikka.reitinhaku.AStar;
import fi.wakr.logiikka.Analysoija;
import fi.wakr.logiikka.reitinhaku.Dijkstra;
import fi.wakr.logiikka.reitinhaku.MTAA;
import fi.wakr.logiikka.reitinhaku.Reitinhakija;
import fi.wakr.util.Kuva;
import fi.wakr.util.Piste;
import java.io.File;

/**
 * Sovelluksen käyttöliittymä, joka visualisoi reitinhaun sekä siihen liittyvän
 * analysoinnin.
 *
 * @author Kristian Wahlroos
 */
public class Ikkuna extends javax.swing.JFrame {

    private Kuva karttaKuvana;
    private Reitinhakija reitinhakija;
    private BufferedImage alkuPerainenKuva;
    private SwingWorker pathPainter;
    private Piirtaja p;

    public Ikkuna() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelPolkuMask = new javax.swing.JLabel();
        jLabelKuva = new javax.swing.JLabel();
        jProgressi = new javax.swing.JProgressBar();
        jLabelTime = new javax.swing.JLabel();
        jTextPiirtoNopeus = new javax.swing.JTextField();
        jLabelStock = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextTime = new javax.swing.JTextPane();
        jLabelTime1 = new javax.swing.JLabel();
        jYlaMenu = new javax.swing.JMenuBar();
        jTiedostoMenu = new javax.swing.JMenu();
        jMenuAvaa = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuPoistu = new javax.swing.JMenuItem();
        jAsetusMenu = new javax.swing.JMenu();
        jMenuDijkstra = new javax.swing.JMenuItem();
        jMenuMTAA = new javax.swing.JMenuItem();
        jMenuAStar = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuResetoiKuva = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabelPolkuMask.setOpaque(true);
        jLabelPolkuMask.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelPolkuMaskMouseClicked(evt);
            }
        });
        jLabelPolkuMask.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabelPolkuMaskMouseMoved(evt);
            }
        });

        jLabelTime.setText("Ajossa kesti: ");

        jTextPiirtoNopeus.setText("0");

        jLabelStock.setText("Piirron hidastus:");

        jTextTime.setEnabled(false);
        jScrollPane1.setViewportView(jTextTime);

        jLabelTime1.setText("s");

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

        jAsetusMenu.setText("Algoritmit");

        jMenuDijkstra.setText("Dijkstra");
        jMenuDijkstra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuDijkstraActionPerformed(evt);
            }
        });
        jAsetusMenu.add(jMenuDijkstra);

        jMenuMTAA.setText("MTAA*");
        jMenuMTAA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuMTAAActionPerformed(evt);
            }
        });
        jAsetusMenu.add(jMenuMTAA);

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
                .addGap(72, 72, 72)
                .addComponent(jLabelStock)
                .addGap(18, 18, 18)
                .addComponent(jTextPiirtoNopeus, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jProgressi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jLabelTime)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTime1)
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
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextPiirtoNopeus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelStock))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTime1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabelTime)
                        .addComponent(jProgressi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jLabelKuva, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(72, Short.MAX_VALUE)
                    .addComponent(jLabelPolkuMask, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(112, 112, 112)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuAvaaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAvaaActionPerformed
        //JFileChooser kuvanAvaus = new JFileChooser(System.getProperty("user.dir") + "/src/main/java/fi/wakr/util/karttakuvat");
        JFileChooser kuvanAvaus = new JFileChooser(new File(".").getAbsolutePath());
        BufferedImage kuva;
        if (kuvanAvaus.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                kuva = ImageIO.read(kuvanAvaus.getSelectedFile());
                alkuPerainenKuva = ImageIO.read(kuvanAvaus.getSelectedFile());
                karttaKuvana = new Kuva(kuva, jLabelKuva.getHeight(), jLabelKuva.getWidth());
                jLabelKuva.setIcon(new ImageIcon(karttaKuvana.getKuva()));
                karttaKuvana.konvertoi2DTaulukkoonRPGArvoina(karttaKuvana.getBufferoituKuva());
                p = new Piirtaja(this.jLabelKuva, this.karttaKuvana, this.alkuPerainenKuva);

                jProgressi.setValue(0);

                if (pathPainter != null) {
                    pathPainter.cancel(false);
                }

                if (reitinhakija != null) {
                    alustaReitinHakijaSamaanKuinNyt();
                }

            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }


    }//GEN-LAST:event_jMenuAvaaActionPerformed


    private void jMenuPoistuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuPoistuActionPerformed
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_jMenuPoistuActionPerformed


    private void jMenuAStarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAStarActionPerformed

        if (karttaKuvana == null) {
            return;
        }

        if (pathPainter != null) {
            pathPainter.cancel(false);
        }
        readAndSetDelay();
        reitinhakija = new AStar(karttaKuvana.getRGPArvot(), p);
        luoTaustaProsessiPiirtamiselle();
        pathPainter.execute();


    }//GEN-LAST:event_jMenuAStarActionPerformed

    // lukee hidastuksen
    private void readAndSetDelay() {
        if (p != null) {
            try {
                p.setDelay(Integer.parseInt(jTextPiirtoNopeus.getText()));
            } catch (Exception e) {
            }
        }

    }

    // Rinakkaistaminen piirtämiselle ja logiikalle
    private void luoTaustaProsessiPiirtamiselle() {
        pathPainter = new SwingWorker<Void, Void>() {

            public void nuku(int ms) {
                try {
                    Thread.sleep(ms);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Ikkuna.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            protected Void doInBackground() throws Exception {
                long aloitusAika = System.nanoTime();
                ajaReitinHaku();
                jLabelKuva.repaint();

                if (this.isCancelled()) {
                    return null;
                }

                nuku(4);
                reitinhakija.naytaPolku();
                jProgressi.setValue(100);
                long lopetusAika = System.nanoTime() - aloitusAika;
                jTextTime.setText("" + (lopetusAika / 1000000000.0));
                return null;
            }

        };
    }


    private void jLabelPolkuMaskMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelPolkuMaskMouseClicked

        if (onkoAlkuVaiheessa()) {

            pathPainter.cancel(true);
            reitinhakija.keskeyta();

            muutaMaaliJaLahtoKlikkauksella(evt);
            reitinhakija.ilmoitaMaalinMuutoksesta();

            luoTaustaProsessiPiirtamiselle();
            pathPainter.execute();

        }

    }//GEN-LAST:event_jLabelPolkuMaskMouseClicked

    private boolean onkoAlkuVaiheessa() {
        return alkuPerainenKuva != null && reitinhakija != null && pathPainter != null;
    }

    private void muutaMaaliJaLahtoKlikkauksella(MouseEvent evt) {
        Piste muutetutKoordinaatit = muutaKoordinaatitPitkasta(evt.getY(), evt.getX());
        karttaKuvana.getBufferoituKuva().setRGB(muutetutKoordinaatit.getX(), muutetutKoordinaatit.getY(), Color.RED.getRGB());
        karttaKuvana.getBufferoituKuva().setRGB(reitinhakija.getMaaliPisteena().getX(), reitinhakija.getMaaliPisteena().getY(), Color.WHITE.getRGB());
        reitinhakija.asetaMaali(muutetutKoordinaatit.getX(), muutetutKoordinaatit.getY());
        int tY = Analysoija.getRivi(reitinhakija.getNykyinenSolmu(), alkuPerainenKuva.getWidth());
        int tX = Analysoija.getSarake(reitinhakija.getNykyinenSolmu(), alkuPerainenKuva.getWidth());
        reitinhakija.asetaLahto(tX, tY);
    }

    private void jMenuResetoiKuvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuResetoiKuvaActionPerformed

        if (karttaKuvana == null) {
            return;
        }

        jProgressi.setValue(0);
        if (pathPainter != null) {
            pathPainter.cancel(false);
        }
        reitinhakija.keskeyta();
        jLabelPolkuMask.repaint();
        p = new Piirtaja(this.jLabelKuva, this.karttaKuvana, this.alkuPerainenKuva);
        reitinhakija = new Dijkstra(karttaKuvana.getRGPArvot(), p);

    }//GEN-LAST:event_jMenuResetoiKuvaActionPerformed

    private void alustaReitinHakijaSamaanKuinNyt() {
        if (reitinhakija instanceof Dijkstra) {
            reitinhakija = new Dijkstra(karttaKuvana.getRGPArvot(), p);
        } else if (reitinhakija instanceof AStar) {
            reitinhakija = new AStar(karttaKuvana.getRGPArvot(), p);
        } else {
            reitinhakija = new MTAA(karttaKuvana.getRGPArvot(), p);
        }
    }


    private void jLabelPolkuMaskMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelPolkuMaskMouseMoved

    }//GEN-LAST:event_jLabelPolkuMaskMouseMoved

    private void jMenuMTAAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuMTAAActionPerformed
        if (karttaKuvana == null) {
            return;
        }

        if (pathPainter != null) {
            pathPainter.cancel(false);

        }
        readAndSetDelay();
        if (!(reitinhakija instanceof MTAA)) {
            reitinhakija = new MTAA(karttaKuvana.getRGPArvot(), p);
        }
        luoTaustaProsessiPiirtamiselle();
        pathPainter.execute();
    }//GEN-LAST:event_jMenuMTAAActionPerformed

    private void jMenuDijkstraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuDijkstraActionPerformed

        if (karttaKuvana == null) {
            return;
        }

        if (pathPainter != null) {
            pathPainter.cancel(false);

        }
        readAndSetDelay();
        reitinhakija = new Dijkstra(karttaKuvana.getRGPArvot(), p);
        luoTaustaProsessiPiirtamiselle();
        pathPainter.execute();
    }//GEN-LAST:event_jMenuDijkstraActionPerformed

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

    private void ajaReitinHaku() {
        reitinhakija.suoritaReitinHaku();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jAsetusMenu;
    private javax.swing.JLabel jLabelKuva;
    private javax.swing.JLabel jLabelPolkuMask;
    private javax.swing.JLabel jLabelStock;
    private javax.swing.JLabel jLabelTime;
    private javax.swing.JLabel jLabelTime1;
    private javax.swing.JMenuItem jMenuAStar;
    private javax.swing.JMenuItem jMenuAvaa;
    private javax.swing.JMenuItem jMenuDijkstra;
    private javax.swing.JMenuItem jMenuMTAA;
    private javax.swing.JMenuItem jMenuPoistu;
    private javax.swing.JMenuItem jMenuResetoiKuva;
    private javax.swing.JProgressBar jProgressi;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JTextField jTextPiirtoNopeus;
    private javax.swing.JTextPane jTextTime;
    private javax.swing.JMenu jTiedostoMenu;
    private javax.swing.JMenuBar jYlaMenu;
    // End of variables declaration//GEN-END:variables

}
