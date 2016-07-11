package com.xinma.base.datastore.model.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 定位信息
 * 
 * @author Alauda
 *
 * @date 2015年6月30日
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationEO {

	private String ip;

	private CoordinateEO coords;

	private AddressEO address;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public CoordinateEO getCoords() {
		return coords;
	}

	public void setCoords(CoordinateEO coords) {
		this.coords = coords;
	}

	public AddressEO getAddress() {
		return address;
	}

	public void setAddress(AddressEO address) {
		this.address = address;
	}

}
