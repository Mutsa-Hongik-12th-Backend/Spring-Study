package likelion.week2Prac.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity     //entity 클래스임
@Builder    //객체 생성 패턴 - 빌더 패턴 사용
@NoArgsConstructor      // 파라미터가 없는 디폴트 생성자 자동 생성
@AllArgsConstructor     // 모든 필드 값을 파라미터로 받는 생성자 자동 생성
@Getter     // 멤버 필드의 getter 들 자동 생성 (이 경우 : getId, getName, getEmail)
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id     // PK 명시
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // 기본키 값 자동 생성
    @Column(name="member_id")       // 필드와 디비 컬럼 매핑
    
    private Long id;
    private String name;
    private String email;

    @CreatedDate
    private LocalDateTime createdAt;
}
