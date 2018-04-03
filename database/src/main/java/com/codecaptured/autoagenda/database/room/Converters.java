package com.codecaptured.autoagenda.database.room;

import android.arch.persistence.room.TypeConverter;

import com.codecaptured.autoagenda.database.room.entities.TimeBlock;

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
		return data == null ? null : data.split(",");
	}

	@TypeConverter
	public static String stringArrayToString(String[] strings)
	{
		if (strings == null)
		{
			return null;
		}

		StringBuilder builder = new StringBuilder();

		for (String string : strings)
		{
			if (builder.length() > 0)
			{
				builder.append(",");
			}
			builder.append(string);
		}

		return builder.toString();
	}

	@TypeConverter
	public static TimeBlock[] stringToTimeBlock(String data)
	{
		if (data == null)
		{
			return null;
		}

		String[] bothValues = data.split(",");
		TimeBlock[] blocks = new TimeBlock[bothValues.length/2];

		for (int i = 0; i < bothValues.length/2; i++)
		{
			blocks[i].startTime = timestampToDate(Long.parseLong(bothValues[2*i]));
			blocks[i].numberOfMinutes = Integer.valueOf(bothValues[2*i + 1]);
		}

		return blocks;
	}

	@TypeConverter
	public static String timeBlockToString(TimeBlock[] blocks)
	{
		if (blocks == null)
		{
			return null;
		}

		StringBuilder builder = new StringBuilder();

		for (TimeBlock block : blocks)
		{
			if (builder.length() > 0)
			{
				builder.append(",");
			}
			builder.append(String.valueOf(dateToTimestamp(block.startTime)) +
							"," +
							Integer.toString(block.numberOfMinutes));
		}

		return builder.toString();
	}
}
