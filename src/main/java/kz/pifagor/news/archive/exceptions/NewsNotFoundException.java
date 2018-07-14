package kz.pifagor.news.archive.exceptions;

public class NewsNotFoundException extends RuntimeException {
	public NewsNotFoundException(String message){
		super(message);
	}
}
