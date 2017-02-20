package com.src.common;

import java.io.IOException;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import com.src.bean.CEBook;
import com.src.bean.EEBook;
import com.src.bean.BookChapter;
import com.src.bean.Library;

public class GetObject {
	
	public static Library parseXmlByDigester(){	
		Digester digester = new Digester();
		Library library = null;
		digester.setValidating(false);
		digester.addObjectCreate("library", Library.class);
		digester.addSetProperties("library");
		digester.addObjectCreate("library/EEbook", EEBook.class);
		digester.addSetProperties("library/EEbook");
		digester.addObjectCreate("library/EEbook/chapter", BookChapter.class);
		digester.addBeanPropertySetter("library/EEbook/chapter/no");
		digester.addBeanPropertySetter("library/EEbook/chapter/caption");
		digester.addObjectCreate("library/CEbook", CEBook.class);
		digester.addSetProperties("library/CEbook");
		digester.addObjectCreate("library/CEbook/chapter", BookChapter.class);
		digester.addBeanPropertySetter("library/CEbook/chapter/no");
		digester.addBeanPropertySetter("library/CEbook/chapter/caption");
		digester.addSetNext("library/EEbook/chapter", "addBookChapter");
		digester.addSetNext("library/EEbook", "addEEBook");
		digester.addSetNext("library/CEbook/chapter", "addBookChapter");
		digester.addSetNext("library/CEbook", "addCEBook");
		try {
		library = (Library)digester.parse(GetObject.class.getResourceAsStream("book.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return library;
	}
	
	public static void main(String[] args) throws IOException, SAXException {
		Library library = GetObject.parseXmlByDigester();
		//Digester digester = DigesterLoader.createDigester(GetObject.class.getResource("book_rule.xml"));
		//Library library = (Library)digester.parse(GetObject.class.getResourceAsStream("book.xml"));
		System.out.println(" 图书馆: " + library.getName());
		System.out.println("共藏书: " +(library.getListEEBook().size()+library.getListCEBook().size()));		
		for(EEBook book : library.getListEEBook()){
			System.out.println("EE共有"+library.getListEEBook().size()+"本书");
			System.out.println("书名: "+book.getTitle()+"  作者: "+book.getAuthor());
			System.out.println(".........................");
			System.out.println("共 "+book.getListBookChapter().size()+"章");
			for(BookChapter bookChapter:book.getListBookChapter()){
				System.out.println(bookChapter.getNo()+":"+bookChapter.getCaption());
			}
			System.out.println("..................");
		}	
		for(CEBook book : library.getListCEBook()){
			System.out.println("CE共有"+library.getListCEBook().size()+"本书");
			System.out.println("书名: "+book.getTitle()+"  作者: "+book.getAuthor());
			System.out.println(".........................");
			System.out.println("共 "+book.getListBookChapter().size()+"章");
			for(BookChapter bookChapter:book.getListBookChapter()){
				System.out.println(bookChapter.getNo()+":"+bookChapter.getCaption());
			}
			System.out.println("..................");
		}
	}
}
