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
package org.teklib.www.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.teklib.www.Constants;
import org.teklib.www.servlet.BookServlet;

/**
 * Controller for the book static file.<p/>
 */
@Controller
@RequestMapping("/bookFile")
public class BookFile {
	
	private static Logger logger = Logger.getLogger(BookFile.class.getName());

	private String path;
	public void setPath(String path) {
		this.path = path;
	}

	@RequestMapping(method = RequestMethod.GET)
    public void bookHandler(@RequestParam("file") String filename, Model modell) {
    	modell.addAttribute(BookServlet.FILE, getFileMap( filename ));
    }
	protected Map<String, String> getFileMap(String filename) {
    	Map<String, String> map = new HashMap<String, String>();
    	map.put(BookServlet.FILENAME, path+filename); //TODO
    	if(filename.endsWith(Constants.FILE_EXTENSION_PDF)) {
    		map.put(BookServlet.FILE_MIME_TYPE, Constants.MIME_TYPE_PDF);
    	} else if(filename.endsWith(Constants.FILE_EXTENSION_CHM)) {
    		map.put(BookServlet.FILE_MIME_TYPE, Constants.MIME_TYPE_MSHELP);
    	} else {
    		map.put(BookServlet.FILE_MIME_TYPE, Constants.MIME_TYPE_TEXT_PLAIN);
    	}
    	logger.info(new StringBuffer("getFile:").append(map).toString());
		return map;
	}
}
