package miu.edu.etitle.service.impl;

import miu.edu.etitle.domain.Model;
import miu.edu.etitle.repository.ModelRepository;
import miu.edu.etitle.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ModelServiceImpl implements ModelService {
    @Autowired
    ModelRepository modelRepository;

    @Override
    public Model getModel(String name) {
        Optional<Model> model = modelRepository.findByName(name);
        if (!model.isPresent()) {
            Model model1 = new Model();
            model1.setCode(name.toUpperCase());
            model1.setName(name);
            modelRepository.save(model1);
            model = modelRepository.findByName(name);
        }
        return model.get();
    }
}
