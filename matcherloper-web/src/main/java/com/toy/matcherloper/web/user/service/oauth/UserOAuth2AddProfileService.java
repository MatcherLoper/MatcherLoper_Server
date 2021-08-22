package com.toy.matcherloper.web.user.service.oauth;

import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.web.user.api.dto.request.OAuth2AddProfileRequest;
import com.toy.matcherloper.web.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.toy.matcherloper.web.utils.DtoConverter.*;

@Service
@RequiredArgsConstructor
public class UserOAuth2AddProfileService {

    private final UserFindService userFindService;

    @Transactional
    public Long addProfileOAuth2User(String userEmail, OAuth2AddProfileRequest oAuth2AddProfileRequest) {
        User user = userFindService.findByEmail(userEmail);
        user.addProfileFromOAuth2(
                oAuth2AddProfileRequest.getPhoneNumber(),
                oAuth2AddProfileRequest.getIntroduction(),
                toUserPositionSet(oAuth2AddProfileRequest.getUserPositionDtoList()),
                toSkillSet(oAuth2AddProfileRequest.getSkillDtoList()),
                toAddress(oAuth2AddProfileRequest.getAddressDto())
        );
        return user.getId();
    }
}
