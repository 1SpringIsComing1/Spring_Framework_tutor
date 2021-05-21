package maksym.kruhovykh.app.repository.entity;

import lombok.*;

import javax.persistence.*;

@Table(name = "brand")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column
    private String brand;

    public Brand(String brand) {
        this.brand = brand;
    }

    public String getRole() {
        return brand;

    }
}
