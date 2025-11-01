package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Loan implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private User user;
	private Book book;
	private Date borrowDate;
	private Date returnDate;

	public Loan() {

	}

	public Loan(Integer id, User user, Book book, Date borrowDate, Date returnDate) {
		this.id = id;
		this.user = user;
		this.book = book;
		this.borrowDate = borrowDate;
		this.returnDate = returnDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Date getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(Date borrowDAte) {
		this.borrowDate = borrowDAte;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Loan other = (Loan) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Loan [id=" + id + ", user=" + user + ", book=" + book + ", borrowDate=" + borrowDate + ", returnDate="
				+ returnDate + "]";
	}

}
