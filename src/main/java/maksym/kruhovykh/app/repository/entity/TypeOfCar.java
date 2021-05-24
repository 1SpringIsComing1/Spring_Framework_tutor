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
@ToString
public class TypeOfCar {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column
    private String name;

    @Column
    private Double transportationPricePerKm;

    public TypeOfCar(String name) {
        this.name = name;
    }

    public String getRole() {
        return name.toUpperCase();
    }
}
