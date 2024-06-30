package goit.hw.filter;

import goit.hw.time.TimeManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(value = "/time/*")
public class TimezoneValidateFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {

        String timeZone = req.getParameter("timezone");
        if (timeZone == null) timeZone = "UTC";
        timeZone = timeZone.replace(" ", "+");

        if (TimeManager.isTimeZoneValid(timeZone)) {
            chain.doFilter(req, resp);
        } else {
            resp.setStatus(400);

            resp.setContentType("application/json");
            resp.getWriter().write("{\"Error\": \"Invalid timezone\"}");
            resp.getWriter().close();
        }
    }

}
