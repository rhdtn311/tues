package kong.tues.commons.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import kong.tues.member.SessionConst;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        log.info("로그인 사용자 인증 체크 : {}", requestURI);

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("비로그인 사용자 요청");

            response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods","*");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "*");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            response.setStatus(406);
            response.getWriter().write(new ObjectMapper().writeValueAsString(new LoginFailUserRes()));
            return false;
        }

        return true;
    }

    @Data
    private class LoginFailUserRes {
        private final boolean isLogin = false;
        private final String message = "아이디와 비밀번호를 확인해주세요.";
    }
}
