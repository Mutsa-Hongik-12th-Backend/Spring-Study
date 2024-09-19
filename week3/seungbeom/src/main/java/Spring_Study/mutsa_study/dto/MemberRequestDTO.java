package Spring_Study.mutsa_study.dto;

import Spring_Study.mutsa_study.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDTO {
    private String name;
    private String email;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .name(name)
                .build();
    }
}
