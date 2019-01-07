package ee.valiit.demo.demo.model.weather;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "weather")
@Data
<<<<<<< HEAD
@SequenceGenerator(name = "id_sequence2", sequenceName ="weather_id_seq", allocationSize=1)
=======
@SequenceGenerator(name = "id_sequence2", sequenceName ="weather_id_seq" )
>>>>>>> origin/master
public class Weather {
    @Id
    @Column(name ="id", nullable = false, unique = true)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_sequence2")
<<<<<<< HEAD
    @SequenceGenerator(name = "id_sequence2", sequenceName ="weather_id_seq", allocationSize=1)
=======
    @SequenceGenerator(name = "id_sequence2", sequenceName ="weather_id_seq" )
>>>>>>> origin/master
    private Integer id;
    //@OneToMany(mappedBy = "id")
    private Integer cityId;
    private LocalDateTime dateTime;
    private Double temp;
<<<<<<< HEAD
=======
    //private String generalDescription;
>>>>>>> origin/master
    private String description;
    private Double windSpeed;
    private Integer humidity;


}
