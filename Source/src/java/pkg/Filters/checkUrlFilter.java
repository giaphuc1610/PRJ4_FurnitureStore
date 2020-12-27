package pkg.Filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "checkUrlFilter", urlPatterns = {"/faces/products.xhtml"})
public class checkUrlFilter implements Filter {

    private static final boolean debug = true;
    private FilterConfig filterConfig = null;

    private void doBeforeProcessing(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        log("checkUrlFilter:DoBeforeProcessing");
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        log("checkUrlFilter:DoAfterProcessing");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        List<String> pageSizeList = new ArrayList<String>();
        pageSizeList.add("6");
        pageSizeList.add("12");
        pageSizeList.add("18");
        pageSizeList.add("24");
        pageSizeList.add("-1");

        String category = request.getParameter("categoryID");
        String type = request.getParameter("typeID");
        String brand = request.getParameter("brandID");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String page = request.getParameter("page");
        String pageSize = request.getParameter("pageSize");

        if (((category != null && type != null && brand == null) || (brand != null && category == null && type == null) || (from != null && to != null)) && page != null && pageSize != null && pageSizeList
                .contains(pageSize)) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).sendRedirect("404.xhtml");
        }
    }

    public FilterConfig getFilterConfig() {
        return this.filterConfig;
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            log("checkUrlFilter:Initializing filter");
        }
    }

    @Override
    public String toString() {
        if (this.filterConfig == null) {
            return "checkUrlFilter()";
        }
        StringBuffer sb = new StringBuffer("checkUrlFilter(");
        sb.append(this.filterConfig);
        sb.append(")");
        return sb.toString();
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n");

                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>");
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception exception) {
            }
        } else {

            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception exception) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception exception) {
        }

        return stackTrace;
    }

    public void log(String msg) {
        this.filterConfig.getServletContext().log(msg);
    }
}
