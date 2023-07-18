package run.fleek.domain.users;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.domain.users.dto.SignUpDto;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "trace_user", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class FleekUser implements SystemMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trace_user_seq")
    @SequenceGenerator(name = "trace_user_seq", sequenceName = "trace_user_seq", allocationSize = 1)
    @Column(name = "fleek_user_id", nullable = false)
    private Long fleekUserId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Column(name = "updated_at", nullable = false)
    private Long updatedAt;

    public static FleekUser create(SignUpDto signUpDto) {
        return FleekUser.builder()
          .userName(signUpDto.getUserName())
          .password(signUpDto.getPassword())
          .build();
    }
}