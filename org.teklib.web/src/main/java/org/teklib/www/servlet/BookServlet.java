/* This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.teklib.www.servlet;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BookServlet extends HttpServlet {

	private static final long serialVersionUID = 2L;

	public static final String FILE = "image";
	public static final String FILENAME = "filename";
	public static final String FILE_MIME_TYPE = "fileMimeType";

	public static final String VIEW_FILE_EXPIRES = "Expires";
	public static final String VIEW_FILE_EXPIRES_DATE = "Sun, 17 Jan 2038 19:14:07 GMT";

	public void init() throws ServletException {
		super.init();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException {

		try {
			Map fileMap = ((Map) req.getAttribute(FILE));
			File file = new File((String)fileMap.get(FILENAME));

			resp.addHeader(VIEW_FILE_EXPIRES, VIEW_FILE_EXPIRES_DATE);
			resp.setContentType((String)fileMap.get(FILE_MIME_TYPE));
			resp.setContentLength((int)file.length());

			FileInputStream stream = new FileInputStream(file);
			byte[] bytes = new byte[512];
			int length = stream.read(bytes);
			OutputStream out = new BufferedOutputStream(resp.getOutputStream());
			while(length != -1) {
				out.write(bytes);
				length = stream.read(bytes);
			}
			out.flush();
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
