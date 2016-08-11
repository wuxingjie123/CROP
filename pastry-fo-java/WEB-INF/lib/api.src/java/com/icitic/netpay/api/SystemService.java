package com.icitic.netpay.api;

import java.util.List;

import com.xunda.model.City;
import com.xunda.model.Province;

public interface SystemService {
	/**
	 * 获取省
	 * @param card
	 * @return
	 */
	public List<Province> findAllProvince();
	/**
	 * 获取所有某省的所有城市
	 * @param card
	 * @return
	 */
	public List<City> findCitysByProvince(String provinceid);
}
