package third.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDTO {

    @NotBlank(message = "제목을 작성해 주세요.")
    @Size(max = 20, message = "제목은 최대 20글자까지 입력할 수 있습니다.")
    private String title;

    @Size(max = 100, message = "본문은 최대 100글자까지 입력할 수 있습니다.")
    private String content;

    @NotBlank(message = "멤버ID를 작성해 주세요.")
    private Long memberId;
}
