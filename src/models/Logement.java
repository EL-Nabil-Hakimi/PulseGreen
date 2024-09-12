package models;

import enums.ConsumptionType;

import java.time.LocalDate;

public class Logement extends Consumption {

    private String name ;
    private float impact;
    private float consumptionEnergie;

    public Logement(LocalDate startDate, LocalDate endDate, float consumption, ConsumptionType type, String name, float impact, float consumptionEnergie) {
        super(startDate, endDate, consumption, type);
        this.name = name;
        this.impact = impact;
        this.consumptionEnergie = consumptionEnergie;
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

    public float getconsumptionEnergie() {
        return consumptionEnergie;
    }

    public void setconsumptionEnergie(float parcourue) {
        this.consumptionEnergie = parcourue;
    }

    @Override
    public String toString() {
        return  "Date Debut =" + getStartDate() +
                ", Date Fin =" + getEndDate() +
                ", Consommation =" + getConsumption() +
                ", Tipe de consommation=" + getType() +
                ", Name ='" + name + '\'' +
                ", Impact=" + impact +
                ", Consommation d'energie=" + consumptionEnergie +
                " Calcul Impact = " + calculerImpact() +
                " Kg/co²";
    }

    @Override
    public float calculerImpact() {
        return consumptionEnergie * impact * getConsumption();
    }

}
