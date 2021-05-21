package maksym.kruhovykh.app.repository.entity;

import lombok.*;

import javax.persistence.*;

@Table(name = "car")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @OneToOne
    private Brand brand;

    @OneToOne
    private TypeOfCar type;

}
