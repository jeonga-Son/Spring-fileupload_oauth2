package com.ll.exam.app10.app.member.service;

import com.ll.exam.app10.app.member.entity.Member;
import com.ll.exam.app10.app.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;
    private final MemberRepository memberRepository;

    public Member getMemberByUsername(String username) {
        return memberRepository.findByUsername(username).orElse(null);
    }

    public Member join(String username, String password, String email, MultipartFile profileImg) {
        // 여기서 Rel이란 상대경로를 말한다.
        // UUID.randomUUID()로 UUID 값을 생성할 수 있는데
        //생성 시에는 UUID 형태로 가져오기 때문에 toString(); 으로 String으로 바꿔준 후 사용해주면 된다.
        // 이후 출력을 해 보면 UUID 값이 정상적으로 나오는 것이 보이는데
        //출력을 할 때마다 다르게 나오므로 이 값을 고유 값으로 부여해주면 된다
        // UUID는 고유 값을 만드는 것이 전부이다. (파일을 계속 덮어쓰게 되지 않도록)
        String profileImgRelPath = "member/" + UUID.randomUUID().toString() + ".png";
        File profileImgFile = new File(genFileDirPath + "/" + profileImgRelPath);

        profileImgFile.mkdirs(); // 관련된 폴더가 혹시나 없다면 만들어준다.

        try {
            profileImg.transferTo(profileImgFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Member member = Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .profileImg(profileImgRelPath)
                .build();

        memberRepository.save(member);

        return member;
    }
}
