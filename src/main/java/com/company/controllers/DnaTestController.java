package com.company.controllers;

import com.company.data.dtos.DnaTestModel;
import com.company.data.dtos.DnaTestModelDetails;
import com.company.data.dtos.RunDnaTest;
import com.company.services.DnaTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/tests")
public class DnaTestController {

    @Autowired
    private DnaTestService dnaTestService;

    /**
     * POST method for running new DNA test.
     * @param createDnaTest Object containing all info for running new test from request body.
     * @return The newly created test in the response
     */
    @RequestMapping(method = RequestMethod.POST, path = "/run-test")
    public ResponseEntity<DnaTestModel> runNewTest(@RequestBody final RunDnaTest createDnaTest) {
        final DnaTestModel newDnaTest = dnaTestService.createNewDnaTest(createDnaTest);
        return new ResponseEntity<>(newDnaTest, HttpStatus.OK);
    }

    /**
     * GET method for retrieving all DNA tests.
     * @return All DNA tests in the response.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<DnaTestModel>> getAllTests() {
        List<DnaTestModel> dnaTests = dnaTestService.getAllDnaTests();
        return new ResponseEntity<>(dnaTests, HttpStatus.OK);
    }

    /**
     * GET method for retrieving a detailed representation of a DNA test by its ID.
     * @param id Target DNA test ID.
     * @return Detailed representation of target DNA test.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<DnaTestModelDetails> getTestById(@PathVariable final Long id) {
        return new ResponseEntity<>(this.dnaTestService.getDnaTestById(id), HttpStatus.OK);
    }
}
