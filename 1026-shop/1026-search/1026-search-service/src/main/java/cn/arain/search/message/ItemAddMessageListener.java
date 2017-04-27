package cn.arain.search.message;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import cn.arain.search.SearchService;
/**
 * 
* <p>Title:ItemAddMessageListener </p>
* @author Arain
* @date2017年4月24日
 */
public class ItemAddMessageListener implements MessageListener{
	
	@Autowired
	private SearchService searchService;
	
	@Override
	public void onMessage(Message message) {
		
		try {
			TextMessage textMessage = (TextMessage) message;
			
			String id = textMessage.getText();
			System.out.println(id+"----------------");
			Thread.sleep(1000);
			
			searchService.itemAddSolr(Long.valueOf(id));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
