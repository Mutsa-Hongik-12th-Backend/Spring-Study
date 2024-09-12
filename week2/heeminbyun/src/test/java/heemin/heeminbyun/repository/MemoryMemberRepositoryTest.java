package heemin.heeminbyun.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import heemin.heeminbyun.domain.Member;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository=new MemoryMemberRepository();

    // 테스트는 그 순서와 관계없이 서로 의존관계 없이 설계가 되어야 함.
    // 그러기 위해 하나의 테스트가 끝날 때마다 저장소나 공용 데이터들을 다시 깔끔하게 지워줘야 함.
    // test 끝날 때마다 메모리 비워주는 코드
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save(){

        Member member= new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result);

    }

    @Test
    public void findByName(){
        Member member1=new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2=new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result= repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);


    }

    @Test
    public void findAll(){
        Member member1=new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2=new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}