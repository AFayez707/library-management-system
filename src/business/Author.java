package business;

import java.io.Serializable;

final public class Author extends Person implements Serializable {
	private String authorId;

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	private String bio;
	private Book book;
	public String getBio() {
		return bio;
	}
	
	public Author(String id, String f, String l, String t, Address a, String bio) {
		super(f, l, t, a);
		this.authorId = id;
		this.bio = bio;
	}	
	

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public String toString() {
		return this.getFirstName() + " " + this.getLastName();
	}

	private static final long serialVersionUID = 7508481940058530471L;
}
