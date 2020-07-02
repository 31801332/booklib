package cn.edu.zucc.booklib.random;

import java.text.SimpleDateFormat;
import java.sql.Date;

public class RDate {
	public static void main(String[] args) {

		for (int i = 0; i < 30; i++) {
			Date date = new RDate().randomDate("2019-01-01", "2019-01-31");
			System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
		}
	}

	public Date randomDate(String beginDate, String endDate) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date start = new Date(format.parse(beginDate).getTime());
			Date end = new Date(format.parse(endDate).getTime());

			if (start.getTime() >= end.getTime()) {
				return null;
			}
			long date = random(start.getTime(), end.getTime());
			return new Date(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static long random(long begin, long end) {
		long rtn = begin + (long) (Math.random() * (end - begin));
		if (rtn == begin || rtn == end) {
			return random(begin, end);
		}
		return rtn;
	}

}
