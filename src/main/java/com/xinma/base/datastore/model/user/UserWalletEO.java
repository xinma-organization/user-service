package com.xinma.base.datastore.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 用户零钱包信息
 * 
 * @author Alauda
 *
 * @date 2015年6月30日
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserWalletEO {

	private UserWalletSummaryEO summary;

	private UserWalletBillsEO bills;

	public UserWalletSummaryEO getSummary() {
		return summary;
	}

	public void setSummary(UserWalletSummaryEO summary) {
		this.summary = summary;
	}

	public UserWalletBillsEO getBills() {
		return bills;
	}

	public void setBills(UserWalletBillsEO bills) {
		this.bills = bills;
	}
}
