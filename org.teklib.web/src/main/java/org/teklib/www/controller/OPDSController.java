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

import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.teklib.www.service.TeklibService;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class for Teklib web application.<p/>
 */
@Controller
public class OPDSController {

    private static Logger logger = Logger.getLogger(OPDSController.class.getName());

    @Autowired
    private TeklibService teklibService;

    @RequestMapping(value="/odps.xml", produces="application/xml; charset=utf-8")
    // @ ResponseBody
    public ModelAndView odpsHandler(@RequestParam(value = "shelf", required = false) String shelf, Model modell) {
        logger.info("/odps.xml : " + shelf);
        ModelAndView mav = new ModelAndView();
        if(shelf == null) {
            mav.addObject("shelfList", teklibService.getShelfList());
            mav.setViewName("odps");
        } else {
            Map<String, String> cryteria = new HashMap<String, String>();
            cryteria.put("shelf", shelf);
            mav.addObject("items", teklibService.getBookList(cryteria, 0, 0));
            mav.setViewName("odps");
        }
        return mav;
    }
}
