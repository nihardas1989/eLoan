package com.iiht.training.eloan.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiht.training.eloan.dto.UserDto;
import com.iiht.training.eloan.entity.Users;
import com.iiht.training.eloan.repository.UsersRepository;
import com.iiht.training.eloan.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private UsersRepository usersRepository;
	
	private UserDto convertUserEntityToUserOutputDto(Users users) {
		UserDto userDto = new UserDto(users.getId(), users.getFirstName(), 
				users.getLastName(), users.getEmail(), users.getMobile());
		return userDto;
	}
	
	private Users covertUserInputDtoToUserEntity(UserDto userDto, String role) {
		Users users = new Users(userDto.getFirstName(), userDto.getLastName(),
				userDto.getEmail(),userDto.getMobile(), role);
		return users;
	}
	
	@Override
	public UserDto registerClerk(UserDto userDto) {
		Users users = this.covertUserInputDtoToUserEntity(userDto, "Clerk");
		Users newUsers = this.usersRepository.save(users);
		UserDto userDtoReturn = this.convertUserEntityToUserOutputDto(newUsers);
		return userDtoReturn;
	}

	@Override
	public UserDto registerManager(UserDto userDto) {
		Users users = this.covertUserInputDtoToUserEntity(userDto, "Manager");
		Users newUsers = this.usersRepository.save(users);
		UserDto userDtoReturn = this.convertUserEntityToUserOutputDto(newUsers);
		return userDtoReturn;
	}

	@Override
	public List<UserDto> getAllClerks() {
		List<Users> users = this.usersRepository.findByRole("Clerk");//findByRole("Clerk")
		List<UserDto> usersDtos = 
				users.stream()
							 .map(this :: convertUserEntityToUserOutputDto)
							 .collect(Collectors.toList());
		return usersDtos;
	}

	@Override
	public List<UserDto> getAllManagers() {
		List<Users> users = this.usersRepository.findByRole("Manager");//findByRole("Manager")
		List<UserDto> usersDtos = 
				users.stream()
							 .map(this :: convertUserEntityToUserOutputDto)
							 .collect(Collectors.toList());
		return usersDtos;
	}

}
