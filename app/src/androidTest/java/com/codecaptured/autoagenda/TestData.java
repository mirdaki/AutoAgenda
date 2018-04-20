package com.codecaptured.autoagenda;

import com.codecaptured.autoagenda.database.room.entities.DataTimeBlock;
import com.codecaptured.autoagenda.database.room.entities.Task;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class TestData
{

	// Default data
	static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	static final Calendar calendar;
	static final Date tempDueDate1;
	static final Date tempDueDate2;
	static final Date tempDueDate3;
	static final Date tempStartDate1;
	static final Date tempStartDate2;
	static final Date tempStartDate3;

	static
	{
		calendar = Calendar.getInstance();

		calendar.set(2018, Calendar.APRIL, 17, 10, 00, 00);
		tempDueDate1 = calendar.getTime();

		calendar.set(2018, Calendar.APRIL, 20, 10, 00, 00);
		tempDueDate2 = calendar.getTime();

		calendar.set(2018, Calendar.APRIL, 21, 10, 00, 00);
		tempDueDate3 = calendar.getTime();

		calendar.set(2018, Calendar.APRIL, 21, 10, 00, 00);
		tempStartDate1 = calendar.getTime();

		calendar.set(2018, Calendar.APRIL, 22, 10, 00, 00);
		tempStartDate2 = calendar.getTime();

		calendar.set(2018, Calendar.APRIL, 22, 10, 00, 00);
		tempStartDate3 = calendar.getTime();
	}

	static final String[] tempTags1  = {"work"};
	static final String[] tempTags2  = {"work", "home"};
	static final String[] tempTags3  = {"home"};

	static final DataTimeBlock[] DATA_TIME_BLOCK_ARRAY_1 = {new DataTimeBlock(tempStartDate1, 120)};
	static final DataTimeBlock[] DATA_TIME_BLOCK_ARRAY_2 = {new DataTimeBlock(tempStartDate2, 15)};
	static final DataTimeBlock[] DATA_TIME_BLOCK_ARRAY_3 = {new DataTimeBlock(tempStartDate3, 60)};


	static final Task tempTask1 = new Task("Temp1", "Hello", false, tempDueDate1, 120, 3, tempTags1, UUID.randomUUID(), DATA_TIME_BLOCK_ARRAY_1);
	static final Task tempTask2 = new Task("Temp2", "Hello", false, tempDueDate2, 15, 3, tempTags2, UUID.randomUUID(), DATA_TIME_BLOCK_ARRAY_2);
	static Task tempTask3 = new Task("Temp3", "Hello", false, tempDueDate3, 60, 1, tempTags3, UUID.randomUUID(), DATA_TIME_BLOCK_ARRAY_3);
}
