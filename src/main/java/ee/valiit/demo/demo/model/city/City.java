package ee.valiit.demo.demo.model.city;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table (name = "city")
@Data
<<<<<<< HEAD
@SequenceGenerator(name = "id_sequence", sequenceName ="city_id_seq", allocationSize=1)
=======
@SequenceGenerator(name = "id_sequence", sequenceName ="city_id_seq" )
>>>>>>> origin/master
public class City {
    @Id
    @Column(name ="id", nullable = false, unique = true)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_sequence")
<<<<<<< HEAD
    @SequenceGenerator(name = "id_sequence", sequenceName ="city_id_seq", allocationSize=1)
=======
    @SequenceGenerator(name = "id_sequence", sequenceName ="city_id_seq" )
>>>>>>> origin/master
    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "city_id")
    private Integer id;
    @Column(unique = true)
    private String name;
}
