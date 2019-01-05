package com.cmnt.nurse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = {"com.cmnt.nurse"})
@MapperScan({"com.cmnt.nurse.dao.*","com.gitee.sunchenbin.mybatis.actable.dao.*"})
public class NurseApplication {

	public static void main(String[] args) {
		SpringApplication.run(NurseApplication.class, args);
	}

}

