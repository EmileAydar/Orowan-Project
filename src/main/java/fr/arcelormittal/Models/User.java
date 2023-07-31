package fr.arcelormittal.Models;

public class User {

    private int id;
    private String name, email, password;
    private String role;

    public User(int id, String name, String email, String password, int roleToken){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        if (roleToken == 1) this.role = "Administrateur";
        if (roleToken == 2) this.role = "Worker";
        if (roleToken == 3) this.role = "Process Engineer";
    }

    @Override
    public String toString() {
        return "User : {ID : " + id +
                " | Name : " + name +
                " | E-Mail : " + email +
                " | Password : " + password +
                " | Rôle : " + role + " }";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setRole(String role){
        this.role = role;
    }
}
