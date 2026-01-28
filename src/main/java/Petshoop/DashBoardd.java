/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Petshoop;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import Petshoop.obj.Koneksi;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JOptionPane;

/**
 *
 * @author ACER
 */
public class DashBoardd extends javax.swing.JFrame {

    /**
     * Creates new form DashBoardd
     */
    private Connection conn;

    public DashBoardd() {
        initComponents();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        
        try {
            conn = Petshoop.obj.Koneksi.Go();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Koneksi DB gagal: " + e.getMessage());
        }
    }
    
    
    private void loadProdukTerlaris(String tanggal) {
        try {
            String sql =
                "SELECT p.nama_produk, SUM(dt.jumlah) total " +
                "FROM detail_transaksi dt " +
                "JOIN produk p ON dt.id_produk = p.id_produk " +
                "JOIN kasir k ON dt.no_transaksi = k.no_transaksi " +
                "WHERE DATE(k.tanggal) = ? " +
                "GROUP BY p.nama_produk " +
                "ORDER BY total DESC LIMIT 1";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tanggal);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                jLabel8.setText(
                    rs.getString("nama_produk") +
                    " (" + rs.getInt("total") + " terjual)"
                );
            } else {
                jLabel8.setText("Tidak ada data");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void loadProdukTerjual(String tanggal) {
        try {
            String sql =
                "SELECT SUM(jumlah) total " +
                "FROM detail_transaksi dt " +
                "JOIN kasir k ON dt.no_transaksi = k.no_transaksi " +
                "WHERE DATE(k.tanggal) = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tanggal);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                jLabel9.setText(rs.getInt("total") + " item");
            } else {
                jLabel9.setText("0 item");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    private void loadPendapatan(String tanggal) {
        try {
            String sql =
                "SELECT SUM(total_harga) total FROM kasir WHERE DATE(tanggal) = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tanggal);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                NumberFormat rupiah =
                    NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                jLabel10.setText(rupiah.format(rs.getDouble("total")));
            } else {
                jLabel10.setText("Rp 0");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    private void loadGrafik(String tanggal) {
        try {
            PanelGrafik.removeAll();
            PanelGrafik.setLayout(new BorderLayout());

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            String sql =
                "SELECT p.nama_produk, SUM(dt.jumlah) AS total " +
                "FROM detail_transaksi dt " +
                "JOIN produk p ON dt.id_produk = p.id_produk " +
                "JOIN kasir k ON dt.no_transaksi = k.no_transaksi " +
                "WHERE DATE(k.tanggal) = ? " +
                "GROUP BY p.nama_produk " +
                "ORDER BY total DESC";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tanggal);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                dataset.addValue(
                    rs.getInt("total"),
                    "Jumlah Terjual",
                    rs.getString("nama_produk")
                );
            }

            JFreeChart chart = ChartFactory.createBarChart(
                "Grafik Penjualan Tanggal " + tanggal,
                "Produk",
                "Jumlah Terjual",
                dataset
            );

            ChartPanel chartPanel = new ChartPanel(chart);
            PanelGrafik.add(chartPanel, BorderLayout.CENTER);

            PanelGrafik.revalidate();
            PanelGrafik.repaint();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Grafik: " + e.getMessage());
        }
    }

    
    private void loadKinerjaKasir(String tanggal) {
        try {
            String sql =
                "SELECT p.nama_pegawai, COUNT(DISTINCT dt.no_transaksi) total " +
                "FROM detail_transaksi dt " +
                "JOIN kasir k ON dt.no_transaksi = k.no_transaksi " +
                "JOIN pegawai p ON dt.id_pegawai = p.id_pegawai " +
                "WHERE DATE(k.tanggal) = ? " +
                "GROUP BY p.nama_pegawai " +
                "ORDER BY total DESC LIMIT 1";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tanggal);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                lblKinerjaKasir.setText(
                    rs.getString("nama_pegawai") +
                    " (" + rs.getInt("total") + " transaksi)"
                );
            } else {
                lblKinerjaKasir.setText("Tidak ada data");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Kinerja Kasir: " + e.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        Kembalibtn = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        Tampilkanbtn = new javax.swing.JButton();
        txtperiode = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        PanelGrafik = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        lblKinerjaKasir = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 153, 102));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Dashboard");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 765, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1390, 80));

        jPanel3.setBackground(new java.awt.Color(255, 153, 102));

        Kembalibtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Kembalibtn.setText("Kembali");
        Kembalibtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KembalibtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(Kembalibtn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(Kembalibtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 84, 130, 720));

        jPanel5.setBackground(new java.awt.Color(102, 255, 102));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Pendapatan");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Isi Tanggal Dulu");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator3)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel10))
                .addContainerGap(108, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 150, 230, 170));

        jPanel6.setBackground(new java.awt.Color(255, 51, 51));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Produk Terjual");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Isi Tangal Dulu");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel9))
                .addContainerGap(125, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 150, 240, 170));

        jPanel7.setBackground(new java.awt.Color(102, 204, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Produk Terlaris");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Isi Tanggal Dulu");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 230, 170));

        Tampilkanbtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Tampilkanbtn.setText("Tampilkan");
        Tampilkanbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TampilkanbtnActionPerformed(evt);
            }
        });
        jPanel1.add(Tampilkanbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 100, 100, 30));

        txtperiode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtperiodeActionPerformed(evt);
            }
        });
        jPanel1.add(txtperiode, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 110, 140, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Masukan Tanggal");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, -1, -1));

        PanelGrafik.setBackground(new java.awt.Color(204, 204, 204));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Grafik Penjualan");

        javax.swing.GroupLayout PanelGrafikLayout = new javax.swing.GroupLayout(PanelGrafik);
        PanelGrafik.setLayout(PanelGrafikLayout);
        PanelGrafikLayout.setHorizontalGroup(
            PanelGrafikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelGrafikLayout.createSequentialGroup()
                .addGap(519, 519, 519)
                .addComponent(jLabel6)
                .addContainerGap(541, Short.MAX_VALUE))
        );
        PanelGrafikLayout.setVerticalGroup(
            PanelGrafikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelGrafikLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(294, Short.MAX_VALUE))
        );

        jPanel1.add(PanelGrafik, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 360, 1170, 320));

        jPanel9.setBackground(new java.awt.Color(255, 255, 51));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Kinerja Kasir");

        lblKinerjaKasir.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblKinerjaKasir.setText("Isi Tanggal Dulu");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(lblKinerjaKasir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblKinerjaKasir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 150, 250, 170));

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtperiodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtperiodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtperiodeActionPerformed

    private void TampilkanbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TampilkanbtnActionPerformed
        String tanggal = txtperiode.getText().trim();

        if (tanggal.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tanggal belum diisi!\nFormat: yyyy-mm-dd");
            return;
        }

        loadProdukTerlaris(tanggal);
        loadProdukTerjual(tanggal);
        loadPendapatan(tanggal);
        loadGrafik(tanggal);
        loadKinerjaKasir(tanggal);
    }//GEN-LAST:event_TampilkanbtnActionPerformed

    private void KembalibtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KembalibtnActionPerformed
        FormKasir fk = new FormKasir();
        fk.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_KembalibtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Kembalibtn;
    private javax.swing.JPanel PanelGrafik;
    private javax.swing.JButton Tampilkanbtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel lblKinerjaKasir;
    private javax.swing.JTextField txtperiode;
    // End of variables declaration//GEN-END:variables

}
