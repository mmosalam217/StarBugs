package com.starbugs.Core.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.starbugs.Core.Dao.CompanyRepository;
import com.starbugs.Core.Model.Company;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	

	public CompanyService(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	public Company addCompany(Company company) throws Exception {
		// validate inputs..
		Company clientCompany = companyRepository.save(company);
		return clientCompany;
	
		
	}
	
	public Company getCompanyById(UUID id) throws Exception{
		return companyRepository.findById(id).orElseThrow(()-> new Exception("Company Not Found"));
	}
	
	// Only Client Root User is allowed to perform such an action...
	public Company updateCompany(UUID id, Company updates) throws Exception {

		Company company = companyRepository.findById(id).orElseThrow(()-> new Exception("Company Not Found"));
		company.setDomain(updates.getDomain());
		company.setName(updates.getName());
		company.setRegion(updates.getRegion());
		return companyRepository.save(company);
	}
	
	public void deleteCompany(UUID id) {
		companyRepository.deleteById(id);
	}
	
	

	
	
}
