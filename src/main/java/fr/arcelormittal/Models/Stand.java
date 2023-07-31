package fr.arcelormittal.Models;

public class Stand {

    private int id;
    private boolean active;

    public Stand(int id, boolean active){
        this.id = id;
        this.active = active;
    }

    @Override
    public String toString(){
        return "Stand " + getId() + " | " + isActive();
    }

    public int getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
