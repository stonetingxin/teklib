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

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.teklib.model.CoverImage;
import org.teklib.www.service.TeklibService;
import org.teklib.www.servlet.ImageServlet;

/**
 * Controller for the image static file.<p/>
 */
@Controller
@RequestMapping("/image")
public class Image {
	private static Logger logger = Logger.getLogger(Image.class.getName());

	@Autowired
	private TeklibService teklibService;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
    public String imageHandler(@PathVariable int id, Model modell) {
		if(logger.isLoggable(Level.INFO)) logger.info("Get Image: " + id);
    	modell.addAttribute(ImageServlet.IMAGE, getImageMap(teklibService.getImageById(id)));
    	return "image";
    }
	@SuppressWarnings("all")
	protected Map getImageMap(CoverImage cover) {
		Map image = new HashMap();
		image.put(ImageServlet.IMAGE_DATA, cover.getImage());
		image.put(ImageServlet.IMAGE_MIME_TYPE, cover.getMimeType());
		return image;
	}
	protected int getImageId(String in) {
		String sId = in.substring(in.lastIndexOf("/")+1, in.lastIndexOf("."));
		return(Integer.parseInt(sId.substring( sId.indexOf(",")+1, sId.length())));
	}
	protected int getImageType(String in) {
		String sId = in.substring(in.lastIndexOf("/")+1, in.lastIndexOf( "." ));
		return(Integer.parseInt(sId.substring( 0, sId.indexOf(","))));
	}
}
