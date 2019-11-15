package ru.paper.translate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.DoubleAdder;

@SpringBootApplication
public class TranslateApplication {

	public static void main(String[] args) {
		SpringApplication.run(TranslateApplication.class, args);
	}

}
