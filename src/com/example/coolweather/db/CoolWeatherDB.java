package com.example.coolweather.db;

import java.util.ArrayList;
import java.util.List;

import com.example.coolweather.model.City;
import com.example.coolweather.model.County;
import com.example.coolweather.model.Province;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CoolWeatherDB {

	public static final String DB_NAME = "cool_weather";
	public static final int VERSION = 1;
	private static CoolWeatherDB coolWeatherDB;
	private SQLiteDatabase db;

	private CoolWeatherDB(Context context) {
		CoolWeatherOpenHelper dbhelper = new CoolWeatherOpenHelper(context,
				DB_NAME, null, VERSION);
		db = dbhelper.getWritableDatabase();
	}

	public synchronized static CoolWeatherDB getInstance(Context context) {
		if (coolWeatherDB == null) {
			coolWeatherDB = new CoolWeatherDB(context);
		}
		return coolWeatherDB;

	}

	public void saveProvince(Province province) {
		if (province != null) {
			ContentValues values = new ContentValues();
			values.put("provinceName", province.getProvinceName());
			values.put("provinceCode", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}

	public List<Province> loadProvinces() {
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db
				.query("Province", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor
						.getColumnIndex("provinceName")));
				province.setProvinceCode(cursor.getString(cursor
						.getColumnIndex("provinceCode")));
				list.add(province);
			} while (cursor.moveToNext());
		}
		return list;
	}

	public void saveCity(City city) {
		if (city != null) {
			ContentValues values = new ContentValues();
			values.put("cityName", city.getCityName());
			values.put("cityCode", city.getCityCode());
			values.put("provinceId", city.getProvinceId());
			db.insert("City", null, values);
		}

	}

	public List<City> loadCities(int provinceId) {
		List<City> list = new ArrayList<City>();
		City city = new City();
		Cursor cursor = db.query("City", null, "province_id = ?",
				new String[] { String.valueOf(provinceId) }, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor
						.getColumnIndex("cityName")));
				city.setCityCode(cursor.getString(cursor
						.getColumnIndex("cityCode")));
				city.setProvinceId(cursor.getInt(cursor
						.getColumnIndex("provinceId")));
				list.add(city);
			} while (cursor.moveToNext());
		}
		return list;
	}

	public void saveCounty(County county) {
		if (county != null) {
			ContentValues values = new ContentValues();
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("city_id", county.getCityId());
			db.insert("County", null, values);
		}
	}

	public List<County> loadCounties(int cityId) {
		List<County> list = new ArrayList<County>();
		County county = new County();
		Cursor cursor = db.query("County", null, "city_id = ?",
				new String[] { String.valueOf(cityId) }, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyName(cursor.getString(cursor
						.getColumnIndex("cityName")));
				county.setCountyCode(cursor.getString(cursor
						.getColumnIndex("countyCode")));
				county.setCityId(cursor.getInt(cursor.getColumnIndex("cityId")));
				list.add(county);
			} while (cursor.moveToNext());
		}
		return list;

	}
}
