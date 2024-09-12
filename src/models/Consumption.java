package models;
import enums.ConsumptionType;
import java.time.LocalDate;

public abstract class Consumption {
    private LocalDate startDate ;
    private LocalDate endDate ;
    private float consumption ;
    private ConsumptionType type;

    public Consumption(LocalDate endDate, LocalDate startDate, float consumption, ConsumptionType type) {
        this.endDate = endDate;
        this.startDate = startDate;
        this.consumption = consumption;
        this.type = type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public float getConsumption() {
        return consumption;
    }

    public void setConsumption(float consumption) {
        this.consumption = consumption;
    }

    public ConsumptionType getType() {
        return type;
    }

    public void setType(ConsumptionType type) {
        this.type = type;
    }

    public abstract float calculerImpact();

}
