package se.emma.databas;

public class WorkRole {

    private int roleId;
    private String title;
    private String description;
    private double salary;
    private java.sql.Date creationDate;

    public WorkRole(int roleId, String title, String description, double salary, java.sql.Date creationDate){

        this.roleId = roleId;
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.creationDate = creationDate;
    }

    public WorkRole(String title, String description, double salary, java.sql.Date creationDate){

        this.title = title;
        this.description = description;
        this.salary = salary;
        this.creationDate = creationDate;
    }

    public int getRoleId() {
        return roleId;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public double getSalary(){
        return salary;
    }
    public java.sql.Date getCreationDate(){
        return creationDate;
    }

    public void setSalary(double salary){
        this.salary = salary;
    }

    public boolean equals (Object object){

        if (object == this){
            return true;
        }
        if (!(object instanceof WorkRole)){
            return false;
        }
        WorkRole otherObject = (WorkRole) object;

        return otherObject.getRoleId() == this.roleId;
    }

    @Override
    public int hashCode() {
        return this.roleId;
    }

    public String toString(){

        return "role id: " + roleId + " title: " + title + " description: " + description + " salary: " + salary + " creation date: " + creationDate + " \n";
    }
}
