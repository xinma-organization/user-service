package com.xinma.base.datastore.impl.aliyun.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ots.OTSClient;
import com.aliyun.openservices.ots.model.BatchWriteRowRequest;
import com.aliyun.openservices.ots.model.BatchWriteRowResult;
import com.aliyun.openservices.ots.model.BatchWriteRowResult.RowStatus;
import com.aliyun.openservices.ots.model.DeleteRowRequest;
import com.aliyun.openservices.ots.model.DeleteRowResult;
import com.aliyun.openservices.ots.model.GetRangeRequest;
import com.aliyun.openservices.ots.model.GetRangeResult;
import com.aliyun.openservices.ots.model.GetRowRequest;
import com.aliyun.openservices.ots.model.GetRowResult;
import com.aliyun.openservices.ots.model.PutRowRequest;
import com.aliyun.openservices.ots.model.PutRowResult;
import com.aliyun.openservices.ots.model.RangeRowQueryCriteria;
import com.aliyun.openservices.ots.model.RowDeleteChange;
import com.aliyun.openservices.ots.model.RowPutChange;
import com.aliyun.openservices.ots.model.RowUpdateChange;
import com.aliyun.openservices.ots.model.SingleRowQueryCriteria;
import com.aliyun.openservices.ots.model.UpdateRowRequest;
import com.aliyun.openservices.ots.model.UpdateRowResult;
import com.google.common.base.Preconditions;
import com.xinma.base.datastore.error.TableError;
import com.xinma.base.datastore.exceptions.TableException;

class OtsRowUtils {

	private static Logger logger = LoggerFactory.getLogger(OtsRowUtils.class);

	private final static int allowRetryCnt = 5;

	private static void exceptionCheck(Exception e, int retryCnt) {
		if (retryCnt >= allowRetryCnt) {
			// 重试次数达到重试上限异常
			throw new TableException(e, TableError.ReachRetryLimitErr);
		}

		if (OtsExceptionUtils.isRetryException(e)) {
			try {
				Thread.sleep(200 * (++retryCnt));
			} catch (InterruptedException e1) {
				logger.error("OTS putRow() Exception.", e);
			}
		} else {
			throw new TableException(e, TableError.OTSExceptionErr);
		}
	}

	/**
	 * 新增OTS某条行记录
	 * 
	 * @param client
	 *            OTSClient
	 * @param rowPutChange
	 *            RowPutChange
	 * @return PutRowResult
	 */
	protected static PutRowResult putRow(final OTSClient client, final RowPutChange rowPutChange) {
		int retryCnt = 0;
		do {
			try {
				PutRowRequest request = new PutRowRequest(rowPutChange);

				return client.putRow(request);

			} catch (Exception e) {
				exceptionCheck(e, retryCnt);
			}
		} while (true);
	}

	/**
	 * 修改OTS行记录
	 * 
	 * @param client
	 *            OTSClient
	 * @param rowUpdateChange
	 *            RowUpdateChange
	 * @return UpdateRowResult
	 */
	protected static UpdateRowResult updateRow(final OTSClient client, final RowUpdateChange rowUpdateChange) {

		int retryCnt = 0;
		do {
			try {
				UpdateRowRequest request = new UpdateRowRequest(rowUpdateChange);

				return client.updateRow(request);

			} catch (Exception e) {
				exceptionCheck(e, retryCnt);
			}
		} while (true);
	}

	/**
	 * 删除ots某条行记录
	 * 
	 * @param client
	 *            OTSClient
	 * @param rowDeleteChange
	 *            RowDeleteChange
	 * @return DeleteRowResult
	 */
	protected static DeleteRowResult deleteRow(final OTSClient client, RowDeleteChange rowDeleteChange) {

		int retryCnt = 0;
		do {
			try {
				DeleteRowRequest request = new DeleteRowRequest(rowDeleteChange);

				return client.deleteRow(request);

			} catch (Exception e) {
				exceptionCheck(e, retryCnt);
			}
		} while (true);
	}

	/**
	 * 获取ots某条行记录
	 * 
	 * @param client
	 *            OTSClient
	 * @param criteria
	 *            SingleRowQueryCriteria
	 * @return GetRowResult
	 */
	protected static GetRowResult getRow(final OTSClient client, final SingleRowQueryCriteria criteria) {

		int retryCnt = 0;
		do {

			try {
				GetRowRequest request = new GetRowRequest(criteria);

				return client.getRow(request);

			} catch (Exception e) {
				exceptionCheck(e, retryCnt);
			}
		} while (true);
	}

	/**
	 * 批量插入OTS行数据
	 * 
	 * @param client
	 *            OTSClient
	 * @param rowPutChanges
	 *            List<RowPutChange>
	 * @return 返回插入失败的操作列表List<RowPutChange>
	 */
	protected static List<RowPutChange> batchWriteRow(OTSClient client, final List<RowPutChange> rowPutChanges) {
		BatchWriteRowRequest request = new BatchWriteRowRequest();
		for (RowPutChange rowPutChange : rowPutChanges) {
			Preconditions.checkNotNull(rowPutChange.getTableName(), "表名不能为空");
			Preconditions.checkNotNull(rowPutChange.getRowPrimaryKey(), "表主键设置不能为空");
			Preconditions.checkNotNull(rowPutChange.getCondition(), "主键重复判断条件不能为空");
			request.addRowPutChange(rowPutChange);
		}

		BatchWriteRowResult result = client.batchWriteRow(request);

		List<RowPutChange> failedRowPutChanges = new ArrayList<RowPutChange>();

		Map<String, List<RowStatus>> status = result.getPutRowStatus();
		for (Entry<String, List<RowStatus>> entry : status.entrySet()) {
			String tableName = entry.getKey();
			List<RowStatus> statuses = entry.getValue();
			for (int index = 0; index < statuses.size(); index++) {
				RowStatus rowStatus = statuses.get(index);
				if (!rowStatus.isSucceed()) {
					logger.error(
							"访问OTS服务时，发生BatchWriteRowErr, OTS服务返回错误号为 " + rowStatus.getError().getCode() + " ，错误信息为 "
									+ rowStatus.getError().getMessage(),
							new TableException(TableError.BatchWriteRowErr));

					// 操作失败，取出没有没有操作成功的RowPutChange
					failedRowPutChanges.add(request.getRowPutChange(tableName, index));
				}
			}
		}
		return failedRowPutChanges;
	}

	/**
	 * 读取指定主键范围内的数据
	 * 
	 * @param client
	 *            OTSClient
	 * @param rangeQueryCriteria
	 *            OTS范围查询条件
	 * @return OTS范围查询结果
	 */
	protected static GetRangeResult getRange(final OTSClient client, RangeRowQueryCriteria rangeQueryCriteria) {
		GetRangeRequest request = new GetRangeRequest(rangeQueryCriteria);
		return client.getRange(request);
	}

}
