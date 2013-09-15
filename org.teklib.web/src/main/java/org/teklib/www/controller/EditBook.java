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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.teklib.www.model.BookEditBean;
import org.teklib.www.model.BookFileJVT;
import org.teklib.www.model.BookJVT;
import org.teklib.www.service.TeklibService;
import org.teklib.www.validation.BookValidator;

import static org.teklib.www.Constants.FORM_ACTION_CANCEL;
import static org.teklib.www.Constants.FORM_ACTION_DELETE;
import static org.teklib.www.Constants.FORM_ACTION_SUBMIT;
import static org.teklib.www.Constants.REDIRECT_HOME;

@Controller
@RequestMapping("/edit")
@SessionAttributes("bookEditBean")
public class EditBook {
	
	private static Logger logger = Logger.getLogger(EditBook.class.getName());
	
	@Autowired
	private TeklibService teklibService;
	@Autowired
	private BookValidator bookValidator;
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		logger.info("WebDataBinder:");
	}

	@RequestMapping(method = RequestMethod.GET)
	public void setupNewForm(@RequestParam(value="filename", required=false) String file, @RequestParam(value="bookId", required=false) Integer id, Model modell) {
		
		logger.info(new StringBuffer("EditBook: file:").append(file).append(", id:").append(id).toString());
		
		BookEditBean bookEditBean = new BookEditBean();

        List<String> publisherList = new ArrayList<String>();
        List<String> shelfList = new ArrayList<String>();
        for(Map<String, String> publisherItem : teklibService.getPublisherList()) {
            publisherList.add(publisherItem.get("text"));
        }
        for(Map<String, String> shelfItem : teklibService.getShelfList()) {
            shelfList.add(shelfItem.get("text"));
        }
		bookEditBean.setPublisherList(publisherList);
		bookEditBean.setShelfList(shelfList);

		if(file != null) {
			List<String> files = new ArrayList<String>();
			files.add(file);
			bookEditBean.setFiles(files);
		} else if(id != null) {
			BookJVT book = teklibService.getBookById(id);
			bookEditBean.setAuthor(book.getAuthor());
			bookEditBean.setCoverId(book.getCoverImage());
			bookEditBean.setDescription(book.getDescription());
			List<String> files = new ArrayList<String>();
			for(BookFileJVT bookFile : book.getFiles()) {
				files.add(bookFile.getFilename());
			}
			bookEditBean.setFiles(files);
			bookEditBean.setId(book.getId());
			bookEditBean.setIsbn(book.getIsbn());
			bookEditBean.setName(book.getName());
			bookEditBean.setPublisher(book.getPublisher());
			bookEditBean.setReleaseDate(book.getReleaseDate());
			bookEditBean.setShelf(book.getShelf());
		} else {
			logger.warning("no file or book id set.");
		}
		modell.addAttribute(bookEditBean);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute BookEditBean bookEditBean, BindingResult result, SessionStatus status, String formAction) {
		logger.info(new StringBuffer("process submit:").append(bookEditBean)
				.append("\n\tBindResult:").append(result)
				.append("\n\tSessionState:").append(status)
				.append("\n\tFormAction:").append(formAction).toString());
		
		if(formAction != null && formAction.equals(FORM_ACTION_SUBMIT)) {
			bookValidator.validate(bookEditBean, result);
			if (result.hasErrors()) {
				return "edit";
			} else {
				this.teklibService.storeBook(bookEditBean);
				status.setComplete();
				return REDIRECT_HOME;
			}
		} else if(formAction != null && formAction.equals(FORM_ACTION_DELETE)) {
			this.teklibService.deleteBook(bookEditBean);
			status.setComplete();
			return REDIRECT_HOME;
        } else if(formAction != null && formAction.equals(FORM_ACTION_CANCEL)) {
            status.setComplete();
            return REDIRECT_HOME;
        } else {
			throw new RuntimeException("unknown form action:"+formAction);
		}
	}
}
