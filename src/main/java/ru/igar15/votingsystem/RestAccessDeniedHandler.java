package ru.igar15.votingsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import ru.igar15.votingsystem.util.exception.ErrorInfo;
import ru.igar15.votingsystem.web.json.JacksonObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.igar15.votingsystem.util.exception.ErrorType.ACCESS_DENIED_ERROR;
import static ru.igar15.votingsystem.web.AppExceptionHandler.EXCEPTION_ACCESS_DENIED;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ErrorInfo responseEntity = new ErrorInfo(request.getRequestURL(), ACCESS_DENIED_ERROR,
                ACCESS_DENIED_ERROR.getErrorCode(), EXCEPTION_ACCESS_DENIED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        ServletOutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = JacksonObjectMapper.getMapper();
        mapper.writeValue(outputStream, responseEntity);
        outputStream.flush();
    }
}
