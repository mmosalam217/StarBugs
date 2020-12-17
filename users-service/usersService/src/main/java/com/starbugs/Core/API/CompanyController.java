package com.starbugs.Core.API;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.starbugs.Core.Config.JwtProvider;
import com.starbugs.Core.Model.Company;
import com.starbugs.Core.Service.CompanyService;

@RestController
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	JwtProvider jwtUtils;
	
	
	
	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	@RequestMapping("/companies/company/{id}")
	public ResponseEntity<?> getCompany(@PathVariable("id") UUID id){
		try {
			Company comp = companyService.getCompanyById(id);
			return ResponseEntity.ok(comp);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getLocalizedMessage());
		}
	}
	@RequestMapping(value = "/companies/add-company", method = RequestMethod.POST)
	public Company addCompany(@RequestBody Company company) {
		Company client = null;
		try {
			client = companyService.addCompany(company);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return client;
	}
	
	

	


	
	
}
