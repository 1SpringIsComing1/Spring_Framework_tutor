package maksym.kruhovykh.app.repository.entity;

import lombok.*;

import javax.persistence.*;

@Table(name = "car")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Builder
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    Integer maxCountOfPlaces;

    @Column
    Integer currentCountOfPlaces;

    @OneToOne
    private Brand brand;

    @OneToOne
    private TypeOfCar typeOfCar;

}
