package org.b3log.solo.processor.renderer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.renderer.AbstractHTTPResponseRenderer;
import org.b3log.latke.servlet.renderer.HTTP404Renderer;
/**
 * 
 * @author Rocky.Wang
 *
 */
public final class PlainTextRender extends AbstractHTTPResponseRenderer{
	
	/**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(PlainTextRender.class.getName());
    
    private String plainText;
    
	public String getPlainText() {
		return plainText;
	}



	public void setPlainText(String plainText) {
		this.plainText = plainText;
	}



	@Override
	public void render(HTTPRequestContext context) {
        final HttpServletResponse response = context.getResponse();
        response.setCharacterEncoding("UTF-8");

        try {
            final PrintWriter writer = response.getWriter();
            response.setContentType("text/plain");
            writer.println(plainText);

            writer.close();
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "PlainText renders error", e);

            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (final IOException ex) {
                LOGGER.log(Level.ERROR, "Can not send error 500!", ex);
            }
        }
        
        
	}

}
