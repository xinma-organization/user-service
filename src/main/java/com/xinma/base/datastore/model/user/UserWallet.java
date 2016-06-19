package com.xinma.base.datastore.model.user;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 用户钱包
 * 
 * @author Hoctor
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserWallet {
	/**
	 * 收入总额，单位为元
	 */
	@JsonProperty("tr")
	private Float totalReceipt;

	/**
	 * 提现总额，单位为元
	 */
	@JsonProperty("tw")
	private Float totalWithdraw;

	public Float getTotalReceipt() {
		return totalReceipt == null ? 0 : totalReceipt;
	}

	public void setTotalReceipt(Float totalReceipt) {
		this.totalReceipt = totalReceipt;
	}

	public Float getTotalWithdraw() {
		return totalWithdraw == null ? 0 : totalWithdraw;
	}

	public void setTotalWithdraw(Float totalWithdraw) {
		this.totalWithdraw = totalWithdraw;
	}

	public Float getAvailable() {
		return new BigDecimal(Float.toString(getTotalReceipt()))
				.subtract(new BigDecimal(Float.toString(getTotalWithdraw()))).floatValue();
	}
}
