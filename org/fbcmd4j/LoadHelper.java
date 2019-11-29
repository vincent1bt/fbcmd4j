package org.fbcmd4j.helper;

import java.util.Scanner;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import facebook4j.auth.AccessToken;
import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.Post;

public class LoadHelper {
    String folder = "config";
    String fileName = "fbcmd4j.properties";
    private static final Logger logger = LogManager.getLogger(LoadHelper.class);
    Properties props = new Properties();

	public Facebook getInstance() {
		Facebook facebook = new FacebookFactory().getInstance();
		facebook.setOAuthAppId(this.props.getProperty("appId"), this.props.getProperty("appSecret"));
        facebook.setOAuthPermissions(this.props.getProperty("permissions"));
        facebook.setOAuthAccessToken(new AccessToken(this.props.getProperty("accessToken"), null));
		
		return facebook;
    }

    public void loadConfiguration() {
        logger.info("Cargando configuracion desde el archivo fbcmd4j.properties");

        Path folderPath = Paths.get(this.folder);
        Path filePath = Paths.get(this.folder, this.fileName);

        try {
            this.props.load(Files.newInputStream(filePath));
        } catch (IOException exception) {
            logger.error(exception);
        }
    }

    public void createConfiguration(Scanner scanner) {
        logger.info("Creando nueva configuracion");

        System.out.println("Ingrese el appId:");
        String appId = scanner.nextLine();

        System.out.println("Ingrese el appSecret:");
        String appSecret = scanner.nextLine();

        System.out.println("Ingrese el accessToken:");
        String accessToken = scanner.nextLine();

        this.props.setProperty("appId", appId);
        this.props.setProperty("appSecret", appSecret);
        this.props.setProperty("accessToken", accessToken);

        System.out.println("Configuracion cambiada");
    }
}