package view.l3.iirt;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CarnetAdresse extends JFrame {
    private static final long serialVersionUID = -3198112101824024268L;

    public JTextField txtNom, txtPrenom, txtTelephone, txtEmail, txtRecherche;
    public JButton btnAjouter, btnSupprimer, btnSauvegarder, btnCharger, btnModifier;
    public JButton btnTheme, btnVider, btnSuivant, btnPrecedent;
    public JTable table;
    public DefaultTableModel tableModel;
    public JLabel lblTotalContacts;

    public int page = 0;
    public final int PAGE_SIZE = 50;

    public CarnetAdresse() {
        setTitle("Carnet d'adresses");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelRecherche = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtRecherche = new JTextField(20);
        panelRecherche.add(new JLabel("🔍 Recherche :"));
        panelRecherche.add(txtRecherche);
        add(panelRecherche, BorderLayout.NORTH);

        JPanel panelCentre = new JPanel(new BorderLayout());
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 10, 10));
        txtNom = new JTextField(); txtPrenom = new JTextField();
        txtTelephone = new JTextField(); txtEmail = new JTextField();

        panelForm.setBorder(BorderFactory.createTitledBorder("Ajouter / Modifier un contact"));
        panelForm.add(new JLabel("Nom :")); panelForm.add(txtNom);
        panelForm.add(new JLabel("Prénom :")); panelForm.add(txtPrenom);
        panelForm.add(new JLabel("Téléphone :")); panelForm.add(txtTelephone);
        panelForm.add(new JLabel("Email :")); panelForm.add(txtEmail);

        tableModel = new DefaultTableModel(new Object[]{"Nom", "Prénom", "Téléphone", "Email"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        panelCentre.add(panelForm, BorderLayout.NORTH);
        panelCentre.add(scrollPane, BorderLayout.CENTER);
        add(panelCentre, BorderLayout.CENTER);

        JPanel panelBas = new JPanel();

        btnAjouter = new JButton("Ajouter");
        btnSupprimer = new JButton("Supprimer");
        btnSauvegarder = new JButton("Sauvegarder");
        btnCharger = new JButton("Charger");
        btnModifier = new JButton("Modifier");
        btnTheme = new JButton("🌗 Thème");
        btnVider = new JButton("🧹 Vider champs");
        btnPrecedent = new JButton("⬅️ Précédent");
        btnSuivant = new JButton("Suivant ➡️");
        lblTotalContacts = new JLabel("Total : 0 contacts");

        btnAjouter.setBackground(Color.GREEN);
        btnSupprimer.setBackground(Color.RED);
        btnSauvegarder.setBackground(Color.CYAN);
        btnCharger.setBackground(Color.ORANGE);
        btnModifier.setBackground(Color.MAGENTA);
        btnTheme.setBackground(Color.LIGHT_GRAY);
        btnVider.setBackground(Color.PINK);
        btnPrecedent.setBackground(Color.WHITE);
        btnSuivant.setBackground(Color.WHITE);

        panelBas.add(btnAjouter);
        panelBas.add(btnSupprimer);
        panelBas.add(btnSauvegarder);
        panelBas.add(btnCharger);
        panelBas.add(btnModifier);
        panelBas.add(btnVider);
        panelBas.add(btnTheme);
        panelBas.add(btnPrecedent);
        panelBas.add(btnSuivant);
        panelBas.add(lblTotalContacts);

        add(panelBas, BorderLayout.SOUTH);
    }
}
