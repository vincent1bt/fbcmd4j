package org.fbcmd4j;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;
import java.net.URL;
import java.net.MalformedURLException;

import org.fbcmd4j.helper.LoadHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Post;
import facebook4j.ResponseList;

public class Main {
    static final Logger logger = LogManager.getLogger(Main.class);

    public static void showPosts(ResponseList<Post> feed) {
        for (Post post : feed) {
            if(post.getStory() != null) {
                System.out.println("Historia: " + post.getStory());
            }
                
            if(post.getMessage() != null)
                System.out.println("Mensaje: " + post.getMessage());
            System.out.println("|o||o||o||o||o||o||o||o||o||o||o||o||o||o||o||o||o|");
        }

        System.out.println("======================================================");
    }

    public static void savePosts(ResponseList<Post> feed, String fileName, Scanner scanner) {
        int postsSize = feed.size();
        System.out.println("Cuantos posts quieres guardar? Maximo: " + Integer.toString(postsSize));
        int size = Integer.parseInt(scanner.nextLine());
        int index = 0;

        try {
            PrintWriter writer = new PrintWriter(fileName, "UTF-8");

            for (Post post : feed) {
                if (index == size) {
                    break;
                }

                if(post.getStory() != null) {
                    writer.println("Historia: " + post.getStory());
                    System.out.println();
                }
                    
                if(post.getMessage() != null) {
                    writer.println("Mensaje: " + post.getMessage());
                }
                
                index += 1;
            }
            
            writer.close();
            System.out.println("Archivo guardado");
        } catch (Exception exception) {
            logger.error(exception);
        }
    }

	public static void main(String[] args) {
		logger.info("Iniciando app");
        
        LoadHelper facebookHelper = new LoadHelper();

        logger.info("Cargando configuracion por defecto");
        facebookHelper.loadConfiguration();
        Facebook facebook = facebookHelper.getInstance();
		
		try {
			Scanner scanner = new Scanner(System.in);
            
            while(true) {
                System.out.println("Opciones: ");
                System.out.println("(1) Obtener NewsFeed");
                System.out.println("(2) Obtener Wall");
                System.out.println("(3) Publicar Estado");
                System.out.println("(4) Publicar Link");
                System.out.println("(5) Configuracion");
                System.out.println("(6) Salir");
                System.out.println("=================================");

                String respuesta = scanner.nextLine();

                if (respuesta.equals("1")) {
                    logger.info("Obtener NewsFeed");
                    System.out.println("NewsFeed: ");
                    ResponseList<Post> feed = facebook.getFeed();

                    showPosts(feed);
                    
                    System.out.println("Guardar el NewsFeed en un archivo de texto?");
                    String guardar = scanner.nextLine();

                    if (guardar.equals("si")) {
                        logger.info("Guardar posts de NewsFeed");
                        savePosts(feed, "NewsFeed.txt", scanner);
                    }

                } else if (respuesta.equals("2")) {
                    logger.info("Obtener Wall");
                    System.out.println("Wall: ");
                    ResponseList<Post> wall = facebook.getPosts();
                    
                    showPosts(wall);

                    System.out.println("Guardar el Wall en un archivo de texto?");
                    String guardar = scanner.nextLine();

                    if (guardar.equals("si")) {
                        logger.info("Guardar posts de Wall");
                        savePosts(wall, "Wall.txt", scanner);
                    }

                } else if (respuesta.equals("3")) {
                    logger.info("Publicar estado");
                    System.out.println("Escribe tu estado: ");
                    String estado = scanner.nextLine();

                    try {
                        facebook.postStatusMessage(estado);
                    } catch (FacebookException exception) {
                        logger.error(exception);
                    }	

                } else if (respuesta.equals("4")) {
                    logger.info("Publicar link");
                    System.out.println("Escribe el link: ");
                    String link = scanner.nextLine();

                    try {
                        facebook.postLink(new URL(link));
                    } catch (FacebookException exception) {
                        logger.error(exception);
                    } catch (MalformedURLException urlException) {
                        logger.error(urlException);
                    }

                } else if (respuesta.equals("5")) {
                    facebookHelper.createConfiguration(scanner);
                    facebook = facebookHelper.getInstance();
                } else if (respuesta.equals("6")) {
                    System.out.println("Saliendo de la aplicacion");
                    System.exit(0);
					break;
                } else {
                    System.out.println("Por favor seleccione una opcion valida");
                }
            }

		} catch (Exception exception) {
			logger.error(exception);
		}
	}
}