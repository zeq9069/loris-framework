package org.kyrin.loris_framework.transaction;

import java.lang.reflect.Method;

import org.kyrin.loris_framework.aop.AspectProxy;
import org.kyrin.loris_framework.aop.ProxyChain;
import org.kyrin.loris_framework.transaction.Transaction;
import org.kyrin.loris_framework.utils.DatabaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 事务代理
 * @author kyrin
 *
 */
public class TransactionProxy extends AspectProxy{
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionProxy.class);

	private static final ThreadLocal<Boolean> FLAG_HELDER=new ThreadLocal<Boolean>(){
		@Override
		protected Boolean initialValue() {
			return false;
		}
	};
	
	@Override
	public Object doProxy(ProxyChain proxyChain) {
		Object result=null;
		boolean flag=FLAG_HELDER.get();
		try {
			Method method = proxyChain.getTargetMethod();
			if(!flag && method.isAnnotationPresent(Transaction.class)){
				logger.debug("begin transaction");
				System.out.println("begin transaction");
				DatabaseHelper.beginTransaction();
				result = proxyChain.doProxyChain();
				logger.debug("commit transaction");
				System.out.println("commit transaction");
				DatabaseHelper.commitTransaction();
			}
		} catch (Throwable e) {
			logger.debug("rollback transaction");
			System.out.println("rollback transaction");
			DatabaseHelper.rollbackTransaction();
			throw new RuntimeException(e);
		}
		return result;
	}
}
