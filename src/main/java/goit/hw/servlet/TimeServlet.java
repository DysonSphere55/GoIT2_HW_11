package goit.hw.servlet;

import goit.hw.time.TimeManager;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    private TemplateEngine engine;

    @Override
    public void init(ServletConfig config) throws ServletException {
        engine = new TemplateEngine();

        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix("./templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String timeZone = req.getParameter("timezone");
        if (timeZone == null) {
            timeZone = getTimeZoneCookie(req);
        }
        timeZone = timeZone.replace(" ", "+");

        resp.setContentType("text/html");
        resp.addCookie(new Cookie("timeZone", timeZone));

        Map<String, Object> map = new HashMap<>();
        map.put("timeZone", timeZone);
        map.put("time", TimeManager.getDateTime(timeZone));

        Context context = new Context(
                req.getLocale(),
                map
        );

        engine.process(
                "index",
                context,
                resp.getWriter()
        );

        resp.getWriter().close();
    }

    private static String getTimeZoneCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        Cookie timeZoneCookie = Arrays.stream(cookies)
                .filter(it -> it.getName().equals("timeZone"))
                .findFirst()
                .orElse(new Cookie("timeZone", "UTC"));
        return timeZoneCookie.getValue();
    }
}
