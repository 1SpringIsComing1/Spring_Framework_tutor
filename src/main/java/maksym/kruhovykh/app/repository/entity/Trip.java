package maksym.kruhovykh.app.repository.entity;

import lombok.*;
import maksym.kruhovykh.app.service.enumeration.Status;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "trip")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Builder
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer distance;

    @OneToOne
    private Location departure;

    @OneToOne
    private Location arrival;

    @Column
    private Double price;

    @Column
    private LocalDateTime departureTime;

    @Column
    private LocalDateTime arrivalTime;

    @OneToOne
    private Driver driver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @Column(columnDefinition = "boolean default true")
    private Boolean isShowed;

}
