package maksym.kruhovykh.app.repository.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "`order`")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private Integer distance;

    @OneToOne
    private Address departures;

    @OneToOne
    private Address arrivals;

    @Column
    private Double price;

    @Column
    private LocalDateTime dateCreation;

    @OneToOne
    private Driver driver;

    @OneToOne
    private Client client;

}