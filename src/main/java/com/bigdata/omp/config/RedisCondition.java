package com.bigdata.omp.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

//判断是ipv4还是ipv6(用来控制RedisTemplateConfig中的bean是否加载)
public class RedisCondition implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Environment environment = context.getEnvironment();
		//此处可以获取到配置文件的信息
        String cluster = environment.getProperty("spring.redis.cluster.nodes");
		System.out.println("RedisCondition cluster================"+cluster);
		if(null == cluster) {
			return false;
		}
		if(cluster.contains("[")) {
			return true;
		}else {
			return false;
		}
	}
}
