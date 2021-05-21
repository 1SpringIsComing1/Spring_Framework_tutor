package maksym.kruhovykh.app.repository.entity;

import lombok.*;

import javax.persistence.*;

@Table(name = "type_of_car")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class TypeOfCar {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column
    private String type;

    @Column
    private Double transportationPricePerKm;

    public TypeOfCar(String type) {
        this.type = type;
    }

    public String getRole() {
        return type.toUpperCase();
    }
}
