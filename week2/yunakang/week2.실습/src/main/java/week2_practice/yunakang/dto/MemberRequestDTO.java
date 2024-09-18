package week2_practice.yunakang.dto;

/*import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberRequestDTO {
  private String name;
  private String email;
}*/

public record MemberRequestDTO(String name, String email) {
}