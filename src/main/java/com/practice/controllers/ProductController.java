package com.practice.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.practice.exceptions.ErrorResponse;
import com.practice.exceptions.ProductException;
import com.practice.model.Product;
import com.practice.service.ProductService;

@RestController
@RequestMapping("/onlineshopping")
public class ProductController {
	
	@Autowired
	
	private ProductService productService;
	
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@RequestMapping("/hi")
	public String sayHi()
	{
		return "hello";
	}
	@RequestMapping(value="/products",method=RequestMethod.POST,consumes="application/json")
	public ResponseEntity addProduct(@RequestBody Product product) {
		 productService.saveProduct(product);
		 return new ResponseEntity("product saved successfully",HttpStatus.OK);
		
	}
	@RequestMapping(value="/products",method=RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
	public Iterable<Product> getProducts(){
		return productService.getAllProducts();
	}

	@RequestMapping(value="/products/{id}",method=RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
	//@ResponseStatus(value=HttpStatus.NOT_FOUND,reason="This Product is not found in the system")

	public ResponseEntity<Product> getProductsById(@PathVariable String id) throws Exception{
		Long l=null;
		 if(StringUtils.isNumeric(id))
		 {
			l= Long.parseLong(id);
			l=l/0;
		 }
	  else
		 {
		  throw new ProductException("Invalid Product name requested");
		 }
		 
		 
				Product p=productService.getProductById(l);
				if(p!=null) {
					//return p;
					return new ResponseEntity<Product>(p,HttpStatus.OK);
				}
				else
				{
					return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
				}
				
	}
	
	@ExceptionHandler(ProductException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);
	}
}
