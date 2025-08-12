package kr.kiomn2.newsletter.adapter.member.webapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.kiomn2.newsletter.MemberCommandFixture;
import kr.kiomn2.newsletter.adapter.persistence.config.EmbeddedRedisTestConfig;
import kr.kiomn2.newsletter.application.member.MemberCommandService;
import kr.kiomn2.newsletter.application.member.required.EmailSender;
import kr.kiomn2.newsletter.domain.member.Member;
import kr.kiomn2.newsletter.domain.member.MemberCommand;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@Import(EmbeddedRedisTestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest
class MemberApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberCommandService memberCommandService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Test
    void registerMemberApiRegisterMemberTest() throws Exception {
        MemberCommand.MemberRegister memberRegisterRequest
                = MemberCommandFixture.memberRegisterCommand("tess@test.com");


        mockMvc.perform(post("/api/member/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberRegisterRequest))
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.isError").value(false))
                .andExpect(jsonPath("$.body.id").exists())
                .andExpect(jsonPath("$.body.email").value("tess@test.com"));
    }

    @Test
    void registerMemberApiVerifyEmailTest() throws Exception {
        Member member = memberCommandService.registeMember(MemberCommandFixture.memberRegisterCommand("tess@test.com"));

        String verifyCode = redisTemplate.opsForValue().get("email_verification:" + member.getEmail());

        mockMvc.perform(post("/api/member/verify-email/{id}/{verifyCode}", member.getId(), verifyCode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isError").value(false))
                .andExpect(jsonPath("$.body.id").value(member.getId()))
                .andExpect(jsonPath("$.body.email").value("tess@test.com"))
        ;
    }
}