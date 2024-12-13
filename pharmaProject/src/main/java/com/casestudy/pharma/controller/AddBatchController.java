package com.casestudy.pharma.controller;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.casestudy.pharma.dto.BatchDTO;
import com.casestudy.pharma.entity.BatchInfo;
import com.casestudy.pharma.entity.ResponseMessage;
import com.casestudy.pharma.exception.PharmaControllerException;
import com.casestudy.pharma.service.BatchService;
import com.casestudy.pharma.service.CSVService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/batch")
public class AddBatchController {

	@Value("${email.recipient}")
    private String emailRecipient;
	
	@Value("${email.subject}")
    private String emailSubject;
	
	@Value("${email.body}")
    private String emailBody;
	
	@Autowired
	private WebClient webClient;
	
	@Autowired
	private BatchService batchService;
	
	@Autowired
    private CSVService csvService;
	
//	@Autowired
//    private KafkaProducerConfig kafkaProducerConfig;
	
	@Operation(summary = "Post Batch", description = "This endpoint post the batch and save in the Database all the batch details ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
	@PostMapping
	public ResponseEntity<ResponseMessage> doPost(@Valid @RequestBody BatchDTO batchDTO) {
		batchService.addBatch(batchDTO);
		return new ResponseEntity<ResponseMessage>(new ResponseMessage("created sucessfully"), HttpStatus.CREATED);
	}

	/**
     * @param batchCode
     * @return ResponseEntity
     */
	@Operation(summary = "Get Batch with batchcode", description = "This endpoint returns the batch details for the batchcode ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{batchCode}")
    public ResponseEntity<ResponseMessage> getBatchByCode(@PathVariable String batchCode) {
        BatchInfo batch = batchService.getBatchByCode(batchCode);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("fetched sucessfully"), HttpStatus.OK);
    }
 
    /**
     * @param page
     * @param size
     * @return ResponseEntity
     */
    // Fetch all batches with pagination
    @Operation(summary = "Get All Batches with pagination in pdf", description = "This endpoint returns all the batches with pagination in pdf format ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getAllBatches(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<BatchInfo> batches = batchService.getAllBatches(page,size);
        
        byte[] convertResultIntoPDF = convertResultIntoPDF(batches);
          HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=products.pdf");
      
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("to", emailRecipient);
        formData.add("subject", emailSubject);
        formData.add("body", emailBody);

        // Add PDF attachment as a part of the multipart request
        formData.add("attachment", new ByteArrayResource(convertResultIntoPDF) {
            @Override
            public String getFilename() {
                return "attachment.pdf";
            }
        });
        
         Mono<String> bodyToMono = webClient.post()
        .uri("/sendEmailWithAttachment")
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(BodyInserters.fromMultipartData(formData))
        .retrieve()
        .bodyToMono(String.class);
         
         bodyToMono.subscribe();
         
        //kafkaProducerConfig.sendEmailRequest(emailRecipient,emailSubject,emailBody,convertResultIntoPDF);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(convertResultIntoPDF);
        
    }

	private byte[] convertResultIntoPDF(Page<BatchInfo> batches) {
		 // Create PDF document
		// Add the table to the document
		Document document = new Document();
        try {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, byteArrayOutputStream);

        // Open document
        document.open();
        
        PdfPTable table = new PdfPTable(6);
        table.addCell("BATCH_CODE");
        table.addCell("MEDICINE_CODE");
        table.addCell("WEIGHT");
        table.addCell("PRICE");
        table.addCell("MEDICINE_TYPE_CODE");
        table.addCell("SHIPPING_CHARGE");

        // Add data from the paginated results
        for (BatchInfo batchInfo : batches.getContent()) {
            table.addCell(String.valueOf(batchInfo.getBatchCode()));
            table.addCell(batchInfo.getMedicineMaster().getMedicineCode());
            table.addCell(String.valueOf(batchInfo.getWeight()));
            table.addCell(String.valueOf(batchInfo.getPrice()));
            table.addCell(String.valueOf(batchInfo.getMedicineTypeMaster().getMedicineTypeCode()));
            table.addCell(String.valueOf(batchInfo.getShippingCharge()));
        }

        
			document.add(table);
			document.close();
			 // Get the PDF as byte array
	        byte[] pdfBytes = byteArrayOutputStream.toByteArray();
	        return pdfBytes;
		} catch (DocumentException e) {
			throw new PharmaControllerException("601", "Some Issue while writing the result in pdf");
		}finally {
			if(document!=null) {
				document.close();
			}
		}
	}
	
	
	@Operation(summary = "Bulk upload batches from csv", description = "This endpoint bulk uploads the batches which are provided in csv format ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/csv")
    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try {
            List<BatchDTO> batchDTOs = csvService.parseCSVFile(file);
            batchService.saveRequests(batchDTOs);
            return ResponseEntity.ok("Uploaded successfully! Processed " + batchDTOs.size() + " records.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while processing the file: " + e.getMessage());
        }
    }
	
}
