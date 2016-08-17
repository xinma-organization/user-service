package com.xinma.base.datastore.ext.table;

import java.math.BigInteger;
import java.util.Date;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xinma.base.datastore.impl.aliyun.table.DataStoreBaseTest;
import com.xinma.base.datastore.impl.aliyun.table.TagTableServiceImpl;
import com.xinma.base.datastore.model.tag.TagBasicInfoEO;
import com.xinma.base.tag.CloudTag;
import com.xinma.base.tag.TagBuilder;
import com.xinma.base.tag.TagConstants;

public class TagTableServiceTest extends DataStoreBaseTest {

	private TagTableService tagTableService;

	@BeforeClass
	public void init() {
		tagTableService = new TagTableServiceImpl(otsClient);
	}

	@Test
	public void addLotteryEnrolled() {
		// throw new RuntimeException("Test not implemented");
	}

	@Test
	public void addTagAccessLog() {
		// throw new RuntimeException("Test not implemented");
	}

	@Test
	public void addTagAward() {
		// throw new RuntimeException("Test not implemented");
	}

	@Test
	public void getTagAccessLogs() {
		// throw new RuntimeException("Test not implemented");
	}

	@Test
	public void getTagAwards() {
		// throw new RuntimeException("Test not implemented");
	}

	@Test(enabled = true)
	public void getTagBasicInfo() throws JsonProcessingException {
		TagBasicInfoEO tagBasicInfo = tagTableService.getTagBasicInfo(1010167820);
		System.out.println(new ObjectMapper().writeValueAsString(tagBasicInfo));
		// throw new RuntimeException("Test not implemented");
	}

	@Test
	public void getTagLotteryEnrolled() {
		// throw new RuntimeException("Test not implemented");
	}

	@Test
	public void getTagRow() {
		// throw new RuntimeException("Test not implemented");
	}

	@Test(enabled = false)
	public void putOrUpdateTagBasicInfo() {

		TagBasicInfoEO tagBasicInfo = new TagBasicInfoEO();
		tagBasicInfo.setOrganizationId(1);
		tagBasicInfo.setProductId(1);
		tagBasicInfo.setFactoryId(1);
		tagBasicInfo.setProduceTime(new Date());

		BigInteger startUniqueCode = BigInteger.valueOf(1010167820);
		long tagCount = 100;
		TagBuilder builder = new TagBuilder(TagConstants.CODE_VERSION_1, startUniqueCode, tagCount);

		CloudTag cloudTag = builder.nextCloudTag();
		while (cloudTag != null) {
			System.out.println(cloudTag.getHiddenCode());
			tagBasicInfo.setMeta(cloudTag);
			tagTableService.putOrUpdateTagBasicInfo(tagBasicInfo);

			cloudTag = builder.nextCloudTag();
		}
	}
}
