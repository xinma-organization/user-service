package com.xinma.base.datastore.model.user;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 云端用户表信息实体类
 * 
 * @author Alauda
 *
 * @date 2016年5月30日
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRow {

	private int userId;

	private UserWalletEO wallet;

	private List<UserAwardsEO> awards;

	private List<UserPointsEO> points;

	private Set<Long> tags;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public UserWalletEO getWallet() {
		return wallet;
	}

	public void setWallet(UserWalletEO wallet) {
		this.wallet = wallet;
	}

	public List<UserAwardsEO> getAwards() {
		return awards;
	}

	public void setAwards(List<UserAwardsEO> awards) {
		this.awards = awards;
	}

	public List<UserPointsEO> getPoints() {
		return points;
	}

	public void setPoints(List<UserPointsEO> points) {
		this.points = points;
	}

	public Set<Long> getTags() {
		return tags;
	}

	public void setTags(Set<Long> tags) {
		this.tags = tags;
	}

}
