package com.codecaptured.autoagenda.database.room;

import android.arch.persistence.room.TypeConverter;

import com.codecaptured.autoagenda.database.room.entities.DataTimeBlock;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.UUID;

/**
 * Created by matthew on 4/2/18.
 */

public class Converters
{
	@TypeConverter
	public static Date timestampToDate(Long value)
	{
		return value == null ? null : new Date(value);
	}

	@TypeConverter
	public static Long dateToTimestamp(Date date)
	{
		return date == null ? null : date.getTime();
	}

	@TypeConverter
	public static UUID stringToUuid(String id)
	{
		return id == null ? null : UUID.fromString(id);
	}

	@TypeConverter
	public static String uuidToString(UUID id)
	{
		return id == null ? null : id.toString();
	}

	@TypeConverter
	public static String[] stringToStringArray(String data)
	{
		Type listType = new TypeToken<String[]>() {}.getType();
		return new Gson().fromJson(data, listType);
	}

	@TypeConverter
	public static String stringArrayToString(String[] strings)
	{
		Gson gson = new Gson();
		String json = gson.toJson(strings);
		return json;
	}

	@TypeConverter
	public static DataTimeBlock[] stringToDataTimeBlockArray(String data)
	{
		Type listType = new TypeToken<DataTimeBlock[]>() {}.getType();
		return new Gson().fromJson(data, listType);
	}

	@TypeConverter
	public static String dataTimeBlockArrayToString(DataTimeBlock[] blocks)
	{
		Gson gson = new Gson();
		String json = gson.toJson(blocks);
		return json;
	}

	@TypeConverter
	public static DataTimeBlock stringToDataTimeBlock(String data)
	{
		Type type = new TypeToken<DataTimeBlock>() {}.getType();
		return new Gson().fromJson(data, type);
	}

	@TypeConverter
	public static String dataTimeBlockToString(DataTimeBlock block)
	{
		Gson gson = new Gson();
		String json = gson.toJson(block);
		return json;
	}
}
