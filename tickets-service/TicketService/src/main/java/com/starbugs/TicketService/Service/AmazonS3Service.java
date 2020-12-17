package com.starbugs.TicketService.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.starbugs.TicketService.Model.TicketAttachment;

@Service
public class AmazonS3Service {

	@Value("${aws.s3.bucket}")
	private String s3Bucket;
	
	@Value("${aws.endpointUrl}")
	private String endpoint;
	
	@Autowired
	private AmazonS3 amazonS3;
	
	public AmazonS3Service(AmazonS3 amazonS3) {
		this.amazonS3 = amazonS3;
	}

	public AmazonS3Service() {
		
	}
	
	
	// Upload single attachment...
	public TicketAttachment uploadFile(MultipartFile file) throws IOException, AmazonServiceException  {
		File temp = new File(file.getOriginalFilename());
		String newFileName = new Date().getTime() + "_" + file.getOriginalFilename().replace(" ", "_");
			 
			// Copy bytes from Multipart file into temp file
			FileOutputStream fileOutputStream = new FileOutputStream(temp);
			fileOutputStream.write(file.getBytes());
			fileOutputStream.close();
			
			// upload files one by one to s3 bucket...
			PutObjectRequest putRequest = new PutObjectRequest(s3Bucket, newFileName, temp).withCannedAcl(CannedAccessControlList.PublicRead);
					
			this.amazonS3.putObject(putRequest);
			// Delete temp file...
			temp.delete();
			
			// Return Attachment instance....
			TicketAttachment attachment  = new TicketAttachment();
			attachment.setUrl(endpoint + "/" + s3Bucket + "/" + newFileName);
			return attachment;

		
	}
	
	// Upload multiple attachments...
	public List<TicketAttachment> uploadFiles( MultipartFile[] files){
		List<TicketAttachment> attachments = new ArrayList<>();
		// create temporary file for every uploaded file on the server...
			Arrays.asList(files).stream().forEach(file ->{
			TicketAttachment attachment = null;
			
				try {
					attachment = this.uploadFile(file);
					attachments.add(attachment);		

				} catch (AmazonServiceException | IOException e) {
					e.printStackTrace();
				}
			
			});
		return attachments;
	}
	
	//Delete File from Bucket
	public void deleteFile(String filename) throws Exception{
		try {
			DeleteObjectRequest deleteRequest = new DeleteObjectRequest(this.s3Bucket, filename);
			this.amazonS3.deleteObject(deleteRequest);
		}catch(AmazonServiceException e) {
			throw new Exception(e.getLocalizedMessage());
		}
		
	}
	
}
