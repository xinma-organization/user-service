package com.xinma.base.datastore.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 用户钱包总收支信息
 * 
 * @author Alauda
 *
 * @date 2015年6月30日
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserWalletSummaryEO {

	/**
	 * 总收入
	 */
	private int totalIncome;

	/**
	 * 总支出
	 */
	private int totalExpenditure;

	public int getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(int totalIncome) {
		this.totalIncome = totalIncome;
	}

	public int getTotalExpenditure() {
		return totalExpenditure;
	}

	public void setTotalExpenditure(int totalExpenditure) {
		this.totalExpenditure = totalExpenditure;
	}
}
