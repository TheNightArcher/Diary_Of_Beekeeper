package bg.dirybeekeeper.diaryofbeekeeper.model.entity;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity(name = "last_nutrition")
public class NutritionEntity extends BaseEntity{

    private LocalDate lastNutrition;

    public LocalDate getLastNutrition() {
        return lastNutrition;
    }

    public void setLastNutrition(LocalDate lastNutrition) {
        this.lastNutrition = lastNutrition;
    }
}
