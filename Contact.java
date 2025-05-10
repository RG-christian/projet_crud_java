package model.l3.iirt;

public class Contact {
    private String nom, prenom, telephone, email;

    public Contact(String nom, String prenom, String telephone, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
    }

    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getTelephone() { return telephone; }
    public String getEmail() { return email; }

    public String toCSV() {
        return nom + "," + prenom + "," + telephone + "," + email;
    }

    @Override
    public String toString() {
        return "Nom: " + nom + ", Prénom: " + prenom + ", Tel: " + telephone + ", Email: " + email;
    }
}
