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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.teklib.www.model.BookFileJVT;
import org.teklib.www.model.BookJVT;
import org.teklib.www.service.TeklibService;

/**
 * Controller class for Teklib web application.<p/>
 */
@Controller
public class TeklibController {

	private static Logger logger = Logger.getLogger(TeklibController.class.getName());
	
	private TeklibService teklibService;
	public void setTeklibService(TeklibService teklibService) {
		this.teklibService = teklibService;
	}
	private String path;
	public void setPath(String path) {
		this.path = path;
	}

    //@TODO remove
    @RequestMapping(value="/test.htm")
    public void testHandler() {
        logger.info("/test.htm");
    }

    @RequestMapping(value="/main.htm")
    public void bookListHandler() {
    	logger.info("/main.htm");
    }
    @RequestMapping("/uncategorized.htm")
    public ModelAndView uncategorizedHandler() {
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("files", teklibService.getUncategorizedBooks(path));
        return mav;
    }
	@RequestMapping(value="/book/{id}.htm", method = RequestMethod.GET)
    public ModelAndView bookHandler(@PathVariable int id) {
		if(logger.isLoggable(Level.INFO)) logger.info("Load Book: "+id);
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("book", teklibService.getBookById(id));
    	mav.setViewName("book");
        return mav;
    }
	@RequestMapping(value="/lost.htm", method = RequestMethod.GET)
    public ModelAndView lostHandler() {
		if(logger.isLoggable(Level.INFO)) logger.info("lost books");
		if (logger.isLoggable(Level.FINEST)) logger.finest("get lost books");
		List<BookJVT> missingList = new ArrayList<BookJVT>();
		List<BookJVT> books = teklibService.getBookList(new HashMap<String, String>(), 0, 9999);
		for(BookJVT book : books) {
			try {
				for(BookFileJVT bookFile : book.getFiles()) {
					File file = new File(path+"/"+bookFile.getFilename());
					if(!file.exists()) {
						missingList.add(book);
					}
				}
			} catch(Exception ex) {}
		}

    	ModelAndView mav = new ModelAndView();
    	mav.addObject("lost", missingList);
    	mav.setViewName("lost");
        return mav;
    }
	@RequestMapping(value="/publisher.htm", method = RequestMethod.GET)
    public ModelAndView publisherHandler() {
		if(logger.isLoggable(Level.INFO)) logger.info("publisher list");
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("publisherList", teklibService.getPublisherList());
    	mav.setViewName("publisher");
        return mav;
    }
	@RequestMapping(value="/shelf.htm", method = RequestMethod.GET)
    public ModelAndView shelfHandler() {
		if(logger.isLoggable(Level.INFO)) logger.info("shelf list");
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("shelfList", teklibService.getShelfList());
    	mav.setViewName("shelf");
        return mav;
    }
}
