package cn.edu.zucc.booklib.control;

import cn.edu.zucc.booklib.model.BeanReader;
import cn.edu.zucc.booklib.util.BaseException;

import java.util.List;

public class PageData {

	public List<BeanReader> loadPageReader(String keyword,int readerTypeId,int page,int count) throws BaseException {
		return new ReaderManager().searchPageReader(keyword, readerTypeId, page, count);
	}

	public static void main(String[] args) {

	}
}
