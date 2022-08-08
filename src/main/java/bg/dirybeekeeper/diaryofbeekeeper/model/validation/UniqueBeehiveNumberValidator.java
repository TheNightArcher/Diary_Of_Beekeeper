package bg.dirybeekeeper.diaryofbeekeeper.model.validation;

import bg.dirybeekeeper.diaryofbeekeeper.repository.BeehiveRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueBeehiveNumberValidator implements ConstraintValidator<UniqueBeehiveNumber,Integer> {
    private BeehiveRepository beehiveRepository;

    public UniqueBeehiveNumberValidator(BeehiveRepository beehiveRepository) {
        this.beehiveRepository = beehiveRepository;
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return beehiveRepository
                .findByCurrentNumber(value)
                .isEmpty();
    }
}
