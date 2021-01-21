package com.ovi.appolis_server.problem.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ovi.appolis_server.problem.data.dto.ProblemDto;
import com.ovi.appolis_server.problem.service.ProblemService;

@RestController
@RequestMapping("/problem")
@CrossOrigin(origins = "http://localhost:4200")
public class ProblemController {

	@Autowired
	private ProblemService problemService;

	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public List<String> getAllCategories() {
		return problemService.getAllCategories().stream().map(category -> category.getDescription())
				.collect(Collectors.toList());
	}

	@RequestMapping(value = "/new/images/{problemId}", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ProblemDto uploadImage(@PathVariable Long problemId, @RequestPart("file") MultipartFile image) {
		System.err.println("Attaching image...");
		return problemService.attachImages(problemId, image);
	}

	@RequestMapping(value = "/download/image/{imgName}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> downloadImage(@PathVariable String imgName) {
		byte[] bytes = problemService.downloadImage(imgName);
		if (bytes != null) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
		}
		return null;
	}

	@RequestMapping(value = "/image", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void receiveImage(@RequestPart("file") MultipartFile image) {
		System.err.println("Attaching image: " + image);
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ProblemDto createProblem(@RequestBody ProblemDto newProblem) {
		return problemService.createProblem(newProblem);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public List<ProblemDto> getProblemsWithStatus(@RequestParam(required = true) String status,
			@RequestParam(required = true) String cityName, @RequestParam(required = true) String countryName) {
		return problemService.getProblemDtosWithStatusFromCity(status, cityName, countryName);
	}

	@RequestMapping(value = "citizen/{citizenEmail}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProblemDto> getProblemsByCitizen(@PathVariable String citizenEmail) {
		return problemService.getProblemsByCitizen(citizenEmail);
	}

	@RequestMapping(value = "/citizen", method = RequestMethod.GET)
	@ResponseBody
	public List<ProblemDto> getProblemsByCitizenAndStatus(@RequestParam String citizenEmail,
			@RequestParam List<String> statusList, @RequestParam String cityName, @RequestParam String countryName) {
		return problemService.getProblemsByCitizenAndStatus(citizenEmail, statusList, cityName, countryName);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ProblemDto getProblemsWithStatus(@PathVariable Long id) {
		return problemService.getProblemById(id);
	}

	@RequestMapping(value = "/status", method = RequestMethod.POST)
	@ResponseBody
	public ProblemDto updateStatus(@RequestParam(required = true) Long problemId,
			@RequestParam(required = true) String statusName, @RequestParam(required = true) String clerkEmail) {
		return problemService.updateStatus(problemId, statusName, clerkEmail);
	}

	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	@ResponseBody
	public ProblemDto addComment(@RequestParam(required = true) Long problemId,
			@RequestParam(required = true) String comment, @RequestParam(required = true) String clerkEmail) {
		return problemService.addComment(problemId, comment, clerkEmail);
	}

	@RequestMapping(value = "/close", method = RequestMethod.POST)
	public ProblemDto closeProblem(@RequestParam(required = true) Long problemId,
			@RequestParam(required = true) Long rating) {
		return problemService.closeProblem(problemId, rating);
	}
}
