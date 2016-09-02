package com.xinma.base.datastore.ext.message;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xinma.base.datastore.enums.PortalDownloadMessageType;
import com.xinma.base.datastore.impl.aliyun.message.MessageServiceImpl;
import com.xinma.base.datastore.impl.aliyun.table.DataStoreBaseTest;
import com.xinma.base.datastore.model.message.ProductTO;
import com.xinma.base.datastore.model.message.QueueMessageTO;

public class MessageServiceTest extends DataStoreBaseTest {

	private MessageService messageService = new MessageServiceImpl(mnsClient);

	private ObjectMapper mapper = new ObjectMapper();

	@Test
	public void batchDeleteMessage() {
		// throw new RuntimeException("Test not implemented");
	}

	@Test
	public void batchPutMessage() {
		// throw new RuntimeException("Test not implemented");
	}

	@Test
	public void deleteMessage() {
		// throw new RuntimeException("Test not implemented");
	}

	@Test
	public void popMessage() throws JsonProcessingException {
		QueueMessageTO message = messageService.popMessage("backend-portal", false);
		System.out.println(mapper.writeValueAsString(message));
	}

	@Test
	public void putMessage() throws JsonProcessingException {
		QueueMessageTO queueMessage = new QueueMessageTO();
		ProductTO product = new ProductTO();
		product.setCode("product-001");
		product.setEseId(1);
		product.setEseName("测试企业");
		product.setInfo("");
		product.setName("测试产品");
		product.setOid(1);
		product.setPrice(1f);

		queueMessage.setMessageBody(mapper.writeValueAsString(product));
		queueMessage.setMessageType(PortalDownloadMessageType.ProductTO.getValue());

		messageService.putMessage("backend-portal", queueMessage);
		// throw new RuntimeException("Test not implemented");
	}
}
