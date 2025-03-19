package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class Main extends Application {
	private ConfigurableApplicationContext springContext;

	@Override
	public void init() throws Exception {
		springContext = SpringApplication.run(Main.class);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/NhanVien.fxml"));
		loader.setControllerFactory(springContext::getBean); // Dùng Spring để quản lý Controller
		Scene scene = new Scene(loader.load());
		scene.getStylesheets().add(getClass().getResource("/asset/css/main.css").toExternalForm());

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		springContext.close();
	}
}



//PS C:\Users\HP> netstat -ano | findstr :8080
//TCP    0.0.0.0:8080           0.0.0.0:0              LISTENING       8376
//PS C:\Users\HP> taskkill /PID 8376 /F
//SUCCESS: The process with PID 8376 has been terminated.
//PS C:\Users\HP>