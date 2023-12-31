package com.example.employee.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHContentUpdateResponse;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.DumperOptions;
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
	
	@PutMapping("/add-new-key-value")
	public String updateRemote(@RequestBody Map<String, String> keyValue) {
	    try {
	        // Personal Access Token
	        String personalAccessToken = "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";

	        // Initialize GitHub client
	        GitHub gitHub = new GitHubBuilder()
	                .withOAuthToken(personalAccessToken)
	                .build();

	        // GitHub Repository Path URL
	        String repoOwner = "Mosin1425";
	        String repoName = "Employee-Incture";
	        String filePath = "src/main/resources/Employees.yaml";

	        // Fetch the existing YAML file content
	        GHRepository repo = gitHub.getRepository(repoOwner + "/" + repoName);
	        GHContent content = repo.getFileContent(filePath);
	        @SuppressWarnings("deprecation")
			String existingYamlContent = content.getContent();
	        
	        Yaml yaml = new Yaml();
	        Map<String, Object> yamlMap = yaml.load(existingYamlContent);

	        Map.Entry<String, String> entry = keyValue.entrySet().iterator().next();
	        String newKey = entry.getKey();
	        String newValue = entry.getValue();
	        
	        if(yamlMap.containsKey(newKey)) {
	        	return "The key " + newKey + " is already present in the file";
	        }
	        yamlMap.put(newKey, newValue);
	       
	        DumperOptions options = new DumperOptions();
	        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
	        Yaml updatedYaml = new Yaml(options);
	        String updatedYamlContent = updatedYaml.dump(yamlMap);
	        
	        
	        String commitMessage = "Update YAML file with new key as : " + newKey + " and value as : " + newValue;

	        // Update the file on GitHub
	        GHContentUpdateResponse updateResponse = content.update(updatedYamlContent, commitMessage);

	        if (updateResponse != null) {
	            return "YAML file updated successfully.";
	        } else {
	            return "Failed to update YAML file.";
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	        return "Error updating YAML file: " + e.getMessage();
	    }
	}
	
	@DeleteMapping("/delete-by-key/{key}")
	public String deleteRemotely(@PathVariable String key) {
		try {
	        String personalAccessToken = "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";
	        GitHub gitHub = new GitHubBuilder()
	                .withOAuthToken(personalAccessToken)
	                .build();

	        // GitHub repository URL to YAML file
	        String repoOwner = "Mosin1425";
	        String repoName = "Employee-Incture";
	        String filePath = "src/main/resources/Employees.yaml";

	        // Fetch the existing YAML file content
	        GHRepository repo = gitHub.getRepository(repoOwner + "/" + repoName);
	        GHContent content = repo.getFileContent(filePath);
	        @SuppressWarnings("deprecation")
			String existingYamlContent = content.getContent();
	        
	        Yaml yaml = new Yaml();
	        Map<String, Object> yamlMap = yaml.load(existingYamlContent);
	        
	        if(yamlMap.containsKey(key)) {
	        	yamlMap.remove(key);
	        	
	        	DumperOptions options = new DumperOptions();
		        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		        Yaml updatedYaml = new Yaml(options);
		        String updatedYamlContent = updatedYaml.dump(yamlMap);
		        
		        String commitMessage = "Delete key named as " + key;
		        GHContentUpdateResponse updateResponse = content.update(updatedYamlContent, commitMessage);
		        
		        if (updateResponse != null) {
		        	return "Key deleted successfully...";
		        } else {
		            return "Failed to delete the key...";
		        }
	        }
	        else {
	        	return "Key doesn't exist";
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
			return "Error deleting the file " + e.getMessage();
		}
	}
	
	@PutMapping("/update-key")
	public String updateKey(@RequestBody Map<String, String> keyValue) {
		try {
			String personalAccessToken = "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";
	        GitHub gitHub = new GitHubBuilder()
	                .withOAuthToken(personalAccessToken)
	                .build();

	        // GitHub repository URL to YAML file
	        String repoOwner = "Mosin1425";
	        String repoName = "Employee-Incture";
	        String filePath = "src/main/resources/Employees.yaml";

	        // Fetch the existing YAML file content
	        GHRepository repo = gitHub.getRepository(repoOwner + "/" + repoName);
	        GHContent content = repo.getFileContent(filePath);
	        @SuppressWarnings("deprecation")
			String existingYamlContent = content.getContent();
	        
	        Yaml yaml = new Yaml();
	        Map<String, Object> yamlMap = yaml.load(existingYamlContent);
	        
	        Map.Entry<String, String> entry = keyValue.entrySet().iterator().next();
	        String newKey = entry.getKey();
	        String newValue = entry.getValue();
	        
	        if(yamlMap.containsKey(newKey)) {
	        	yamlMap.put(newKey, newValue);
	        	
	        	DumperOptions options = new DumperOptions();
		        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		        Yaml updatedYaml = new Yaml(options);
		        String updatedYamlContent = updatedYaml.dump(yamlMap);
		        
		        String commitMessage = "Key: " + newKey + " updated successfully...";
		        GHContentUpdateResponse updateResponse = content.update(updatedYamlContent, commitMessage);
		        
		        if (updateResponse != null) {
		        	return "Key updated successfully...";
		        } else {
		            return "Failed to delete the key...";
		        }
	        }
	        else {
	        	return "Key doesn't exist";
	        }
	        
	        
	        
		} catch (Exception e) {
			e.printStackTrace();
			return "Error updating the file..." + e.getMessage();
		}
	}
	
	@GetMapping("/get-from-file-github")
	public Map<String, Object> getFromGithub() {
		try {
			String personalAccessToken = "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";
	        GitHub gitHub = new GitHubBuilder()
	                .withOAuthToken(personalAccessToken)
	                .build();

	        // GitHub repository URL to YAML file
	        String repoOwner = "Mosin1425";
	        String repoName = "Employee-Incture";
	        String filePath = "src/main/resources/Employees.yaml";

	        // Fetch the existing YAML file content
	        GHRepository repo = gitHub.getRepository(repoOwner + "/" + repoName);
	        GHContent content = repo.getFileContent(filePath);
	        @SuppressWarnings("deprecation")
			String existingYamlContent = content.getContent();
	        
	        Yaml yaml = new Yaml();
	        Map<String, Object> yamlMap = yaml.load(existingYamlContent);
	        
	        return yamlMap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
