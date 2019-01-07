package ee.valiit.demo.demo.model.weather;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "weather")
@Data
@SequenceGenerator(name = "id_sequence2", sequenceName ="weather_id_seq", allocationSize=1)
public class Weather {
    @Id
    @Column(name ="id", nullable = false, unique = true)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_sequence2")
    @SequenceGenerator(name = "id_sequence2", sequenceName ="weather_id_seq", allocationSize=1)
    private Integer id;
    //@OneToMany(mappedBy = "id")
    private Integer cityId;
    private LocalDateTime dateTime;
    private Double temp;
    private String description;
    private Double windSpeed;
    private Integer humidity;


}
