package pizzaapi.persitence.audit;

import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreRemove;
import org.springframework.util.SerializationUtils;
import pizzaapi.persitence.entity.PizzaEntity;

public class AuditPizzaListener {

    private PizzaEntity currentValue;

    @PostLoad
    public void postLoad(PizzaEntity entity) {
        System.out.println("POST LOAD");
        this.currentValue = SerializationUtils.clone(entity);
    }

    @PostPersist
    @PostUpdate
    public void onPostPersit(PizzaEntity pizzaEntity) {
        System.out.println("POST PERSIST OR UPDATE");
        System.out.println("OLD VALUE: " + this.currentValue.toString());
        System.out.println("NEW VALUE: " + pizzaEntity.toString());
    }

    @PreRemove
    public void onPreDelete(PizzaEntity pizza) {
        System.out.println(pizza.toString());
    }
}
