package cn.com.dhcc.credit.approval.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import cn.com.dhcc.platform.middleware.operationshistory.converter.Converter;

@Component
public class DateConvert implements Converter{

	@Override
	public String Convert(Object obj) {
		if(obj == null){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format((Date)obj);
	}

}
