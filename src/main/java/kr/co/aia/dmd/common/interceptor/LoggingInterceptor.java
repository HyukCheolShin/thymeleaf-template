package kr.co.aia.dmd.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull Object handler)
            throws Exception {
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull Object handler, @Nullable Exception ex)
            throws Exception {
        Object startTimeAttr = request.getAttribute("startTime");
        if (startTimeAttr instanceof Long startTime) {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            log.info("[REQUEST] [{}] {} {}ms", request.getMethod(), request.getRequestURI(), duration);
        }
    }
}
