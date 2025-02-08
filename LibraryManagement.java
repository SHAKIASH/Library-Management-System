package library;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

class Book{
	String title;
	String author;
	int bookId;
	boolean isAvailable;
	
	public Book(String title,String author,int bookId){
		this.title=title;
		this.author=author;
		this.bookId=bookId;
		this.isAvailable=true;
	}
	public void displayBooks() {
		System.out.println("Book ID: "+bookId);
		System.out.println("Title: "+title);
		System.out.println("Author: "+author);
		System.out.println("Availability: "+(isAvailable?"Available":"Borrowed"));
		System.out.println();
		System.out.println();
	}
}
class User{
	String userName;
	String password;
	String role;
	
	public User(String userName,String password,String role) {
		this.userName=userName;
		this.password=password;
		this.role=role;
	}
}
class Library{
	ArrayList<Book> books;
	HashMap<String, User> users;
	
	public Library() {
		this.books=new ArrayList<>();
		this.users=new HashMap<>();
		addUser(new User("admin","password","librarian"));
		addUser(new User("student","password","student"));
	}
	public void addBook(Book book) {
		books.add(book);
		System.out.println("Book added successfully!!");
	}
	public void displayAllBooks() {
		if(books.isEmpty()) {
			System.out.println("No books in the library.");
			return;
		}
		System.out.println("-----All books in the Library-----");
		for(Book book:books) {
			book.displayBooks();
			System.out.println("-------------------");
		}
	}
	public Book findBook(int bookId) {
		for(Book book:books) {
			if(book.bookId==bookId) {
				return book;
			}
		}
		return null;
	}
	public void barrowBook(int bookId) {
		Book book=findBook(bookId);
		if(book==null) {
			System.out.println("Book with Id "+bookId+"not found.");
			return;
		}
		if(!book.isAvailable) {
			System.out.println("Book with ID "+bookId+" is Already borrowed.");
			return;
		}
		book.isAvailable=false;
		System.out.println("Book with ID "+bookId+" borrowed successfully");
	}
	public void returnBook(int bookId) {
		Book book=findBook(bookId);
		if(book==null) {
			System.out.println("Book with Id "+bookId+" not found.");
			return;
		}
		if(book.isAvailable) {
			System.out.println("Book with ID "+bookId+" already available.");
			return;
		}
		book.isAvailable=true;
		System.out.println("Book with Id "+bookId+" return successfully.");
	}
	public void addUser(User user) {
		users.put(user.userName, user);
	}
	public User authenticateUser(String userName,String password) {
		User user=users.get(userName);
		if(user !=null && user.password.equals(password)) {
			return user;
		}
		return null;
	}
}
public class LibraryManagement {
	public static void main(String[] args) {
		Scanner scanner=new Scanner(System.in);
		Library library=new Library();
		User currentUser=null;
		
		while(true) {
			if(currentUser==null) {
				System.out.println("\n----Library Management System---");
				System.out.println("1. Login");
				System.out.println("2. Exit");
				System.out.print("Enter your choice: ");
				
				int choice=scanner.nextInt();
				scanner.nextLine();
				
				switch(choice) {
				case 1:
				System.out.print("Enter username: ");
				String username=scanner.nextLine();
				System.out.print("Enter password: ");
				String password=scanner.nextLine();
				
				currentUser =library.authenticateUser(username, password);
				
				if(currentUser !=null) {
					System.out.println("Login successful.Welcome, "+currentUser.userName+"!");
				}
				else {
					System.out.println("Invalid username or password.");
				}
				break;
				case 2:
					System.out.println("Exiting...");
					System.exit(0);
				default:
					System.out.println("Invalid choice. Please try again.");
				}
			}
				else {
					System.out.println("\n ----Library Management System----");
					System.out.println("1. Add Book(Librarian)");
					System.out.println("2. Display All Books");
					System.out.println("3. Borrow Book(Member)");
					System.out.println("4. Return Book(Member)");
					System.out.println("5. Logout");
					System.out.println("Enter your choice: ");
					
					int choice=scanner.nextInt();
					scanner.nextLine();
					
					switch(choice) {
					case 1:
						if(currentUser.role.equals("librarian")) {
						System.out.print("Enter book title: ");
						String title=scanner.nextLine();
						System.out.print("Enter author name: ");
						String author=scanner.nextLine();
						System.out.print("Enter book ID: ");
						int bookId=scanner.nextInt();
						scanner.nextLine();
						library.addBook(new Book(title,author,bookId));
					}
					else {
						System.out.println("You do not have permission to add Books.");
			
					}
						break;
					case 2:
						library.displayAllBooks();
						break;
					case 3:
						if(currentUser.role.equals("student")) {
							System.out.print("Enter book ID to barrow: ");
							int borrowId=scanner.nextInt();
							library.barrowBook(borrowId);
						}
						else {
							System.out.println("You dont have permission to Barrow Books.");
						}
						break;
					case 4:
						if(currentUser.role.equals("member")) {
							System.out.print("Enter book ID to return: ");
							int returnId=scanner.nextInt();
							library.returnBook(returnId);
						}
						else {
							System.out.println("You dont have permission to return Books.");
						}
						break;
					case 5:
						currentUser=null;
						System.out.println("Logged out successfully");
						break;
						default:
							System.out.println("Invalid choice.Please try again.");
						
					}
					
				}
			
			
		}
	}
}
