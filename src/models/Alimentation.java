package models;

import enums.ConsumptionType;
import java.time.LocalDate;

public class Alimentation extends Consumption {
    private String name;
    private float impact;
    private float poids;

    public Alimentation(LocalDate startDate, LocalDate endDate, float consumption, ConsumptionType type, String name, float impact, float poids) {
        super(startDate, endDate, consumption, type);
        this.name = name;
        this.impact = impact;
        this.poids = poids;
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

    public float getPoids() {
        return poids;
    }

    public void setPoids(float poids) {
        this.poids = poids;
    }

    @Override
    public String toString() {
        return  "Date Debut =" + getStartDate() +
                ", Date Fin =" + getEndDate() +
                ", Consommation =" + getConsumption() +
                ", Tipe de consommation=" + getType() +
                ", Name ='" + name  +
                ", Impact=" + impact +
                ", Poids =" + getPoids() +
                " Calcul Impact = " + calculerImpact() +
                " Kg/co²" ;
    }

    @Override
    public float calculerImpact() {
        return poids * impact * getConsumption();
    }
}
