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

import java.net.URLEncoder;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.teklib.lookup.LookupResult;
import org.teklib.lookup.LookupService;
import org.teklib.www.model.BookFileJVT;
import org.teklib.www.model.BookJVT;
import org.teklib.www.service.TeklibService;

@Controller
public class JsonRpcController {

	private static Logger logger = Logger.getLogger(JsonRpcController.class.getName());
	
	private String cleanLookupString(String isbn) {
		Matcher matcher = Pattern.compile("-").matcher(isbn);
	    String output = matcher.replaceAll("");
		matcher = Pattern.compile(" ").matcher(output);
	    return matcher.replaceAll("");
	}

	@Autowired
	private LookupService lookupService;

	@Autowired
	private TeklibService teklibService;

    @RequestMapping(value = "/publisher", method = RequestMethod.GET)
    public @ResponseBody Collection<Map<String, String>> publisher() {
        return teklibService.getPublisherList();
    }
    @RequestMapping(value = "/shelf", method = RequestMethod.GET)
    public @ResponseBody Collection<Map<String, String>> shelf() {
        return teklibService.getShelfList();
    }
    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> book(@RequestParam int id) {
        BookJVT book = teklibService.getBookById(id);

        Map<String, Object> b = new HashMap<String, Object>();
        b.put("id", Integer.toString(book.getId()));
        b.put("coverId", Integer.toString(book.getCoverImage()));
        b.put("name", book.getName());
        b.put("description", book.getDescription());
        b.put("author", book.getAuthor());
        b.put("isbn", book.getIsbn());
        b.put("releaseDate", book.getReleaseDate());
        b.put("description", book.getDescription());
        b.put("shelf", book.getShelf());
        b.put("publisher", book.getPublisher());
        b.put("coverImage", book.getCoverImage());
        Collection<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
        for(BookFileJVT f : book.getFiles()) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", f.getId());
            params.put("name", URLEncoder.encode(f.getFilename()));
            params.put("type", f.getFormat());
            files.add(params);
        }
        b.put("files", files);
        return b;
    }

    @RequestMapping(value = "/books/count", method = RequestMethod.GET)
    public @ResponseBody Integer booksCount(
            @RequestParam String shelf, @RequestParam String publisher, @RequestParam String search) {

        if(logger.isLoggable(Level.INFO)) {
            logger.log(Level.INFO, new StringBuffer("JSON count books:")
                    .append(", Shelf:'").append(shelf)
                    .append("', publisher:'").append(publisher)
                    .append("', search:'").append(search).append("'").toString());
        }
        Map<String, String> cryteria = getCryteria(publisher, shelf, search);
        return teklibService.getSelectedBooks(cryteria);
    }
    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> books(
            @RequestParam String shelf, @RequestParam String publisher, @RequestParam String search,
            @RequestParam int index, @RequestParam int count) {

        if(logger.isLoggable(Level.INFO)) {
            logger.log(Level.INFO, new StringBuffer("JSON lookup books:").append(index).append("/").append(count)
                    .append(", Shelf:'").append(shelf)
                    .append("', publisher:'").append(publisher)
                    .append("', search:'").append(search).append("'").toString());
        }
        Map<String, String> cryteria = getCryteria(publisher, shelf, search);
        List<Map<String, String>> books = new ArrayList<Map<String, String>>();
        for(BookJVT book : teklibService.getBookList(cryteria, index, count)) {
            Map<String, String> b = new HashMap<String, String>();
            b.put("id", Integer.toString(book.getId()));
            b.put("coverId", Integer.toString(book.getCoverImage()));
            b.put("name", book.getName());
            books.add(b);
        }

        Map<String, Object> result = new HashMap<String, Object>();
//        result.put("page", page);
//        result.put("pages", teklibService.getPages(cryteria));
        result.put("count", teklibService.getSelectedBooks(cryteria));
        result.put("books", books);

        return result;

    }
    private Map<String, String> getCryteria(String publisher, String shelf, String search) {
        Map<String, String> cryteria = new HashMap<String, String>();
        if(publisher!=null && !publisher.trim().isEmpty()) {
            cryteria.put("publisher", publisher);
        }
        if(shelf!=null && !shelf.trim().isEmpty()) {
            cryteria.put("shelf", shelf);
        }
        if(search!=null && !search.trim().isEmpty()) {
            cryteria.put("search", search);
        }
        return cryteria;
    }

    @Deprecated
    @RequestMapping(value = "/books.json", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> booksJson(@RequestParam String shelf, @RequestParam String publisher, @RequestParam String search, @RequestParam int page) {

		if(logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, new StringBuffer("JSON lookup books:").append(page)
                    .append(", Shelf:'").append(shelf)
                    .append("', publisher:'").append(publisher)
                    .append("', search:'").append(search).append("'").toString());
        }
		
		Map<String, String> cryteria = new HashMap<String, String>();
		if(publisher!=null && !publisher.trim().isEmpty()) {
			cryteria.put("publisher", publisher);
		}
		if(shelf!=null && !shelf.trim().isEmpty()) {
			cryteria.put("shelf", shelf);
		}
		if(search!=null && !search.trim().isEmpty()) {
			cryteria.put("search", search);
		}

		List<Map<String, String>> books = new ArrayList<Map<String, String>>();
		for(BookJVT book : teklibService.booksHandler(cryteria, page)) {
			Map<String, String> b = new HashMap<String, String>();
			b.put("id", Integer.toString(book.getId()));
			b.put("coverId", Integer.toString(book.getCoverImage()));
			b.put("name", book.getName());
			books.add(b);
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("page", page);
		result.put("pages", teklibService.getPages(cryteria));
		result.put("selected", teklibService.getSelectedBooks(cryteria));
		result.put("books", books);

		return result;
	}
	@RequestMapping(value = "/lookup.json", method = RequestMethod.GET)
    public @ResponseBody Map<String, String> jsonHandler(@RequestParam String isbn) {
		if(logger.isLoggable(Level.FINE)) {
			logger.fine(new StringBuffer("JSON lookup:").append(isbn).toString());
		}

		LookupResult result = lookupService.lookup(cleanLookupString(isbn));

		Map<String, String> res = new HashMap<String, String>();
		res.put("publisher", result.getPublisher());
		res.put("name", result.getName());
		res.put("description", result.getDescription());
		res.put("author", result.getAuthor());
		res.put("releaseDate", result.getReleaseDate());
		res.put("imageUrl", result.getImageUrl());
		return res;
	}
}
