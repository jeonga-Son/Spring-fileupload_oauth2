package com.ll.exam.app10.app.member.service;

import com.ll.exam.app10.app.member.entity.Member;
import com.ll.exam.app10.app.member.repository.MemberRepository;
import com.ll.exam.app10.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
// UserDetailsService는 Spring Security에서 유저의 정보를 가져오는 인터페이스이다.
// extends : 부모에서 선언 / 정의를 모두하며 자식은 메소드 / 변수를 그대로 사용할 수 있음
// implements (interface 구현) : 부모 객체는 선언만 하며 정의(내용)은 자식에서 오버라이딩 (재정의) 해서 사용해야함
public class MemberService implements UserDetailsService {
    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;
    private final MemberRepository memberRepository;

    public Member getMemberByUsername(String username) {
        return memberRepository.findByUsername(username).orElse(null);
    }

    private String getCurrentProfileImgDirName() {
        return "member/" + Util.date.getCurrentDateFormatted("yyyy_MM_dd");
    }

    public Member join(String username, String password, String email, MultipartFile profileImg) {
        // 여기서 Rel이란 상대경로를 말한다.
        // UUID.randomUUID()로 UUID 값을 생성할 수 있는데
        //생성 시에는 UUID 형태로 가져오기 때문에 toString(); 으로 String으로 바꿔준 후 사용해주면 된다.
        // 이후 출력을 해 보면 UUID 값이 정상적으로 나오는 것이 보이는데
        //출력을 할 때마다 다르게 나오므로 이 값을 고유 값으로 부여해주면 된다
        // UUID는 고유 값을 만드는 것이 전부이다. (파일을 계속 덮어쓰게 되지 않도록)
        String profileImgDirName = getCurrentProfileImgDirName();

        String ext = Util.file.getExt(profileImg.getOriginalFilename());

        String fileName = UUID.randomUUID() + "." + ext;
        String profileImgDirPath = genFileDirPath + "/" + profileImgDirName;
        String profileImgFilePath = profileImgDirPath + "/" + fileName;

        new File(profileImgDirPath).mkdirs(); // 폴더가 혹시나 없다면 만들어준다.

        try {
            profileImg.transferTo(new File(profileImgFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String profileImgRelPath = profileImgDirName + "/" + fileName;

        Member member = Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .profileImg(profileImgRelPath)
                .build();

        memberRepository.save(member);

        return member;
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByUsername(username).get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("member"));

        return new User(member.getUsername(), member.getPassword(), authorities);
    }

    public Member join(String username, String password, String email) {
        Member member = Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();

        memberRepository.save(member);

        return member;
    }

    public long count() {
        return  memberRepository.count();
    }

    public void removeProfileImg(Member member) {
        member.removeProfileImgOnStorage();  // 파일삭제
        member.setProfileImg(null);

        memberRepository.save(member);
    }

    public void setProfileImgByUrl(Member member, String url) {
        String filePath = Util.file.downloadImg(url, genFileDirPath + "/" + getCurrentProfileImgDirName() + "/" + UUID.randomUUID());
        member.setProfileImg(getCurrentProfileImgDirName() + "/" + new File(filePath).getName());
        memberRepository.save(member);
    }
}
