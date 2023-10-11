package com.example.employee.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.Yaml;

@RestController
@RequestMapping("/yaml")
public class YamlController {
	@GetMapping("get_yaml_file")
	public Map<String,Object> getData(){
		Yaml yaml = new Yaml();
		try {
			File file = new File(getClass().getClassLoader().getResource("Employees.yaml").getFile());
			FileReader fileReader = new FileReader(file);
			Map<String,Object> data = yaml.load(fileReader);
			return data;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	@PutMapping("/update-yaml-in-local-repo")
    public String updateYaml(@RequestBody Map<String, String> keyValue) {
        // Update the YAML file remotely
        try {
            Yaml yaml = new Yaml();
            Map<String, Object> yamlData = yaml.load(this.getClass().getResourceAsStream("/Employees.yaml"));
            yamlData.putAll(keyValue);

            String updatedYaml = yaml.dump(yamlData);

            try (FileOutputStream output = new FileOutputStream("src/main/resources/Employees.yaml")) {
                output.write(updatedYaml.getBytes());
            }

            return "YAML file updated on local repo...";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error updating the YAML file.";
        }
    }
	
}
