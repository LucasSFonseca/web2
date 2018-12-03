/*
 * Copyright (c) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package br.imd.api;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.Books.Volumes.List;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;

import br.imd.model.Book;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.NumberFormat;


/**
 * @author luquinhas0805@gmail.com (Lucas Santos Fonseca)
 *
 */
public class BookApi {

  private static final String APPLICATION_NAME = "";
  
  private static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance();
  private static final NumberFormat PERCENT_FORMATTER = NumberFormat.getPercentInstance();
  
  public static Book queryGoogleBooks(JsonFactory jsonFactory, String query) throws Exception {
    ClientCredentials.errorIfNotSpecified();
    
    // Create Book Model
    Book book = new Book();
    
    // Set up Books client.
    final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
        .setApplicationName(APPLICATION_NAME)
        .setGoogleClientRequestInitializer(new BooksRequestInitializer(ClientCredentials.API_KEY))
        .build();
    // Set query string.
    System.out.println("Query: [" + "isbn: " + query + "]");
    List volumesList = books.volumes().list("isbn: " + query);
    // Execute the query.
    Volumes volumes = volumesList.execute();
    if (volumes.getTotalItems() == 0 || volumes.getItems() == null) {
      System.out.println("No matches found.");
      return null;
    }
    
    // Output results.
//    for (Volume volume : volumes.getItems()) {
      
    Volume volume = volumes.getItems().get(0);
    Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
 
      // ISBN
      book.setISBN(query);
      
      // Title.
      String title = volumeInfo.getTitle();
      
	  if(title.length() > 255) {
		  title = title.substring(0, 252) + "...";
	  }
	  
      book.setTitulo(title);
      
      // publishedDate
      book.setDataP(volumeInfo.getPublishedDate());
      
      // Author(s).
      java.util.List<String> authors = volumeInfo.getAuthors();
      if (authors != null && !authors.isEmpty()) {
        String authors_str = "";
        for (int i = 0; i < authors.size(); ++i) {
          authors_str += authors.get(i);
          if (i < authors.size() - 1) {
            authors_str += "; ";
          }
        }

	  	if(authors_str.length() > 255) {
	  		authors_str = authors_str.substring(0, 252) + "...";
	  	}
        
	  	book.setAutor(authors_str);
      }
      
      // Page Count.
      if(volumeInfo.getPageCount() != null && volumeInfo.getPageCount() > 0) {
        book.setNumeroPaginas(volumeInfo.getPageCount());
      }
      
      // Publication Date
      if(volumeInfo.getPublishedDate() != null) {
        book.setDataP(volumeInfo.getPublishedDate());
      }
      
      // Thumbnail
      if( volumeInfo.getImageLinks() != null )
      {
	      if(volumeInfo.getImageLinks().getThumbnail() != null) {
	    	System.out.println("========> Thumbnail: " + volumeInfo.getImageLinks().getThumbnail() );
	        book.setThumbnail(volumeInfo.getImageLinks().getThumbnail());
	      }
      }
      
      // Description
      String desc = volumeInfo.getDescription();
      if(desc != null) {
    	  if(desc.length() > 255) {
    		  desc = desc.substring(0, 252) + "...";
    	  }
    	  book.setDescricao(desc);
      }
      
      // Language
      if(volumeInfo.getLanguage() != null) {
        book.setIdioma(volumeInfo.getLanguage());
      }
//    }
    System.out.println(">>Query: " + volumeInfo.getIndustryIdentifiers());
    return book;
  }
  
//  public static void main(String[] args) {
//    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
//    try {
//      if (prefix != null) {
//        query = prefix + query;
//      }
//      try {
//        queryGoogleBooks(jsonFactory, query);
//        // Success!
//        return;
//      } catch (IOException e) {
//        System.err.println(e.getMessage());
//      }
//    } catch (Throwable t) {
//      t.printStackTrace();
//    }
//    System.exit(0);
//  }
  
}
