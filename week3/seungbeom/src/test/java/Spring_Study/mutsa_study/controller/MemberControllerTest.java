package Spring_Study.mutsa_study.controller;

import Spring_Study.mutsa_study.domain.Member;
import Spring_Study.mutsa_study.dto.MemberRequestDTO;
import Spring_Study.mutsa_study.repository.MemberRepository;
import Spring_Study.mutsa_study.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {
    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    @BeforeEach
    public void mockMvcSetup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
        memberRepository.deleteAll();
    }

    @Test
    public void addMember() throws Exception {
        final String url = "/members";
        final String name = "박승범";
        final String email = "psb3707@naver.com";
        final MemberRequestDTO requestDTO = new MemberRequestDTO(name, email);

        final String requestBody = objectMapper.writeValueAsString(requestDTO);
        ResultActions resultActions = mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions.andExpect(status().isCreated());
        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isEqualTo(1);
        assertThat(members.get(0).getName()).isEqualTo(name);
        assertThat(members.get(0).getEmail()).isEqualTo(email);
    }

    @DisplayName("getMembers() : 멤버 전체 조회에 성공한다.")
    @Test
    public void getMembers() throws Exception {
        final String url = "/members";

        memberService.createMember(new MemberRequestDTO("박승범", "psb3707@naver.com"));
        memberService.createMember(new MemberRequestDTO("박준범", "pjb3707@naver.com"));

        ResultActions resultActions = mvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON_VALUE));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("박승범"))
                .andExpect(jsonPath("$[0].email").value("psb3707@naver.com"))
                .andExpect(jsonPath("$[1].name").value("박준범"))
                .andExpect(jsonPath("$[1].email").value("pjb3707@naver.com"));
    }

    @DisplayName("getMember() : 멤버 단건 조회에 성공한다.")
    @Test
    public void getMember() throws Exception {
        final String url = "/members/{id}";

        Member member = memberService.createMember(new MemberRequestDTO("박승범", "psb3707@naver.com"));

        ResultActions result = mvc.perform(get(url, member.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("psb3707@naver.com"))
                .andExpect(jsonPath("$.name").value("박승범"));
    }
}