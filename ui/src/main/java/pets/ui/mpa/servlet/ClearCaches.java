package pets.ui.mpa.servlet;

import static pets.ui.mpa.util.ContextProvider.getApplicationContext;
import static java.util.Objects.requireNonNull;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;

@WebServlet(name = "ClearCaches", urlPatterns = { "/servlet/ClearCaches" })
public class ClearCaches extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ClearCaches.class);

	protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws ServletException, IOException {
		doPost(httpServletRequest, httpServletResponse);
	}

	protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws ServletException, IOException {
		CacheManager cacheManager = getApplicationContext().getBean(CacheManager.class);
		logger.info("Firing Clear Caches Servlet!!!");
        cacheManager.getCacheNames()
                .forEach(cacheName -> requireNonNull(cacheManager.getCache(cacheName)).clear());
        logger.info("Finished Clear Caches Servlet!!!");
	}
}
