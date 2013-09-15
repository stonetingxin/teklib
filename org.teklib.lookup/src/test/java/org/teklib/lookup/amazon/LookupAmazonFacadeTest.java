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
package org.teklib.lookup.amazon;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.teklib.lookup.LookupResult;
import org.w3c.dom.Document;

public class LookupAmazonFacadeTest {
	@Test
	public void Result01Test() throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(LookupAmazonFacadeTest.class.getResourceAsStream("/amazonresult1.xml"));
		LookupResult result = LookupFacadeImpl.fetchTitles(doc);
		
		assert(result.getAuthor().equals("Karl(Author) ; Chaffer, Jonathan(Author) Swedberg")) : "author:"+result.getAuthor();
		assert(result.getIsbn().equals("1847193811")) : "ISBN:"+result.getIsbn();
	}
}
