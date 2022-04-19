package src.kinosaalihaldur2;
import java.time.LocalDateTime;

public class Interval {
    private LocalDateTime algus;
    private LocalDateTime lopp;

    public Interval(LocalDateTime algus, LocalDateTime lopp) {
        this.algus = algus;
        this.lopp = lopp;
    }

    public boolean overlaps(Interval interval){
        if(this.algus.isAfter(interval.getAlgus()) && this.algus.isBefore(interval.getLopp())){
            return true;
        }
        else return this.lopp.isBefore(interval.getLopp()) && this.lopp.isAfter(interval.getAlgus());
    }

    public LocalDateTime getAlgus() {
        return algus;
    }

    public LocalDateTime getLopp() {
        return lopp;
    }
}
