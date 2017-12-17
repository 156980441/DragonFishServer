package com.liang.web.util;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

@Component
public class StartupListener implements ApplicationContextAware, ServletContextAware, InitializingBean,
		ApplicationListener<ContextRefreshedEvent> {

	protected Logger logger = Logger.getLogger(StartupListener.class);
	private boolean threadStart;
	@Autowired
	private TCPSocketThread myThread;

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		System.out.println("Class: " + this.getClass().getName() + " method: "
				+ Thread.currentThread().getStackTrace()[1].getMethodName() + " line:"
				+ Thread.currentThread().getStackTrace()[1].getLineNumber());
		// 服务已启动就开启一个监听线程，随时准备接受设备数据
		if (!threadStart) {
			this.threadStart = true;
			Thread thread = new Thread(myThread, "socket_8647");
			thread.start();
		}
	}

	@Override
	public void setServletContext(ServletContext context) {
		System.out.println("Class: " + this.getClass().getName() + " method: "
				+ Thread.currentThread().getStackTrace()[1].getMethodName() + " line:"
				+ Thread.currentThread().getStackTrace()[1].getLineNumber());

		logger.info("2 => StartupListener.setServletContext");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("Class: " + this.getClass().getName() + " method: "
				+ Thread.currentThread().getStackTrace()[1].getMethodName() + " line:"
				+ Thread.currentThread().getStackTrace()[1].getLineNumber());

		logger.info("3 => StartupListener.afterPropertiesSet");
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent evt) {
		System.out.println("Class: " + this.getClass().getName() + " method: "
				+ Thread.currentThread().getStackTrace()[1].getMethodName() + " line:"
				+ Thread.currentThread().getStackTrace()[1].getLineNumber());

		logger.info("4.1 => MyApplicationListener.onApplicationEvent");
		if (evt.getApplicationContext().getParent() == null) {
//			logger.info("4.2 => MyApplicationListener.onApplicationEvent");
		}
	}

}