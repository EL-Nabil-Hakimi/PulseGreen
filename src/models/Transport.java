package models;
import enums.ConsumptionType;

import java.time.LocalDate;

public class Transport extends Consumption{

    private String name ;
    private float impact;
    private float parcours;

    public Transport(LocalDate startDate, LocalDate endDate, float consumption, ConsumptionType type, String name, float impact, float parcourue) {
        super(startDate, endDate, consumption, type);
        this.name = name;
        this.impact = impact;
        this.parcours = parcourue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getImpact() {
        return impact;
    }

    public void setImpact(float impact) {
        this.impact = impact;
    }

    public float getParcourue() {
        return parcours;
    }

    public void setParcourue(float parcourue) {
        this.parcours = parcourue;
    }

    @Override
    public String toString() {
        return  "Date Debut =" + getStartDate() +
                ", Date Fin =" + getEndDate() +
                ", Consommation =" + getConsumption() +
                ", Tipe de consommation=" + getType() +
                ", Name ='" + name + '\'' +
                ", Impact=" + impact +
                ", Distance parcoure =" + getParcourue() +
                " Calcul Impact = " + calculerImpact() +
                " Kg/co²" ;
    }

    @Override
    public float calculerImpact() {
        return parcours * impact * getConsumption();
    }

}
