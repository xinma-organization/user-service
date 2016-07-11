package com.xinma.base.datastore.impl.aliyun.table;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.mns.common.ClientException;
import com.aliyun.openservices.ots.OTSClient;
import com.aliyun.openservices.ots.OTSErrorCode;
import com.aliyun.openservices.ots.OTSException;
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

	/**
	 * 新增OTS记录
	 * 
	 * @param client
	 *            OTSClient
	 * @param rowPutChange
	 *            添加的行内容
	 * @return 添加行操作的结果
	 */
	public static PutRowResult putRow(final OTSClient client, final RowPutChange rowPutChange) {
		int retryCnt = 0;
		do {
			try {
				PutRowRequest request = new PutRowRequest(rowPutChange);

				return client.putRow(request);

			} catch (Exception e) {
				if (retryCnt >= allowRetryCnt) {
					new TableException(TableError.ReachRetryLimitErr);
					// TODO logger.error("插入行操作不成功，重试次数达到上限。");
					throw e;
				}

				logger.error(e.getMessage());

				if (isRetryException(e)) {
					try {
						Thread.sleep(200 * (++retryCnt));
						continue;
					} catch (InterruptedException e1) {
						logger.error(e1.getMessage());
					}
				} else {
					throw e;
				}
			}
		} while (true);
	}

	/**
	 * 更新表中某行值
	 * 
	 * @param client
	 *            OTSClient
	 * @param rowUpdateChange
	 *            OTS行修改的内容
	 * @return OTS行修改的结果
	 */
	public static UpdateRowResult updateRow(final OTSClient client, final RowUpdateChange rowUpdateChange) {
		Preconditions.checkNotNull(rowUpdateChange.getTableName(), "表名不能为空");
		Preconditions.checkNotNull(rowUpdateChange.getRowPrimaryKey(), "表主键设置不能为空");
		Preconditions.checkNotNull(rowUpdateChange.getCondition(), "主键重复判断条件不能为空");

		int retryCnt = 0;
		do {
			try {

				UpdateRowRequest request = new UpdateRowRequest(rowUpdateChange);

				return client.updateRow(request);
			} catch (Exception e) {

				if (retryCnt == 10) {
					logger.error("更新行操作不成功，重试次数达到上限。");
					throw e;
				}
				logger.error(e.getMessage());

				if (isRetryException(e)) {
					try {
						Thread.sleep(200 * (++retryCnt));
						continue;
					} catch (InterruptedException e1) {
						logger.error(e1.getMessage());
					}
				} else {
					throw e;
				}
			}
		} while (true);
	}

	/**
	 * 删除ots某条记录
	 * 
	 * @param client
	 *            OTSClient
	 * @param rowDeleteChange
	 *            OTS删除操作内容
	 * @return OTS行删除的结果
	 */
	public static DeleteRowResult deleteRow(final OTSClient client, RowDeleteChange rowDeleteChange) {

		Preconditions.checkNotNull(rowDeleteChange.getTableName(), "表名不能为空");
		Preconditions.checkNotNull(rowDeleteChange.getRowPrimaryKey(), "表主键设置不能为空");

		int retryCnt = 0;
		do {
			try {

				DeleteRowRequest request = new DeleteRowRequest(rowDeleteChange);

				return client.deleteRow(request);

			} catch (Exception e) {
				logger.error(e.getMessage());

				if (retryCnt == 10) {
					logger.error("删除行操作不成功，重试次数达到上限。");
					throw e;
				}

				if (isRetryException(e)) {
					try {
						Thread.sleep(200 * (++retryCnt));
						continue;
					} catch (InterruptedException e1) {
						logger.error(e1.getMessage());
					}
				} else {
					throw e;
				}
			}
		} while (true);
	}

	/**
	 * 获取ots某条记录
	 * 
	 * @param client
	 *            OTSClient
	 * @param criteria
	 *            OTS行查询的查询条件
	 * @return OTS行查询结果
	 */
	public static GetRowResult getRow(final OTSClient client, final SingleRowQueryCriteria criteria) {

		Preconditions.checkNotNull(criteria.getTableName(), "表名不能为空");
		Preconditions.checkNotNull(criteria.getRowPrimaryKey(), "行主键不能为空");

		int retryCnt = 0;
		do {

			try {

				GetRowRequest request = new GetRowRequest(criteria);

				return client.getRow(request);

			} catch (Exception e) {
				if (retryCnt == 10) {
					logger.error("读取行操作不成功，重试次数达到上限。");
					throw e;
				}

				logger.error(e.getMessage());

				if (isRetryException(e)) {
					try {
						Thread.sleep(200 * (++retryCnt));
						continue;
					} catch (InterruptedException e1) {
						logger.error(e1.getMessage());
					}
				} else {
					throw e;
				}
			}
		} while (true);
	}

	/**
	 * 批量插入数据, 但是给定OTSClient
	 * 
	 * @param client
	 *            OTSClient
	 * @param rowPutChanges
	 *            OTS行添加数据集
	 * @return 返回插入失败的操作列表
	 */
	public static List<RowPutChange> batchWriteRow(OTSClient client, final List<RowPutChange> rowPutChanges) {
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
	public static GetRangeResult getRange(final OTSClient client, RangeRowQueryCriteria rangeQueryCriteria) {
		GetRangeRequest request = new GetRangeRequest(rangeQueryCriteria);
		return client.getRange(request);
	}

	/**
	 * 判断是不是可重试的OTS Exception
	 * 
	 * @param e
	 *            Exception对象
	 * @return 当前Exception为重试异常时，返回true;否则返回false
	 */
	public static boolean isRetryException(Exception e) {
		if (e instanceof SocketTimeoutException || e instanceof UnknownHostException) {
			return true;
		} else if (e instanceof OTSException) {
			String errCode = ((OTSException) e).getErrorCode();
			if (errCode.equals(OTSErrorCode.NOT_ENOUGH_CAPACITY_UNIT) || errCode.equals(OTSErrorCode.SERVER_UNAVAILABLE)
					|| errCode.equals(OTSErrorCode.STORAGE_TIMEOUT)
					|| errCode.equals(OTSErrorCode.PARTITION_UNAVAILABLE) || errCode.equals(OTSErrorCode.SERVER_BUSY)
					|| errCode.equals(OTSErrorCode.TABLE_NOT_READY)
					|| errCode.equals(OTSErrorCode.ROW_OPERATION_CONFLICT)
					|| errCode.equals(OTSErrorCode.INTERNAL_SERVER_ERROR)) {
				return true;
			}

		} else if (e instanceof ClientException) {
			return true;
		}

		return false;
	}

	/**
	 * 判断OTS异常是否是OTS条件判断失败的异常（OTS操作不满者Condition的异常）
	 * 
	 * @param e
	 *            Exception对象
	 * @return 当前异常为OTSCondtion异常,返回true;否则返回false
	 */
	public static boolean isOTSConditionCheckFailException(Exception e) {
		if (e instanceof OTSException) {
			String errCode = ((OTSException) e).getErrorCode();
			if (errCode.equals(OTSErrorCode.CONDITION_CHECK_FAIL)) {
				return true;
			}
		}

		return false;
	}

}
