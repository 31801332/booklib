package cn.edu.zucc.booklib.random;

import cn.edu.zucc.booklib.control.BookManager;
import cn.edu.zucc.booklib.control.PublisherManager;
import cn.edu.zucc.booklib.control.ReaderManager;
import cn.edu.zucc.booklib.control.SystemUserManager;
import cn.edu.zucc.booklib.model.*;
import cn.edu.zucc.booklib.util.BaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class randBean {
	public void randReader(int count) {
		Random r = new Random();
		BeanReader b = new BeanReader();

		List<BeanReaderType> brt = new ArrayList<BeanReaderType>();
		List<BeanSystemUser> bsu = new ArrayList<BeanSystemUser>();

		try {
			brt = new ReaderManager().loadAllReaderType();
			bsu = new SystemUserManager().loadAllUsers(false);
		} catch (BaseException e) {
			e.printStackTrace();
		}

		for (int I = 1; I <= count; I++) {
			b.setReaderid("" + r.nextInt(100000000));
			b.setReaderName(new RName().build());
			int i = r.nextInt(brt.size());
			b.setReaderTypeId(brt.get(i).getReaderTypeId());
			b.setLendBookLimitted(r.nextInt(1000));
			b.setCreateDate(new RDate().randomDate("2020-01-01", "2020-05-01"));
			b.setCreatorUserId(bsu.get(r.nextInt(bsu.size())).getUserid());

			try {
				new ReaderManager().createReader(b);
			} catch (BaseException e) {
				e.printStackTrace();
				System.out.println(I + ":error");
			}
//			System.out.println(b.toString());
		}
	}

	public void randBook(int count) {
		Random r = new Random();
		BeanBook b = new BeanBook();

		List<BeanPublisher> bp = new ArrayList<BeanPublisher>();

		try {
			bp = new PublisherManager().loadAllPublisher();
		} catch (BaseException e) {
			e.printStackTrace();
		}

		for (int I = 1; I <= count; I++) {
			b.setBarcode("" + r.nextInt(1000000));
			b.setBookname(new RName().build());
			int i = r.nextInt(bp.size());
			b.setPubid(bp.get(i).getPubid());
			b.setPrice(r.nextDouble() * r.nextInt(100));
			b.setState("ÔÚ¿â");
			b.setPubName(bp.get(i).getPublisherName());

			try {
				new BookManager().createBook(b);
			} catch (BaseException e) {
				e.printStackTrace();
				System.out.println(I + "error");
			}

//			System.out.println(b.toString());
		}

	}

	public static void main(String[] args) {
		new randBean().randBook(1000);
	}
}
