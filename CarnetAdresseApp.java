package controlleur.l3.iirt;

import model.l3.iirt.Contact;
import view.l3.iirt.CarnetAdresse;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CarnetAdresseApp {
    private final CarnetAdresse ui;
    private final ArrayList<Contact> contacts = new ArrayList<>();

    public CarnetAdresseApp() {
        ui = new CarnetAdresse();
        initialiserActions();
        ui.setVisible(true);

        // 🔒 Sauvegarde automatique à la fermeture
        ui.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                sauvegarderAuto();
            }
        });

        // 🕒 Sauvegarde toutes les 5 minutes
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                sauvegarderAuto();
            }
        }, 0, 5 * 60 * 1000);
    }

    private void initialiserActions() {
        // Ajouter
        ui.btnAjouter.addActionListener(e -> {
            String nom = ui.txtNom.getText().trim();
            String prenom = ui.txtPrenom.getText().trim();
            String tel = ui.txtTelephone.getText().trim();
            String email = ui.txtEmail.getText().trim();

            if (!verifierChamps(nom, prenom, tel, email)) return;

            for (Contact c : contacts) {
                if (c.getEmail().equalsIgnoreCase(email) || c.getTelephone().equals(tel)) {
                    message("Email ou téléphone déjà utilisé", "Erreur");
                    return;
                }
            }

            contacts.add(new Contact(nom, prenom, tel, email));
            afficherPage(0);
            viderChamps();
        });

        // Modifier
        ui.btnModifier.addActionListener(e -> {
            int i = ui.table.getSelectedRow();
            if (i >= 0) {
                String nom = ui.txtNom.getText().trim();
                String prenom = ui.txtPrenom.getText().trim();
                String tel = ui.txtTelephone.getText().trim();
                String email = ui.txtEmail.getText().trim();

                if (!verifierChamps(nom, prenom, tel, email)) return;

                Contact c = new Contact(nom, prenom, tel, email);
                contacts.set(ui.page * ui.PAGE_SIZE + i, c);
                afficherPage(ui.page);
                viderChamps();
                message("Contact modifié", "Succès");
            } else {
                message("Sélectionnez un contact à modifier", "Info");
            }
        });

        // Supprimer
        ui.btnSupprimer.addActionListener(e -> {
            int i = ui.table.getSelectedRow();
            if (i >= 0) {
                int confirm = JOptionPane.showConfirmDialog(ui, "Supprimer ce contact ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    contacts.remove(ui.page * ui.PAGE_SIZE + i);
                    afficherPage(ui.page);
                }
            } else {
                message("Sélectionnez un contact", "Info");
            }
        });

        // Sauvegarder
        ui.btnSauvegarder.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(ui) == JFileChooser.APPROVE_OPTION) {
                File fichier = chooser.getSelectedFile();
                try (PrintWriter writer = new PrintWriter(fichier)) {
                    for (Contact c : contacts) {
                        writer.println(c.toCSV());
                    }

                    try (FileWriter txt = new FileWriter("contacts.txt")) {
                        for (Contact c : contacts) txt.write(c.toString() + "\n");
                    }

                    try (FileWriter json = new FileWriter("contacts.json")) {
                        json.write("[\n");
                        for (int i = 0; i < contacts.size(); i++) {
                            Contact c = contacts.get(i);
                            json.write("  {\"nom\":\"" + c.getNom() + "\",\"prenom\":\"" + c.getPrenom() + "\",\"tel\":\"" + c.getTelephone() + "\",\"email\":\"" + c.getEmail() + "\"}");
                            if (i < contacts.size() - 1) json.write(",");
                            json.write("\n");
                        }
                        json.write("]");
                    }

                    String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                    message("Sauvegarde réussie le " + date, "Succès");
                } catch (Exception ex) {
                    message("Erreur lors de la sauvegarde", "Erreur");
                }
            }
        });

        // Charger
        ui.btnCharger.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(ui) == JFileChooser.APPROVE_OPTION) {
                File fichier = chooser.getSelectedFile();
                try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
                    contacts.clear();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] data = line.split(",");
                        if (data.length == 4) {
                            contacts.add(new Contact(data[0], data[1], data[2], data[3]));
                        }
                    }
                    afficherPage(0);
                } catch (Exception ex) {
                    message("Erreur de chargement", "Erreur");
                }
            }
        });

        // Recherche
        ui.txtRecherche.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String filtre = ui.txtRecherche.getText().toLowerCase();
                ui.tableModel.setRowCount(0);
                if (filtre.isEmpty()) {
                    afficherPage(0);
                    return;
                }
                for (Contact c : contacts) {
                    if (c.getNom().toLowerCase().contains(filtre) ||
                        c.getPrenom().toLowerCase().contains(filtre) ||
                        c.getEmail().toLowerCase().contains(filtre) ||
                        c.getTelephone().contains(filtre)) {
                        ui.tableModel.addRow(new Object[]{c.getNom(), c.getPrenom(), c.getTelephone(), c.getEmail()});
                    }
                }
            }
        });

        // Double-clic → détails
        ui.table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = ui.table.getSelectedRow();
                if (i >= 0 && e.getClickCount() == 2) {
                    Contact c = contacts.get(ui.page * ui.PAGE_SIZE + i);
                    String msg = "Nom : " + c.getNom() + "\nPrénom : " + c.getPrenom() + "\nTéléphone : " + c.getTelephone() + "\nEmail : " + c.getEmail();
                    JOptionPane.showMessageDialog(ui, msg, "Détails du contact", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Thème clair/sombre
        ui.btnTheme.addActionListener(e -> {
            try {
                boolean isMetal = UIManager.getLookAndFeel().getName().toLowerCase().contains("metal");
                UIManager.setLookAndFeel(isMetal ? UIManager.getSystemLookAndFeelClassName() : "javax.swing.plaf.metal.MetalLookAndFeel");
                SwingUtilities.updateComponentTreeUI(ui);
            } catch (Exception ex) {
                message("Erreur de thème", "Erreur");
            }
        });

        // Vider
        ui.btnVider.addActionListener(e -> viderChamps());

        // Pagination
        ui.btnSuivant.addActionListener(e -> {
            if ((ui.page + 1) * ui.PAGE_SIZE < contacts.size()) {
                ui.page++;
                afficherPage(ui.page);
            }
        });

        ui.btnPrecedent.addActionListener(e -> {
            if (ui.page > 0) {
                ui.page--;
                afficherPage(ui.page);
            }
        });
    }

    private void afficherPage(int page) {
        ui.tableModel.setRowCount(0);
        int start = page * ui.PAGE_SIZE;
        int end = Math.min(start + ui.PAGE_SIZE, contacts.size());
        for (int i = start; i < end; i++) {
            Contact c = contacts.get(i);
            ui.tableModel.addRow(new Object[]{c.getNom(), c.getPrenom(), c.getTelephone(), c.getEmail()});
        }
        mettreAJourCompteur();
    }

    private boolean verifierChamps(String nom, String prenom, String tel, String email) {
        if (nom.isEmpty() || prenom.isEmpty()) {
            message("Nom et prénom obligatoires", "Erreur");
            return false;
        }
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            message("Email invalide", "Erreur");
            return false;
        }
        if (!tel.matches("^\\+?\\d{8,15}$")) {
            message("Téléphone invalide", "Erreur");
            return false;
        }
        return true;
    }

    private void sauvegarderAuto() {
        try (PrintWriter writer = new PrintWriter("autosave.csv")) {
            for (Contact c : contacts) writer.println(c.toCSV());
            System.out.println("✅ Auto-sauvegarde OK");
        } catch (Exception ex) {
            System.err.println("❌ Auto-sauvegarde échouée : " + ex.getMessage());
        }
    }

    private void mettreAJourCompteur() {
        ui.lblTotalContacts.setText("Total : " + contacts.size() + " contacts");
    }

    private void viderChamps() {
        ui.txtNom.setText("");
        ui.txtPrenom.setText("");
        ui.txtTelephone.setText("");
        ui.txtEmail.setText("");
    }

    private void message(String msg, String titre) {
        JOptionPane.showMessageDialog(ui, msg, titre, JOptionPane.INFORMATION_MESSAGE);
    }

    
}
