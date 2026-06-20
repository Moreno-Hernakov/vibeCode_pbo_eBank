/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.ebanking.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.ebanking.model.Menu;
import com.ebanking.model.User;
import com.ebanking.view.page.Router;
import com.ebanking.view.page.DashboardPage;
import com.ebanking.view.page.TransferPage;
import com.ebanking.view.page.MutasiPage;
import com.ebanking.view.page.PembayaranPage;
import com.ebanking.view.page.AdminPage;
import java.sql.SQLException;

/**
 *
 * @author user
 */
public class IsiBank extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(IsiBank.class.getName());
    private Color defaultColor = new Color(0, 102, 102);
    private Color activeColor = new Color(255, 255, 255);

    /** User yang sedang login; sumber daftar menu untuk sidebar & body. */
    private User currentUser;
    /** Referensi tombol sidebar yang dibuat dinamis (untuk reset highlight). */
    private final List<JButton> menuButtons = new ArrayList<>();
    /** Router yang mengelola perpindahan halaman body (CardLayout). */
    private Router router;

    /**
     * Creates new form IsiBank (konstruktor default - dipakai untuk testing UI).
     */
    public IsiBank() {
        initComponents();
        
    }

    /**
     * Konstruktor utama: menerima User hasil login lalu membangun
     * layout 3-section (header tetap, sidebar dinamis, body swap via CardLayout).
     */
    public IsiBank(User user) throws SQLException {
        this.currentUser = user;
        initComponents();
        setupBody();
        setupRouter();
        renderMenu();
    }

    /**
     * Task 1 - Jadikan jPanel2 sebagai container body ber-CardLayout,
     * diposisikan di kanan sidebar dan di bawah header.
     */
    private void setupBody() {
        // Layout 3-section: header tipis, sidebar & body sejajar di bawahnya.
        int headerHeight = 45;
        int sidebarLeft = 10;
        int sidebarWidth = 228;
        int totalWidth = 940;
        int totalHeight = 523;
        int contentTop = headerHeight;                 // sidebar & body mulai tepat di bawah header
        int bodyLeft = sidebarLeft + sidebarWidth;      // body menempel di kanan sidebar (flush)
        // Header tipis selebar penuh.
        jPanel3.setBounds(0, 0, totalWidth, headerHeight);
        // Sidebar sejajar di bawah header.
        jPanel5.setBounds(sidebarLeft, contentTop, sidebarWidth, totalHeight - contentTop);
        // Body di kanan sidebar, top sejajar dengan sidebar.
        jPanel2.setBounds(bodyLeft, contentTop, totalWidth - bodyLeft, totalHeight - contentTop);
        jPanel2.setBackground(new Color(245, 245, 245));
        jPanel2.setLayout(new CardLayout());
    }

    /**
     * Task 2 - Bangun satu panel body (placeholder) untuk tiap menu,
     * di-register ke CardLayout dengan key = routePath.
     */
    /**
     * Daftarkan semua halaman body ke Router (route -> Page).
     * Menambah menu baru cukup register satu Page di sini.
     */
    private void setupRouter() throws SQLException {
        router = new Router(jPanel2);
        router.register(new DashboardPage(currentUser));
        router.register(new TransferPage(currentUser));
        router.register(new MutasiPage(currentUser));
        router.register(new PembayaranPage(currentUser));
        router.register(new AdminPage(currentUser));
    }

    private void buildContentPanels() {
        if (currentUser == null || currentUser.getMenus() == null) {
            return;
        }
        for (Menu menu : currentUser.getMenus()) {
            JPanel page = new JPanel();
            page.setBackground(new Color(245, 245, 245));
            JLabel label = new JLabel(menu.getMenuTitle());
            label.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20));
            page.add(label);
            jPanel2.add(page, menu.getRoutePath());
        }
        jPanel2.revalidate();
        jPanel2.repaint();
    }

    /**
     * Task 3 - Render tombol sidebar secara dinamis dari daftar menu user.
     * Klik tombol hanya menukar body (CardLayout), header & sidebar tetap.
     */
    private void renderMenu() {
        jPanel5.removeAll();
        menuButtons.clear();

        if (currentUser == null || currentUser.getMenus() == null) {
            jPanel5.revalidate();
            jPanel5.repaint();
            return;
        }

        List<Menu> menus = currentUser.getMenus();
        for (int i = 0; i < menus.size(); i++) {
            final Menu menu = menus.get(i);
            // Logout tidak ditampilkan di sidebar; nanti ditaruh di header.
            if ("/logout".equals(menu.getRoutePath())) {
                continue;
            }
            JButton btn = new JButton(menu.getMenuTitle());
            btn.setBackground(defaultColor);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setHorizontalAlignment(JButton.LEFT);
            btn.setBounds(0, 0 + (i * 30), 230, 30);
            btn.addActionListener(e -> {
                setActiveMenu(btn);
                router.navigate(menu.getRoutePath());
            });
            jPanel5.add(btn);
            menuButtons.add(btn);
        }

        // Tampilkan menu pertama secara default.
        if (!menuButtons.isEmpty()) {
            setActiveMenu(menuButtons.get(0));
            router.navigate(menus.get(0).getRoutePath());
        }

        jPanel5.revalidate();
        jPanel5.repaint();
    }

    /**
     * Highlight tombol menu aktif; reset tombol dinamis lainnya ke warna default.
     */
    private void setActiveMenu(JButton active) {
        for (JButton btn : menuButtons) {
            btn.setBackground(defaultColor);
            btn.setForeground(Color.WHITE);
        }
        active.setBackground(activeColor);
        active.setForeground(defaultColor);
    }
    
    private void resetButton() {
      
  
    }
    
    private void setActive(JButton btn) {
    resetButton();
    btn.setBackground(activeColor);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jDialog1 = new javax.swing.JDialog();
        menuBar1 = new java.awt.MenuBar();
        menu1 = new java.awt.Menu();
        menu2 = new java.awt.Menu();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();

        jMenu1.setText("jMenu1");

        jMenu2.setText("jMenu2");

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        menu1.setLabel("File");
        menuBar1.add(menu1);

        menu2.setLabel("Edit");
        menuBar1.add(menu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        jPanel4.setBackground(new java.awt.Color(153, 153, 153));
        jPanel4.setPreferredSize(new java.awt.Dimension(940, 900));
        jPanel4.setLayout(null);

        jPanel3.setBackground(new java.awt.Color(0, 102, 102));

        jLabel1.setFont(new java.awt.Font("Showcard Gothic", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("dompetku");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel1)
                .addContainerGap(696, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.add(jPanel3);
        jPanel3.setBounds(0, 0, 880, 50);

        jPanel5.setBackground(new java.awt.Color(0, 102, 102));
        jPanel5.setAutoscrolls(true);
        jPanel5.setLayout(null);
        jPanel4.add(jPanel5);
        jPanel5.setBounds(10, 50, 228, 430);

        jPanel2.setLayout(null);
        jPanel4.add(jPanel2);
        jPanel2.setBounds(1120, 0, 0, 0);

        jPanel1.add(jPanel4);
        jPanel4.setBounds(-10, 0, 940, 523);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 861, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new IsiBank().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private java.awt.Menu menu1;
    private java.awt.Menu menu2;
    private java.awt.MenuBar menuBar1;
    // End of variables declaration//GEN-END:variables
}

