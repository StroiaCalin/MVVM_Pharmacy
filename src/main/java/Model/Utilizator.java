package Model;

public class Utilizator {
    private int idClienti;
    private String username;
    private String password;
    private String role;
    private String farmacie;
    public Utilizator() {
        // Default constructor
    }
    public Utilizator(String username, String password, String role, String farmacie) {
        this(-1, username, password, role,farmacie);
    }
    
    public Utilizator(int idClienti, String username, String password, String role, String farmacie) {
        this.idClienti = idClienti;
        this.username = username;
        this.password = password;
        this.role = role;
        this.farmacie= farmacie;
    }

    public int getIdClienti() {
        return idClienti;
    }

    public void setIdClienti(int idClienti) {
        this.idClienti = idClienti;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String nume) {
        this.username = nume;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFarmacie() {
        return farmacie;
    }

    public void setFarmacie(String farmacie) {
        this.farmacie = farmacie;
    }

    @Override
    public String toString() {
        return "Clienti{" +
                "id=" + idClienti +
                ", username=" + username  +
                ", password=" + password +
                 ", role=" + role +
                ", farmacie=" + farmacie +
                '}';
    }

}