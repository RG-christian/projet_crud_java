

import controlleur.l3.iirt.CarnetAdresseApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7144763319669047190L;
	private final JTextField txtEmail;
    private final JPasswordField txtPassword;

    public Login() {
        setTitle("Connexion - Carnet d’Adresses");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Partie gauche (formulaire)
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        leftPanel.setPreferredSize(new Dimension(400, getHeight()));

        // Avatar carré orange
        JLabel avatar = new JLabel("M", SwingConstants.CENTER);
        avatar.setOpaque(true);
        avatar.setBackground(new Color(255, 153, 51));
        avatar.setForeground(Color.WHITE);
        avatar.setFont(new Font("SansSerif", Font.BOLD, 30));
        avatar.setPreferredSize(new Dimension(60, 60));
        avatar.setMaximumSize(new Dimension(60, 60));
        avatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        avatar.setHorizontalAlignment(SwingConstants.CENTER);

        // Titre et description
        JLabel title = new JLabel("Get started");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("<html><center>bienvenue dans votre carnet d'addresse <br>veuillez vous connecter </center></html>");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Champ Email
        JLabel lblEmail = new JLabel("Email");
        txtEmail = new JTextField();
        txtEmail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // Champ Password
        JLabel lblPassword = new JLabel("Password");
        txtPassword = new JPasswordField();
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // Bouton Se connecter
        JButton btnLogin = new JButton("Sign-In");
        btnLogin.setBackground(new Color(255, 153, 51));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnLogin.addActionListener(new LoginAction());

        // Lien "Créer un compte"
        JLabel footer = new JLabel("<html><center>Pas encore de compte ? <u>Créer un compte</u></center></html>");
        footer.setFont(new Font("SansSerif", Font.PLAIN, 12));
        footer.setForeground(Color.GRAY);
        footer.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Ajouter tout au panel gauche
        leftPanel.add(avatar);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(title);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(subtitle);
        leftPanel.add(Box.createVerticalStrut(30));
        leftPanel.add(lblEmail);
        leftPanel.add(txtEmail);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(lblPassword);
        leftPanel.add(txtPassword);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(btnLogin);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(footer);

     // === Partie droite avec image centrée et non déformée ===
        JPanel rightPanel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("assets/images/4.png"); // Chemin de ton image
                Image img = icon.getImage();

                int panelWidth = getWidth();
                int panelHeight = getHeight();

                int imgWidth = img.getWidth(null);
                int imgHeight = img.getHeight(null);

                if (imgWidth > 0 && imgHeight > 0) {
                    // Calcul du ratio pour ne pas déformer
                    double scaleX = (double) panelWidth / imgWidth;
                    double scaleY = (double) panelHeight / imgHeight;
                    double scale = Math.min(scaleX, scaleY); // garder proportions

                    int drawWidth = (int) (imgWidth * scale);
                    int drawHeight = (int) (imgHeight * scale);

                    int x = (panelWidth - drawWidth) / 2;
                    int y = (panelHeight - drawHeight) / 2;

                    g.drawImage(img, x, y, drawWidth, drawHeight, this);
                }
            }
        };
        rightPanel.setPreferredSize(new Dimension(400, 500));
        rightPanel.setLayout(new BorderLayout());




        // Ajout au frame
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
    }

    private class LoginAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String email = txtEmail.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(Login.this, "Veuillez remplir les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (email.equals("rapo@gmail.com") && password.equals("rapo123")) {
                JOptionPane.showMessageDialog(Login.this, "Connexion réussie !");
                dispose(); // Fermer la fenêtre de connexion
                new CarnetAdresseApp(); // Lancer l'application
            } else {
                JOptionPane.showMessageDialog(Login.this, "Identifiants incorrects.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}
