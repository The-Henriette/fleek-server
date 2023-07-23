package run.fleek.domain.users;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.domain.users.dto.SignUpDto;
import run.fleek.enums.UserStatus;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "fleek_user", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class FleekUser implements SystemMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fleek_user_seq")
    @SequenceGenerator(name = "fleek_user_seq", sequenceName = "fleek_user_seq", allocationSize = 1)
    @Column(name = "fleek_user_id", nullable = false)
    private Long fleekUserId;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "user_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Column(name = "updated_at", nullable = false)
    private Long updatedAt;

    public static FleekUser of(String phoneNumber) {
        return FleekUser.builder()
                .phoneNumber(phoneNumber)
                .userStatus(UserStatus.ACTIVE)
                .build();
    }
}