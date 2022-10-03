package com.ll.exam.app10.app.member.entity;


import com.ll.exam.app10.app.base.AppConfig;
import com.ll.exam.app10.app.base.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.File;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Member extends BaseEntity {
    // @Column(unique=true) 은 해당 컬럼에 한정 유니크를 설정할 수 있으나 여러 컬럼을 유니크 설정할 수 없다.
    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    private String profileImg;

    public void removeProfileImgOnStorage() {
        if (profileImg == null || profileImg.trim().length() == 0) return;

        String profileImgPath = getProfileImgPath();

        new File(profileImgPath).delete();
    }

    private String getProfileImgPath() {
        return AppConfig.GET_FILE_DIR_PATH + "/" + profileImg;
    }
}
