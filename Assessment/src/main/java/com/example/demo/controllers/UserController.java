package com.example.demo.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;


@RestController
public class UserController {
	
	
	 private  UserRepository userRepository;
	
	private UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	
	
	
	   @PostMapping("/users")
	    private User saveUser(@RequestBody User user) {

	        return userRepository.save(user);
	    }
	
	   @GetMapping
	    private User getUser(@RequestBody User userDetails,@PathVariable Integer id) throws IOException {

	        User user = findAllUser().stream().filter(user1 -> user1.getId().equals(id)).findAny().get();
	        userRepository.findById(id);
	        return user;
	    }
	   
	   
	   //Finding all users in the File.
	   
	@GetMapping
	public List<User> findAllUser() throws IOException{		
		
		File file = new File ("src\\main\\resources\\users.txt");
		
		List<User> users = new ArrayList<>();
		
		BufferedReader buffer = new BufferedReader(new FileReader(file));
		User user1 = new User();
		
		String result ;
		
		while((result = buffer.readLine()) != null)
		{
			System.out.println(result);
			String [] arrayResult = result.split("=");
			  user1.setFirstName(arrayResult[0]);
	            user1.setLastName(arrayResult[1]);
	            user1.setPhone(arrayResult[2]);
	            users = Arrays.asList(user1);
	
		}
		
		return users;
	}
	@PutMapping("/users/{id}")
    private User updateUser(@RequestBody User userDetails, @PathVariable Integer id) throws IOException {

        FileWriter writer = new FileWriter(
                "src/main/resources/users.txt");


        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(userDetails.getFirstName());
                    user.setLastName(userDetails.getLastName());
                    user.setPhone(userDetails.getPhone());

                    try {
                        writer.write(user.getFirstName()+"="+user.getLastName()+"="+user.getPhone());
                        writer.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    return userRepository.save(userDetails);
                });
    }
	
	
	
	
	 @DeleteMapping
	    private void deleteUserFile(@RequestBody User user ,@PathVariable Long id) throws IOException {
	       findAllUser().stream().forEach(u->{
	            try{
	                 if(id.equals(u.getId())){
	                     findAllUser().remove(user);
	                 }
	            BufferedWriter writer = Files.newBufferedWriter(Paths.get("src/main/resources/users.txt"));
	            writer.write("");
	            writer.flush();

	            writer.write(u.getFirstName()+"="+u.getLastName()+"="+u.getPhone());
	            writer.close();
	             } catch (IOException e) {
	                throw new RuntimeException(e);
	            }
	        });
	        userRepository.delete(user);
	    }

	

	
	
}
