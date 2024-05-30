package in.jwt;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpServletRequest request;

    private ThreadLocal<Instant> threadLocal = new ThreadLocal<>();

    @Before("execution(* in.jwt.MyController.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        String username = getUsernameFromToken();
        if (!"invalid".equals(username)) {
            Instant enteringTime = Instant.now();
            System.out.println(enteringTime);
            threadLocal.set(enteringTime);
        }
    }

    @After("execution(* in.jwt.MyController.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        String username = getUsernameFromToken();
        if (!"invalid".equals(username)) {
            Instant exitingTime = Instant.now();
            System.out.println(exitingTime);
            Instant enteringTime = threadLocal.get();
            logSession(username, joinPoint, enteringTime, exitingTime);
        }
    }

    private void logSession(String username, JoinPoint joinPoint, Instant enteringTime, Instant exitingTime) {
        logger.info(username + " entering >> " + joinPoint.getSignature().getName() + " " + formatInstant(enteringTime) + " " + username + " is exiting >> " + joinPoint.getSignature().getName() + " at " + formatInstant(exitingTime) + " " + "Session Time: " + Duration.between(enteringTime, exitingTime).toMillis() + " ms");
        logToFile(username + " entering >> " + joinPoint.getSignature().getName() + " " +formatInstant(exitingTime) + username + " is exiting >> " + joinPoint.getSignature().getName() + " at " + formatInstant(exitingTime) + " " + "Session Time: " + Duration.between(enteringTime, exitingTime).toMillis() + " ms");
    }

    private String formatInstant(Instant instant) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.ofInstant(instant, ZoneOffset.UTC));
    }

    private void logToFile(String message) {
        try (FileWriter writer = new FileWriter("login_log_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".txt", true)) {
            String formattedMessage = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ": " + message + "\n";
            writer.write(formattedMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getUsernameFromToken() {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken = authorizationHeader.substring(7);
            return jwtUtil.extractUsername(jwtToken);
        }
        return "invalid";
    }
}
