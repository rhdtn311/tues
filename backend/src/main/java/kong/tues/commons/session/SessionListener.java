package kong.tues.commons.session;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

    // 세션 만료 12시간
    private final int sessionTime = 43200;

    public void sessionCreate(HttpSessionEvent sessionEvent) {
        sessionEvent.getSession().setMaxInactiveInterval(sessionTime);
    }

    public void sessionDestroyed(HttpSessionEvent sessionEvent) {

    }
}
