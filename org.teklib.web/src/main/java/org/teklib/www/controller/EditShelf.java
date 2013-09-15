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
import org.teklib.www.model.ShelfEditBean;
import org.teklib.www.service.TeklibService;
import org.teklib.www.validation.BookValidator;

import static org.teklib.www.Constants.FORM_ACTION_CANCEL;
import static org.teklib.www.Constants.FORM_ACTION_DELETE;
import static org.teklib.www.Constants.FORM_ACTION_SUBMIT;
import static org.teklib.www.Constants.REDIRECT_HOME;

@Controller
@RequestMapping("/editShelf")
@SessionAttributes("shelfEditBean")
public class EditShelf {
	
	private static Logger logger = Logger.getLogger(EditShelf.class.getName());
	
	@Autowired
	private TeklibService teklibService;
	@Autowired
	private BookValidator bookValidator;
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		logger.info("WebDataBinder:");
	}

	@RequestMapping(method = RequestMethod.GET)
	public void setupNewForm(@RequestParam(value="shelfName", required=true) String name, Model modell) {
		logger.info(new StringBuffer("EditShelf: name:").append(name).toString());
		
		ShelfEditBean shelfEditBean = teklibService.getShelfByName(name);
		modell.addAttribute(shelfEditBean);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute ShelfEditBean shelfEditBean, BindingResult result, SessionStatus status, String formAction) {
		logger.info(new StringBuffer("process submit:").append(shelfEditBean)
				.append("\n\tBindResult:").append(result)
				.append("\n\tSessionState:").append(status)
				.append("\n\tFormAction:").append(formAction).toString());
		
		if(formAction != null && formAction.equals(FORM_ACTION_SUBMIT)) {
			bookValidator.validate(shelfEditBean, result);
			if (result.hasErrors()) {
				return "editShelf";
			} else {
				this.teklibService.storeShelf(shelfEditBean);
				status.setComplete();
				return REDIRECT_HOME;
			}
		} else if(formAction != null && formAction.equals(FORM_ACTION_DELETE)) {
			this.teklibService.deleteShelf(shelfEditBean);
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
