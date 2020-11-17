package com.company.services;

import com.company.constants.Errors;
import com.company.data.dtos.DnaTestModel;
import com.company.data.dtos.DnaTestModelDetails;
import com.company.data.dtos.RunDnaTest;
import com.company.data.entities.DnaTest;
import com.company.data.entities.User;
import com.company.enums.RoleType;
import com.company.errors.exceptions.DnaTestNotFoundException;
import com.company.repositories.DnaTestRepository;
import com.company.util.DnaDecoder;
import com.company.util.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DnaTestService {


    private final DnaDecoder dnaDecoder;
    private final DnaTestRepository dnaTestRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;


    @Autowired
    public DnaTestService(DnaDecoder dnaDecoder, DnaTestRepository dnaTestRepository, UserService userService, ModelMapper modelMapper, JwtUtil jwtUtil) {
        this.dnaDecoder = dnaDecoder;
        this.dnaTestRepository = dnaTestRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Creates a new DNA test.
     *
     * @param createDnaTest Object containing all required information to create a new DNA test.
     * @return newly created DNA test.
     */
    public DnaTestModel createNewDnaTest(final RunDnaTest createDnaTest) {
        User user = findUserByUsername(createDnaTest);
        final double geneticDisorderProbability = dnaDecoder.getGeneticDisorderProbability(createDnaTest.getDna());
        final DnaTest dnaTest = this.modelMapper.map(createDnaTest, DnaTest.class);
        dnaTest.setResult(geneticDisorderProbability);
        dnaTest.setUser(user);
        dnaTestRepository.saveAndFlush(dnaTest);
        return modelMapper.map(dnaTest, DnaTestModel.class);
    }

    /**
     * Retrieves all existing DNA tests from repository.
     *
     * @return DNA test.
     */
    public List<DnaTestModel> getAllDnaTests() {
        if (jwtUtil.userHasRole(RoleType.MEDICAL_PHYSICIAN)) {
            return this.dnaTestRepository.findAll()
                    .stream()
                    .map(dnaTest -> this.modelMapper.map(dnaTest, DnaTestModel.class))
                    .collect(Collectors.toList());
        } else {
            return this.dnaTestRepository.findAllByUserUsername(jwtUtil.getCurrentUsername())
                    .stream()
                    .map(dnaTest -> this.modelMapper.map(dnaTest, DnaTestModel.class))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Retrieves a DNA test by its ID.
     *
     * @param id The unique DNA test ID.
     * @return DNA test.
     */
    public DnaTestModelDetails getDnaTestById(final Long id) {
        final DnaTest dnaTest = this.dnaTestRepository.findById(id).orElseThrow(() -> new DnaTestNotFoundException(Errors.DNA_TEST_NOT_FOUND));
        System.out.println(this.modelMapper.map(dnaTest, DnaTestModelDetails.class));
        return this.modelMapper.map(dnaTest, DnaTestModelDetails.class);
    }

    private User findUserByUsername(final RunDnaTest createDnaTest) {
        return this.userService.getUserByUsername(createDnaTest.getUsername());
    }


}
