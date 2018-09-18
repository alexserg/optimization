package ru.sberbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class Optdemo1Application {

	public static void main(String[] args) {
		SpringApplication.run(Optdemo1Application.class, args);
	}

	@Scheduled(cron = "* 1/60 * * * *") // every day at one AM
//    @Scheduled(fixedDelay = 1000) // once a second
	public void readApplovin() throws InterruptedException {
		Thread.sleep(1);
//		System.out.println("I'm alive!");
	}
}
