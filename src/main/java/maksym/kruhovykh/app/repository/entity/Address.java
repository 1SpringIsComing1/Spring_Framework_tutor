package maksym.kruhovykh.app.repository.entity;

import lombok.*;

import javax.persistence.*;

@Table(name = "address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column
    private String name;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

}