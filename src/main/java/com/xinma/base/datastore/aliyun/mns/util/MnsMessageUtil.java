package com.xinma.base.datastore.aliyun.mns.util;

import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.xinma.base.datastore.model.QueueMessageDTO;
import com.xinma.base.util.JacksonUtils;

/**
 * 阿里云MNS消息处理工具类
 * 
 * @author zhangyongyi
 *
 */
public class MnsMessageUtil {

	/**
	 * 发送MNS消息,MNSClient为null或queue不存在时抛出RuntimeException异常
	 * 
	 * @param mnsClient
	 *            MNS客户端
	 * @param msgDTO
	 *            消息体对象
	 * @param queueName
	 *            MNS队列名称
	 */
	public static void sendMessage(final MNSClient mnsClient, final QueueMessageDTO msgDTO, final String queueName) {
		if (mnsClient == null) {
			throw new RuntimeException("MNSClient为null");
		} else {
			CloudQueue promotionQueue = mnsClient.getQueueRef(queueName);
			if (promotionQueue == null) {
				throw new RuntimeException("获取名为" + queueName + "的Queue失败");
			} else {
				// 发送消息
				// TODO use one object
				String content = JacksonUtils.write2JsonString(msgDTO);
				Message message = new Message();
				message.setMessageBody(content);
				promotionQueue.putMessage(message);
			}
		}
	}
}
