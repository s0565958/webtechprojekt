package de.htwberlin.webtech.web.api;

public class Person {
    private long id;
    private String name;
    private String lastName;
    private boolean vaccinated;

    public Person(long id, String name, String lastName, boolean vaccinated) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.vaccinated = vaccinated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(boolean vaccinated) {
        this.vaccinated = vaccinated;
    }
}
