package edu.ssafy.enjoytrip.service.user;

import edu.ssafy.enjoytrip.dto.user.CustomUserDetails;
import edu.ssafy.enjoytrip.dto.user.User;
import edu.ssafy.enjoytrip.mapper.UserMapper;
import edu.ssafy.enjoytrip.response.code.CommonResponseCode;
import edu.ssafy.enjoytrip.response.code.CustomResponseCode;
import edu.ssafy.enjoytrip.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userMapper.findById(userId)
                .orElseThrow(() -> new RestApiException(CommonResponseCode.UNAUTHORIZED_REQUEST));
        return CustomUserDetails.builder()
                .user(user)
                .build();
    }
}
