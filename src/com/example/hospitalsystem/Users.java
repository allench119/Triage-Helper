package com.example.hospitalsystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * A class of users, which tracks username and password as well as whether each user is a physician or nurse.
 */
public class Users{
	/**
	 * List of users. Resizable to allow addition/removal of valid users.
	 */
	protected List<User> users = new ArrayList<User>() ;
	
	public Users(File userData) {
		try {
			
			Scanner s = new Scanner(new FileInputStream(userData));
			while(s.hasNextLine()){
				String[] userString = s.nextLine().split(",");
				User temp = new User(userString[0], userString[1], userString[2]);
				System.out.println(temp.toString());
				this.users.add(temp);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Users(List<User> u) {
		this.users = u;
	}
	
	/**
	 * Returns the user that has the username login and password password, or null if no such user exists.
	 * @param login The username.
	 * @param password The password.
	 * @return User with username=login and password=password; null if no such user exists.
	 */
	public User login(String login, String password) {
		for(User user: users){
			if (user.validCredentials(login, password)) {
				return user;
			}
		}
		return null;
	}
	
	/**
	 * Serializes this object, saving it to location dir with the name of file being fileName.
	 * @param dir The location this object will be serialized to.
	 * @param fileName The name of the file being made.
	 */
	public void Serialize(File dir, String fileName) {
		try {
			FileOutputStream fos = new FileOutputStream(new File(dir, fileName));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the Users object saved at a particular location with a particular file name.
	 * @param dir The file object containing the location of the users file.
	 * @param fileName The name of the users file.
	 * @return
	 */
	public static Users deserialize(File dir, String fileName) {
		try{
			FileInputStream fis = new FileInputStream(new File(dir, fileName));
			ObjectInputStream ois = new ObjectInputStream(fis);
			Users u = (Users) ois.readObject();
			ois.close();
			fis.close();
			return u;
		} catch (FileNotFoundException e) {
				
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	protected void setUsers(List <User> users) {
		this.users = users;
	}
	
	protected List<User> getUsers() {
		return this.users;
	}
	
}
