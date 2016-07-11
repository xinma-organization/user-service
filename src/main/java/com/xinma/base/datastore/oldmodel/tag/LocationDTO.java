package com.xinma.base.datastore.oldmodel.tag;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 记录地址位置信息
 * 
 * @author Hoctor
 *
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationDTO implements Serializable {

	private static final long serialVersionUID = 7360665077152733503L;
	/**
	 * IP address
	 */
	@JsonProperty("ip")
	private String ip;

	/**
	 * coordinate type,坐标类型
	 */
	@JsonProperty("t")
	private String coordinateType;

	/**
	 * latitude,纬度坐标
	 */
	@JsonProperty("la")
	private String latitude;

	/**
	 * longitude,经度坐标
	 */
	@JsonProperty("lo")
	private String longitude;

	/**
	 * accuracy,精确度
	 */
	@JsonProperty("ac")
	private String accuracy;

	/**
	 * address, full address
	 */
	@JsonProperty("a")
	private String address;

	/**
	 * country,国家名
	 */
	@JsonProperty("c")
	private String country;

	/**
	 * countryCode,国家码
	 */
	@JsonProperty("cc")
	private String countryCode;

	/**
	 * province,省份
	 */
	@JsonProperty("p")
	private String province;

	/**
	 * city,市
	 */
	@JsonProperty("ct")
	private String city;

	/**
	 * area,区/县
	 */
	@JsonProperty("ar")
	private String area;

	/**
	 * road,路
	 */
	@JsonProperty("r")
	private String road;

	@JsonProperty("sn")
	private String streetNumber;

	public String getCoordinateType() {
		return coordinateType;
	}

	public void setCoordinateType(String coordinateType) {
		this.coordinateType = coordinateType;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
}
