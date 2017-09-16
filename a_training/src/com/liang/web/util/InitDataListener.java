package com.liang.web.util;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class InitDataListener implements InitializingBean, DisposableBean {
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("afterPropertiesSet 被执行");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("destroy 被执行");
	}

}