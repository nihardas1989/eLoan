package com.iiht.training.eloan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iiht.training.eloan.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long>{
	
	List<Users> findByRole(String role);
	
	@Query("select e from Users e where e.role =:role and e.id=:id")
	Users findByRoleId(@Param("role")String role, @Param("id")Long id);

}
