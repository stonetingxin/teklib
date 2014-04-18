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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.teklib.lookup.LookupResult;
import org.teklib.lookup.LookupService;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Lookup the book by ISBN using the a2s Amazon REST Service.
 */
public class LookupFacadeImpl implements LookupService {
	
	private static Logger logger = Logger.getLogger(LookupFacadeImpl.class.getName());

	private String awsAccessKeyId;
    /**
     * Your AWS Access Key ID, as taken from the AWS Your Account page.
     */
	public void setAwsAccessKeyId(String awsAccessKeyId) {
		this.awsAccessKeyId = awsAccessKeyId;
	}
	private String awsSecretKey;
    /**
     * Your AWS Secret Key corresponding to the above ID, as taken from the AWS
     * Your Account page.
     */
	public void setAwsSecretKey(String awsSecretKey) {
		this.awsSecretKey = awsSecretKey;
	}
	private String endpoint;
    /**
     * Use one of the following end-points, according to the region you are
     * interested in:
     * 
     *      US: ecs.amazonaws.com 
     *      CA: ecs.amazonaws.ca 
     *      UK: ecs.amazonaws.co.uk 
     *      DE: ecs.amazonaws.de 
     *      FR: ecs.amazonaws.fr 
     *      JP: ecs.amazonaws.jp
     */
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

    @Override
	public LookupResult lookup(String isbn) {
        SignedRequestsHelper helper;
        try {
            helper = SignedRequestsHelper.getInstance(endpoint, awsAccessKeyId, awsSecretKey);
        } catch (Exception e) {
        	throw new RuntimeException(e);
        }
        
        String requestUrl = null;

        /*
         * Here is an example in map form, where the request parameters are stored in a map.
         */
        Map<String, String> params = new HashMap<String, String>();
        params.put("Service", "AWSECommerceService");
        params.put("Version", "2009-03-31");
        params.put("Operation", "ItemSearch");
        params.put("SearchIndex", "Books");
        params.put("Keywords", isbn);
        params.put("ResponseGroup", "Large");
        params.put("AssociateTag", "AcmeBooks");

        requestUrl = helper.sign(params);
        logger.info("Signed Request is \"" + requestUrl + "\"");

        
        LookupResult result;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(requestUrl);
			result = fetchTitles(doc);
		} catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
		} catch (SAXException e) {
            throw new RuntimeException(e);
		} catch (IOException e) {
            throw new RuntimeException(e);
		}
        
        logger.info(result.toString());
        
        return result;
	}

    /*
     * Utility function to fetch the response from the service and extract the
     * title from the XML.
     */
    protected static LookupResult fetchTitles(Document doc) {
    	LookupResult result = new LookupResult();
        try {
            NodeList attributes = doc.getElementsByTagName("ItemAttributes");
            for (int i = 0; i < attributes.getLength(); i++) {
            	Element element = (Element) attributes.item(i);
            	if(element.getElementsByTagName("Author") != null && element.getElementsByTagName("Author").getLength() > 0) {
	            	result.setAuthor(
	            		((CharacterData)element.getElementsByTagName("Author").item(0).getFirstChild()).getData());
            	}
            	if(element.getElementsByTagName("ISBN") != null && element.getElementsByTagName("ISBN").getLength() > 0) {
	            	result.setIsbn(
	                	((CharacterData)element.getElementsByTagName("ISBN").item(0).getFirstChild()).getData());
            	}
            	if(element.getElementsByTagName("Title") != null && element.getElementsByTagName("Title").getLength() > 0) {
	            	result.setName(
	                    ((CharacterData)element.getElementsByTagName("Title").item(0).getFirstChild()).getData());
            	}
            	if(element.getElementsByTagName("Manufacturer") != null && element.getElementsByTagName("Manufacturer").getLength() > 0) {
	            	result.setPublisher(
	                    ((CharacterData)element.getElementsByTagName("Manufacturer").item(0).getFirstChild()).getData());
            	}
            	if(element.getElementsByTagName("PublicationDate") != null && element.getElementsByTagName("PublicationDate").getLength() > 0) {
	            	result.setReleaseDate(
	                    ((CharacterData)element.getElementsByTagName("PublicationDate").item(0).getFirstChild()).getData());
            	}
            }
            
            NodeList largeImage = doc.getElementsByTagName("LargeImage");
            for (int i = 0; i < largeImage.getLength(); i++) {
            	Element element = (Element) largeImage.item(i);
            	result.setImageUrl(
            		((CharacterData)element.getElementsByTagName("URL").item(0).getFirstChild()).getData());
                break;
            }
            
            NodeList editorialReview = doc.getElementsByTagName("EditorialReview");
            for (int i = 0; i < editorialReview.getLength(); i++) {
            	Element element = (Element) editorialReview.item(i);
            	result.setDescription(
            		((CharacterData)element.getElementsByTagName("Content").item(0).getFirstChild()).getData());
            }
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}