package ee.valiit.demo.demo.model;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.print.attribute.standard.DateTimeAtCreation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "person")
@Data
public class Person {




  @Id
  @Column(name ="id", nullable = false, unique = true)
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_sequence")
  @SequenceGenerator(name = "id_sequence", sequenceName ="person_id_seq" )
  private Integer id;
  //@Column(name = "first_name")
  private String firstName;
  //@Column(name = "last_name")
  private String lastName;
  @Column(name = "social_security_id", unique = true)
  @NotNull
  @Pattern(regexp = "^[1-8][0-9]{2}[0,1][0-9][0-3][0-9][0-9]{4}$")
  @Size(max = 11)
  private String socialSecurityId;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime modifiedAt;
  private String modifiedBy;

}
