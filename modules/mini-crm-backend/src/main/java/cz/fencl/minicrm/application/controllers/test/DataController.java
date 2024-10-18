package cz.fencl.minicrm.application.controllers.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cz.fencl.minicrm.application.services.S3Service;

@RestController
@RequestMapping("/test")
public class DataController {
    @Autowired
    S3Service s3Service;

    @GetMapping("/save")
    public String saveData(@RequestParam(value="pathSave") String path, @RequestParam(value = "dataSave") String data) {
        s3Service.saveData(path, data);
        return String.format("Data: \"%s\" saved!", data);
    }

    @GetMapping("/get")
    public String getData(@RequestParam(value="pathGet") String path) {
        return String.format("Data: %s", s3Service.getData(path));
    }

    @GetMapping("/delete")
    public String deleteData(@RequestParam(value = "pathDelete") String path) {
        s3Service.deleteData(path);
        return "Data has been removed!";
    }

    @GetMapping("/list")
    public String listObjects(@RequestParam(value = "pathList", defaultValue = "") String path) {
        s3Service.listObjects(path);
        return "Objects listed!";
    }
}
