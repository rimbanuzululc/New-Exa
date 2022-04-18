package com.bsi.exa.foundation.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class ImageCompressor {
	public final static Logger logger = LoggerFactory.getLogger(ImageCompressor.class);
	
	public static Future<String> compress(Vertx vertx, String input_, String output_, float quality, Integer maxSize, Integer maxWidth, boolean toJpg) {
		Future<String> result = Future.future();
		
		Vertx.currentContext().<String>executeBlocking(f -> {
			String output = null;
			ImageOutputStream ios = null;
			OutputStream os = null;
			ImageWriter writer = null;
			File compressedImageFile = null;
			float quality_ = quality;
			boolean resized = false;
			try {
				//logger.info("Input : "+input_);
				//logger.info("Output : "+output_);
				File input = new File(input_);
				
				if (input.getName().toLowerCase().contains(".png") && toJpg) {
					//logger.info("Convert to jpg...");
					input = convertPngToJpg(input);
				}
				
				if (input == null) {
					f.complete(null);
					return;
				}
				
				BufferedImage image = ImageIO.read(input);
				
				if (image.getWidth() > maxWidth && input.getName().toLowerCase().contains(".jpg")) {
					//logger.info("Resize image...");
					image = resize(image, maxWidth);
					resized = true;
					//logger.info("Resize image done");
				}
				output = output_;
				
				String format = "jpg";
				if (input.getName().toLowerCase().contains(".png"))
					format = "png";
				
				if ((input.length()/1024) > maxSize && format.equals("jpg")) {
					while (compressedImageFile == null || ((compressedImageFile.length()/1024) > maxSize && quality_ > 0.2f)) {
						//logger.info("Write to output and set quality : "+quality_);
						compressedImageFile = new File(output);
						os = new FileOutputStream(compressedImageFile);
						
						Iterator<ImageWriter>writers =  ImageIO.getImageWritersByFormatName(format);
						writer = (ImageWriter) writers.next();
						
						ios = ImageIO.createImageOutputStream(os);
						writer.setOutput(ios);
						
						ImageWriteParam param = writer.getDefaultWriteParam();
						
						param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
						param.setCompressionQuality(quality_);
						writer.write(null, new IIOImage(image, null, null), param);
						quality_ = quality_ - 0.1f;
						
						try {
							if (os != null)
								os.close();
						} catch (IOException e) {
							logger.error(e.getMessage(), e);
						}
						try {
							if (ios != null)
								ios.close();
						} catch (IOException e) {
							logger.error(e.getMessage(), e);
						}
						if (writer != null)
							writer.dispose();
					}
//					try {
//						vertx.fileSystem().deleteBlocking(input.getAbsolutePath());
//					}
//					catch (Exception e) {}
				}
				else {
					if (resized) {
						//logger.info("Copy resized to output");
						compressedImageFile = new File(output);
						os = new FileOutputStream(compressedImageFile);
						
						Iterator<ImageWriter>writers =  ImageIO.getImageWritersByFormatName(format);
						writer = (ImageWriter) writers.next();
						
						ios = ImageIO.createImageOutputStream(os);
						writer.setOutput(ios);
						
						ImageWriteParam param = writer.getDefaultWriteParam();
						writer.write(null, new IIOImage(image, null, null), param);
						
						try {
							if (os != null)
								os.close();
						} catch (IOException e) {
							logger.error(e.getMessage(), e);
						}
						try {
							if (ios != null)
								ios.close();
						} catch (IOException e) {
							logger.error(e.getMessage(), e);
						}
						if (writer != null)
							writer.dispose();
//						try {
//							vertx.fileSystem().deleteBlocking(input.getAbsolutePath());
//						}
//						catch (Exception e) {}
					}
					else {
						//logger.info("Copy to output");
						//final String apath = input.getAbsolutePath();
						vertx.fileSystem().copy(input.getAbsolutePath(), output, ret->{
							/*try {
								vertx.fileSystem().deleteBlocking(apath);
							}
							catch (Exception e) {}*/
						});
					}
				}
				
				f.complete(output);
			} catch (Exception e) {
				logger.error("Failed convert image : "+e.getMessage(), e);
				try {
					if (os != null)
						os.close();
				} catch (IOException ex) {
					logger.error(e.getMessage(), ex);
				}
				try {
					if (ios != null)
						ios.close();
				} catch (IOException ex) {
					logger.error(e.getMessage(), ex);
				}
				if (writer != null)
					writer.dispose();
				f.fail(e);
			} 
		}, r -> {
			if (r.succeeded()) {
				result.complete(r.result());
			} else {
				result.fail(r.cause());
			}
		});
		
		return result;
	}
	
	public static File convertPngToJpg(File input) {
		String output_ = input.getParent()+File.separator+input.getName().toLowerCase().replace(".png", ".jpg");
		File output = null;
		try {
            output = new File(output_);

            BufferedImage image = ImageIO.read(input);
            BufferedImage result = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            result.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
            ImageIO.write(result, "jpg", output);
            
//            Vertx.currentContext().owner().fileSystem().deleteBlocking(input.getAbsolutePath());
        }  catch (IOException e) {
        	logger.error("Failed convert image : "+e.getMessage(), e);
        }
		return output;
	}
	
	public static File convertPngToJpg(BufferedImage input, String output_) {
		File output = null;
		try {
            output = new File(output_);

            BufferedImage image = input;
            BufferedImage result = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            result.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
            ImageIO.write(result, "jpg", output);
            
            //input.delete();
        }  catch (IOException e) {
        	logger.error("Failed convert image : "+e.getMessage(), e);
        }
		return output;
	}
	
	
	public static BufferedImage resize(BufferedImage image, int maxWidth) {
		try {
            int width = maxWidth;
            int height = image.getHeight()*maxWidth/image.getWidth();
            BufferedImage resizedImage = Scalr.resize(image,
                    Method.SPEED,
                    Mode.FIT_TO_WIDTH,
                    width,
                    height,
                    Scalr.OP_ANTIALIAS);
            
            return resizedImage;
        }  catch (Exception e) {
        	logger.error("Failed convert image : "+e.getMessage(), e);
        }
		return image;
	}
	
	public static String getByteArrayFromImageURL(String url) throws Exception {
		final String EXT_JPG = "jpg";
		
		if (!url.substring(url.lastIndexOf(".") + 1).equalsIgnoreCase(EXT_JPG)) {
			throw new Exception("File from URL not supported");
		}
		
		URL imageUrl = new URL(url);
        URLConnection ucon = imageUrl.openConnection();
        ucon.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
		
        InputStream is = ucon.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int read = 0;
        while ((read = is.read(buffer, 0, buffer.length)) != -1) {
            baos.write(buffer, 0, read);
        }
        baos.flush();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
	}
	
	public static void main(String[] args) throws Exception {
		final String url = "https://www.pinclipart.com/picdir/middle/306-3060913_png-file-clipart.png";
		String tes = url.substring(url.lastIndexOf(".") + 1);
		String res = ImageCompressor.getByteArrayFromImageURL(url);
	}
}